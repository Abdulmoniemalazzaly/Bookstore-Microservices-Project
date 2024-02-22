package com.bookstore.payment.repo;

import com.bookstore.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment , Long> {

    @Query("""
        SELECT P.verificationCode FROM Payment P WHERE P.orderId = :orderId
    """)
    Optional<String> findPaymentCode(@Param("orderId") String orderId);

    @Query("""
        SELECT P FROM Payment P WHERE P.orderId = :orderId
    """)
    List<Payment> findByOrderId(@Param("orderId") String orderId);
}
