package com.bookstore.auth.model;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String accessToken,
        String refreshToken
) {}
