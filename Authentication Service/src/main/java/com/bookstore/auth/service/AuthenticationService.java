package com.bookstore.auth.service;

import com.bookstore.auth.enums.TokenType;
import com.bookstore.commons.exception.EntityNotFoundException;
import com.bookstore.commons.exception.UserNotFoundException;
import com.bookstore.auth.model.AuthenticationRequest;
import com.bookstore.auth.model.AuthenticationResponse;
import com.bookstore.auth.model.jpa.Role;
import com.bookstore.auth.model.jpa.Token;
import com.bookstore.auth.model.jpa.User;
import com.bookstore.auth.repo.RoleRepo;
import com.bookstore.clients.user.UserClient;
import com.bookstore.commons.model.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final HttpServletRequest request;
    private final UserClient userClient;
    private final RoleRepo roleRepo;
    public static final String BEARER = "Bearer";

    public AuthenticationResponse register(RegisterRequest request) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepo.findById(Role.ROLES.ROLE_USER.getValue().longValue())
                .orElseThrow((() -> new EntityNotFoundException("Role not found " + Role.ROLES.ROLE_USER.getValue().longValue())));
        roles.add(role);
        User user = User.builder()
                .email(request.email())
                .userId(UUID.randomUUID().toString())
                .roles(roles)
                .password(passwordEncoder.encode(request.password()))
                .build();
        ResponseEntity userServiceResponse = userClient.saveUser(request , user.getUserId());
        final User savedUser;
        if (userServiceResponse != null)
            savedUser = userService.saveUser(user);
        else
           throw new RuntimeException("Error calling user service!");

        return buildResponse(savedUser);
    }

    private AuthenticationResponse buildResponse(final User user){
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user.getId());
        tokenService.saveToken(accessToken ,user , TokenType.ACCESS , request);
        tokenService.saveToken(refreshToken ,user , TokenType.REFRESH , request);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final User user = userService.getUser(request.email()).orElseThrow();
        return buildResponse(user);
    }

    public Boolean validateToken(String token) {

        final String email = jwtService.extractUsername(token);
        Boolean tokenRevoked = tokenService.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token doesn't exists"))
                .isRevoked();

        if (email != null  && !tokenRevoked) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (jwtService.isTokenValid(token, userDetails))
                return true;
            return false;
        } else {
            return false;
        }
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER))
            return;
        final String refreshToken = authHeader.substring(7);
        final String username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            final User user = userService.getUser(username)
                    .orElseThrow(() -> new UserNotFoundException("The user doesn't exists!"));
            if (jwtService.isTokenValid(refreshToken , user)){
                final String accessToken = jwtService.generateToken(user);
                // revoke the user tokens and save token
                revokeAllUserTokens(user.getId());
                tokenService.saveToken(accessToken ,user , TokenType.ACCESS , request);
                tokenService.saveToken(refreshToken ,user , TokenType.REFRESH , request);
                final AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void revokeAllUserTokens(Long userId){
        List<Token> tokens = tokenService.findAllValidTokensByUser(userId);
        if (!tokens.isEmpty()){
            tokens.forEach(token -> {
                token.setRevoked(true);
                token.setExpired(true);
            });
            tokenService.saveAll(tokens);
        }
    }
}
