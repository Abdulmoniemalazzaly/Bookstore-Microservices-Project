package com.bookstore.auth.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {

        if (response.getBufferSize()>0)
            return ;
        handleResponse(request, response);
        final String errorJsonMessage = mapper.writeValueAsString(authException);
        response.getOutputStream().print(errorJsonMessage);
    }

    private void handleResponse(final HttpServletRequest request,final HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
    }
}
