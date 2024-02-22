package com.bookstore.order.repo;

import com.bookstore.commons.model.jpa.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order , Long> {

    @Query("""
        SELECT O FROM Order O WHERE O.userId = :userId
    """)
    List<Order> findByUserId(@Param("userId") String userId);

    @Query("""
        DELETE FROM Order O WHERE O.orderId = :id
    """)
    @Modifying
    void deleteByOrderId(@Param("id") String id);

    @Query("""
        SELECT O FROM Order O WHERE O.orderId = :orderId
    """)
    Optional<Order> findByOrderId(@Param("orderId") String orderId);
}
