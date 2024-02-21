package com.bookstore.payment.model;

import com.bookstore.commons.model.jpa.AuditorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true)
    private String paymentId;
    @Column(nullable = false)
    private String orderId;
    @Column(nullable = false)
    private String verificationCode;
    @Column(nullable = false)
    private String status;
}
