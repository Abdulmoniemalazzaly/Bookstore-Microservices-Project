package com.bookstore.auth.service;

import com.bookstore.auth.repo.TokenRepo;
import com.bookstore.auth.enums.TokenType;
import com.bookstore.auth.model.jpa.Token;
import com.bookstore.auth.model.jpa.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepo tokenRepo;

    public List<Token> findAllValidTokensByUser(Long userId){
        return tokenRepo.findAllValidTokensByUser(userId);
    }

    public void saveToken(final String token , final User user , TokenType tokenType , HttpServletRequest request){
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        tokenRepo.save(Token.builder()
                .token(token)
                .tokenType(tokenType)
                .user(user)
                .userAgnet(userAgent)
                .ipAddress(ip.getHostAddress())
                .remoteIpAddress(request.getRemoteAddr())
                .expired(false)
                .revoked(false)
                .build());
    }

    public void saveAll(List<Token> tokens) {
        tokenRepo.saveAll(tokens);
    }

    public void save(Token token) {
        tokenRepo.save(token);
    }

    public Optional<Token> findByToken(String token) {
        return tokenRepo.findByToken(token);
    }
}
