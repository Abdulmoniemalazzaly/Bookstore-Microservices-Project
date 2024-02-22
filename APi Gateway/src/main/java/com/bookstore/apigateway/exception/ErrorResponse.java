package com.bookstore.apigateway.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
   String apiPath,
   HttpStatus httpStatus,
   String errorMsg,
   LocalDateTime errorTime
) {}
