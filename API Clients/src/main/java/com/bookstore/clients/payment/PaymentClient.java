package com.bookstore.clients.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "payment-service",
        path = "/api/v1/payment"
)
public interface PaymentClient {

    @PostMapping("/purchase")
    ResponseEntity<Boolean> sendPurchaseMail(@RequestParam(name = "order-id") String orderId,@RequestParam(name = "email") String email);

    @GetMapping("/{order-id}")
    ResponseEntity<String> getPaymentCode(@PathVariable(name = "order-id") String orderId);

    @PutMapping("/confirm/{order-id}")
    ResponseEntity<?> confirm(@PathVariable(name = "order-id") String orderId);
}
