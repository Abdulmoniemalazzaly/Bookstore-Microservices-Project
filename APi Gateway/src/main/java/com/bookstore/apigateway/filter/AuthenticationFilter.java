package com.bookstore.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


@Slf4j
@Component
@RefreshScope
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator routeValidator;
    private final WebClient.Builder webClientBuilder;
    private final String VALIDATE_TOKEN_URI;
    public static final String BEARER = "Bearer";

    public AuthenticationFilter(RouteValidator routeValidator, @Qualifier("Balanced") WebClient.Builder webClientBuilder,
            @Value("${validation-token.uri}") String validationTokenUri){
        super(Config.class);
        this.routeValidator = routeValidator;
        this.webClientBuilder = webClientBuilder;
        this.VALIDATE_TOKEN_URI = validationTokenUri;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())){
               if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                   throw new RuntimeException("There is no authorization header");

               final String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
               if (!authHeader.split(" ")[0].equals(BEARER))
                   throw new RuntimeException("Invalid token");

               final String token = authHeader.split(" ")[1];
               return webClientBuilder.build()
                       .post()
                       .uri(VALIDATE_TOKEN_URI + token)
                       .retrieve()
                       .onStatus(HttpStatus::isError, clientResponse -> clientResponse
                               .bodyToMono(String.class)
                               .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody))))
                       .bodyToMono(Boolean.class)
                       .flatMap(response -> {
                           if (!response)
                               return onError(exchange , new RuntimeException("Token Revoked!") , HttpStatus.UNAUTHORIZED);

                           return chain.filter(exchange);
                       })
                       .onErrorResume(throwable -> onError(exchange , throwable , HttpStatus.BAD_REQUEST));
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange , Throwable throwable , HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        byte[] bytes = throwable.getLocalizedMessage().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    public static class Config{

    }
}
