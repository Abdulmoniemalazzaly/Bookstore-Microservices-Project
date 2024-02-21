package com.bookstore.commons.record;

import lombok.Builder;

@Builder
public record PaymentMessage(
   String paymentId,
   String userEmail,
   String code
) {}
