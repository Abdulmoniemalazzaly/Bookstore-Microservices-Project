package com.bookstore.user.records;

public record UserRequest(
        String firstname,
        String lastname,
        String address,
        String phone
) {}
