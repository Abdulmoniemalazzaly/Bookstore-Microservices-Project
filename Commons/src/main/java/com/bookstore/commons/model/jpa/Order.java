package com.bookstore.commons.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AuditorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id" , nullable = false)
    private String userId;
    @Column(name = "order_id" , nullable = false , unique = true)
    private String orderId;
    @Column(name = "book_id" , nullable = false)
    private Long bookId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    private String status;
}
