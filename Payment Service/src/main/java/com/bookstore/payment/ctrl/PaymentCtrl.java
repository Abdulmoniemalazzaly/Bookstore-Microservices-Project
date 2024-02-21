package com.bookstore.payment.ctrl;

import com.bookstore.payment.model.Payment;
import com.bookstore.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentCtrl {

    private final PaymentService paymentService;

    @PostMapping("/purchase")
    public ResponseEntity<Boolean> sendPurchaseMail(@RequestParam(name = "order-id") String orderId,@RequestParam(name = "email") String email){
        return ResponseEntity.ok(paymentService.sendPurchaseMail(orderId , email));
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<String> getPaymentCode(@PathVariable(name = "order-id") String orderId){
        return ResponseEntity.ok(paymentService.getPaymentCode(orderId));
    }

    @PutMapping("/confirm/{order-id}")
    public ResponseEntity<?> confirm(@PathVariable(name = "order-id") String orderId){
        paymentService.confirmPayment(orderId);
        return ResponseEntity.ok().build();
    }
}
