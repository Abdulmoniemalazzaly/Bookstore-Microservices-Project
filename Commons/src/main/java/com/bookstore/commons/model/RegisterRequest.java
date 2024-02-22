package com.bookstore.commons.model;


import lombok.Builder;


@Builder
public record RegisterRequest(
        String firstname,
        String lastname,
        String password,
        String email,
        String phone,
        String address
) {}
