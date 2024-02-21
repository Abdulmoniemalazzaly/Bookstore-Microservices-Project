package com.bookstore.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndPoint = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate",
            "/api/v1/auth/refresh-token",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndPoint
                    .stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
