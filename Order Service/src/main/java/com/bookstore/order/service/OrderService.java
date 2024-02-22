package com.bookstore.order.service;

import com.bookstore.clients.payment.PaymentClient;
import com.bookstore.commons.model.jpa.Order;
import com.bookstore.commons.security.AuthorizationFilter;
import com.bookstore.commons.service.GenericService;
import com.bookstore.commons.service.JWTCommonService;
import com.bookstore.order.enums.OrderStatus;
import com.bookstore.order.repo.OrderRepo;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService extends GenericService<Order> {
    private final OrderRepo orderRepo;
    private final PaymentClient paymentClient;
    private final JWTCommonService jwtCommonService;

    public OrderService(OrderRepo orderRepo, PaymentClient paymentClient, JWTCommonService jwtCommonService) {
        super(orderRepo);
        this.orderRepo = orderRepo;
        this.paymentClient = paymentClient;
        this.jwtCommonService = jwtCommonService;
    }


    public void deleteByOrderId(String id) {
        orderRepo.deleteByOrderId(id);
    }

    @Override
    public void saveEntity(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.PLACED.getValue());
        super.saveEntity(order);
    }

    public List<Order> findByUserId(String userId) {
        return orderRepo.findByUserId(userId);
    }

    public void purchaseOrder(String orderId, HttpServletRequest httpServletRequest) {
        Order order = orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order doesn't exists with number " + orderId));
        if (!order.getStatus().equals(OrderStatus.PLACED.getValue()))
            throw new RuntimeException("Invalid operation!");
        String email = getUserEmail(httpServletRequest);
        paymentClient.sendPurchaseMail(orderId , email);
    }

    private String getUserEmail(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).replace(AuthorizationFilter.AUTH_HEADER_PREFIX , "");
        return jwtCommonService.extractSubject(token);
    }

    public void confirmPurchaseOrder(String orderId, String code) {
        Order order = orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        String paymentCode = paymentClient.getPaymentCode(orderId).getBody();
        if (!code.equals(paymentCode))
            throw new RuntimeException("Invalid code!");
        order.setStatus(OrderStatus.IN_ACTION.getValue());
        paymentClient.confirm(orderId);
        orderRepo.save(order);
    }
}
