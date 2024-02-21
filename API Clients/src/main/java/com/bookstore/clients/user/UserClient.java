package com.bookstore.clients.user;

import com.bookstore.commons.model.RegisterRequest;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.handler.advice.RequestHandlerCircuitBreakerAdvice;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "user-service",
        path = "/api/v1/user",
        fallback = UserClientFallback.class
)
public interface UserClient {

    @PostMapping
    ResponseEntity<?> saveUser(@RequestBody RegisterRequest registerRequest , @RequestParam(name = "user-id") String userId);
}



