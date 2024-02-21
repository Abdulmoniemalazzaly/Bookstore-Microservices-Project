package com.bookstore.auth.model;

import lombok.Builder;


@Builder
public record AuthenticationRequest(
        String email,
        String password
) {}
