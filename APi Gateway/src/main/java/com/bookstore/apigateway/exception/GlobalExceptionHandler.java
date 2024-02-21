package com.bookstore.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e , WebRequest request) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .apiPath(request.getDescription(false))
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMsg(e.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build()
                ,
                HttpStatus.BAD_REQUEST
        );
    }
}
