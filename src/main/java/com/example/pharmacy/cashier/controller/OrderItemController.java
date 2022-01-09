package com.example.pharmacy.cashier.controller;

import com.example.pharmacy.cashier.model.OrderItem;
import com.example.pharmacy.cashier.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/branches/{branchId}/orders/{orderId}/order-items")
@Slf4j
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findOrderItems(@PathVariable("branchId") Long branchId, @PathVariable("orderId") Long orderId) {
        Collection<OrderItem> orderItems = this.orderItemService.getOrderItemsByOrder(orderId);
        log.info("listing order items: {}", orderItems);
        return ResponseEntity.ok().body(orderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrderItem(@PathVariable("branchId") Long branchId, @PathVariable("orderId") Long OrderId, @PathVariable("id") Long id) {
        OrderItem orderItem = this.orderItemService.findOrderItem(id);
        log.info("listing order item: {}", orderItem);
        return ResponseEntity.ok().body(orderItem);
    }
}