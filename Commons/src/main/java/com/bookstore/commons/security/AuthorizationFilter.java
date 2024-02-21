package com.bookstore.commons.security;

import com.bookstore.commons.service.JWTCommonService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_HEADER_PREFIX = "Bearer";
    private final JWTCommonService jwtCommonService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, JWTCommonService jwtCommonService) {
        super(authenticationManager);
        this.jwtCommonService = jwtCommonService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        final String token = authHeader.substring(7);
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String userId = jwtCommonService.extractUserId(token);
        if (userId == null)
            return null;

        return new UsernamePasswordAuthenticationToken(userId , null ,
                jwtCommonService.extractPermissions(token));
    }
}
