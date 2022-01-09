package com.example.pharmacy.cashier.controller;

import com.example.pharmacy.cashier.model.OrderPayment;
import com.example.pharmacy.cashier.service.OrderPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/branches/{branchId}/orders/{orderId}/order-payments")
@Slf4j
public class OrderPaymentController {

    private final OrderPaymentService orderPaymentService;

    @Autowired
    public OrderPaymentController(OrderPaymentService orderPaymentService) {
        this.orderPaymentService = orderPaymentService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findOrderPayments(@PathVariable("branchId") Long branchId, @PathVariable("orderId") Long orderId) {
        Collection<OrderPayment> orderPayments = this.orderPaymentService.getOrderPaymentsByOrder(orderId);
        log.info("listing order payments: {}", orderPayments);
        return ResponseEntity.ok().body(orderPayments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrderPayment(@PathVariable("branchId") Long branchId, @PathVariable("orderId") Long OrderId, @PathVariable("id") Long id) {
        OrderPayment orderPayment = this.orderPaymentService.findOrderPayment(id);
        log.info("listing order payment: {}", orderPayment);
        return ResponseEntity.ok().body(orderPayment);
    }
}