package com.bookstore.auth.ctrl;

import com.bookstore.auth.model.AuthenticationRequest;
import com.bookstore.auth.model.AuthenticationResponse;
import com.bookstore.auth.service.AuthenticationService;
import com.bookstore.commons.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationCtrl {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/validate-token")
    public Boolean validateToken(@RequestParam(name = "token") String token){
        return service.validateToken(token);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        service.refreshToken(request , response);
        return ResponseEntity.ok().build();
    }
}
