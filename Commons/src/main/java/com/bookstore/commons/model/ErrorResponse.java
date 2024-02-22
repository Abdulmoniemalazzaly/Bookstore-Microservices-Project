package com.bookstore.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
   @JsonProperty("Api Path")
   String apiPath,
   @JsonProperty("HTTP Status")
   HttpStatus httpStatus,
   @JsonProperty("Error Message")
   String errorMsg,
   @JsonProperty("Error Time")
   LocalDateTime errorTime
) {}
