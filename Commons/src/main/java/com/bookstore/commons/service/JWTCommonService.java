package com.bookstore.commons.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
@RefreshScope
public class JWTCommonService {

    private final String SECRET_KEY;
    private static final String USER_ID = "UserId";
    private static final String PERMISSIONS = "Permissions";

    public JWTCommonService(@Value("${security.auth.jwt.secret}") String secretKey) {
        SECRET_KEY = secretKey;
    }

    public String extractUserId(final String token) {
        return (String) extractClaim(token , claims -> claims.get(USER_ID));
    }

    public ArrayList<GrantedAuthority> extractPermissions(final String token){
        ArrayList<GrantedAuthority> permissions = new ArrayList<>();
        for (String permission : (List<String>) extractClaim(token, claims -> claims.get(PERMISSIONS))) {
            permissions.add(new SimpleGrantedAuthority(permission));
        }
        return permissions;
    }

    public <T> T extractClaim(final String token , Function<Claims, T> claimResolver){
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

    public String extractSubject(String token) {
        return extractClaim(token , Claims::getSubject);
    }
}
