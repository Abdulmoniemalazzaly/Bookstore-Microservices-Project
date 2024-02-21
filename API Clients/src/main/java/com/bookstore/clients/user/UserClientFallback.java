package com.bookstore.clients.user;

import com.bookstore.commons.model.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserClientFallback implements UserClient{
    @Override
    public ResponseEntity<?> saveUser(RegisterRequest registerRequest, String userId) {
        log.error("Error calling user service for saving user!");
        return null;
    }
}
