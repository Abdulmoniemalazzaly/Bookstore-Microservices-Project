package com.bookstore.order.ctrl;

import com.bookstore.commons.ctrl.GenericCtrl;
import com.bookstore.commons.model.jpa.Authority;
import com.bookstore.commons.model.jpa.Order;
import com.bookstore.commons.model.jpa.Role;
import com.bookstore.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderCtrl extends GenericCtrl<Order> {

    private final OrderService orderService;

    public OrderCtrl(OrderService orderService) {
        super(orderService);
        this.orderService = orderService;
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<List<Order>> findOrdersByUserId(@PathVariable(name = "user-id") String userId){
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

    @DeleteMapping("/id/{order-id}")
    public ResponseEntity<?> deleteByOrderId(@PathVariable(name = "order-id") String orderId){
        orderService.deleteByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseOrder(@RequestParam String orderId , HttpServletRequest httpServletRequest){
        orderService.purchaseOrder(orderId , httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/purchase/confirm")
    public ResponseEntity<?> confirmPurchaseOrder(@RequestParam String orderId , @RequestParam String code){
        orderService.confirmPurchaseOrder(orderId , code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public List<String> getCreateRoles() {
        return Collections.singletonList(Role.ROLES_NAMES.ROLE_USER.getValue());
    }

    @Override
    public List<String> getCreateAuthorities() {
        return Collections.singletonList(Authority.AUTHORITY_NAMES.READ_AUTHORITY.getValue());
    }
}
