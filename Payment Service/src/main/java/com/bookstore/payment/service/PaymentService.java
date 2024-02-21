package com.bookstore.payment.service;

import com.bookstore.commons.amqp.RabbitMQMessageProducer;
import com.bookstore.commons.record.PaymentMessage;
import com.bookstore.payment.enums.PaymentStatus;
import com.bookstore.payment.model.Payment;
import com.bookstore.payment.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RefreshScope
public class PaymentService {

    private final RabbitMQMessageProducer producer;
    private final String INTERNAL_EXCHANGE;
    private final String INTERNAL_NOTIFICATION_ROUTING_KEY;
    private final PaymentRepo paymentRepo;

    public PaymentService(RabbitMQMessageProducer producer,
                          @Value("${rabbitmq.exchanges.internal}") String internalExchange,
                          @Value("${rabbitmq.routing-keys.internal-notification}") String internalNotificationRoutingKey, PaymentRepo paymentRepo) {
        this.producer = producer;
        this.INTERNAL_EXCHANGE = internalExchange;
        this.INTERNAL_NOTIFICATION_ROUTING_KEY = internalNotificationRoutingKey;
        this.paymentRepo = paymentRepo;
    }

    public Boolean sendPurchaseMail(String orderId, String email){
        List<Payment> payments = paymentRepo.findByOrderId(orderId);
        payments.forEach(payment -> {
            if (!payment.getStatus().equals(PaymentStatus.CANCELED))
                throw new RuntimeException("Invalid purchase");
        });
        Payment payment = Payment.builder()
                .paymentId(UUID.randomUUID().toString())
                .orderId(orderId)
                .verificationCode(generateRandomCode())
                .status(PaymentStatus.NOT_CONFIRMED.getValue())
                .build();
        paymentRepo.save(payment);
        PaymentMessage paymentMessage = PaymentMessage.builder()
                .paymentId(payment.getPaymentId())
                .userEmail(email)
                .code(payment.getVerificationCode())
                .build();
        producer.publish(paymentMessage,
                INTERNAL_EXCHANGE,
                INTERNAL_NOTIFICATION_ROUTING_KEY);
        return true;
    }

    private String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            int n = rnd.nextInt(9);
            code.append("" + n);
        }
        return code.toString();
    }

    public String getPaymentCode(String orderId) {
        return paymentRepo.findPaymentCode(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found witb order id " + orderId));
    }

    public void confirmPayment(String orderId) {
        List<Payment> payments = paymentRepo.findByOrderId(orderId);
        if (payments.size() < 1)
            throw new RuntimeException("invalid operation");
        Payment payment = payments.get(payments.size() -1 );
        payment.setStatus(PaymentStatus.CONFIRMED.getValue());
        paymentRepo.save(payment);
    }
}
