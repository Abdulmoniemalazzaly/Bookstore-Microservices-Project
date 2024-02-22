package com.bookstore.auth.service;

import com.bookstore.auth.model.jpa.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
@RefreshScope
public class JWTService {

    private final String SECRET_KEY;
    private final Long ACCESS_TOKEN_EXPIRATION;
    private final Long REFRESH_TOKEN_EXPIRATION;

    public JWTService(@Value("${security.auth.jwt.secret}") String secretKey,
                      @Value("${security.auth.jwt.access.expiration}") Long accessTokenExpiration,
                      @Value("${security.auth.jwt.refresh.expiration}") Long refreshTokenExpiration) {
        SECRET_KEY = secretKey;
        ACCESS_TOKEN_EXPIRATION = accessTokenExpiration;
        REFRESH_TOKEN_EXPIRATION = refreshTokenExpiration;
    }

    public String extractUsername(final String token) {
        return extractClaim(token , Claims::getSubject);
    }

    public String generateRefreshToken (final UserDetails userDetails){
        return generateToken(new HashMap<>() , userDetails , REFRESH_TOKEN_EXPIRATION);
    }

    public String generateToken (final User user){
        Map<String , Object> extraClaims = new HashMap<>();
        List<String> permissions = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        extraClaims.put("Permissions" , permissions);
        extraClaims.put("UserId" , user.getUserId());
        return generateToken(extraClaims , user , ACCESS_TOKEN_EXPIRATION);
    }

    public boolean isTokenValid(final String token , final UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    public String generateToken(final Map<String , Object> extraClaims ,final UserDetails userDetails ,final Long expiration){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey() , SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(final String token , Function<Claims , T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
         catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
            throw new SecurityException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired");
            throw new SecurityException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported");
            throw new SecurityException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
            throw new SecurityException("JWT claims string is empty");
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}