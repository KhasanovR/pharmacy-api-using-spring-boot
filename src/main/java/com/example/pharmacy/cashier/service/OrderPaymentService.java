package com.example.pharmacy.cashier.service;

import com.example.pharmacy.cashier.exception.OrderPaymentNotFoundException;
import com.example.pharmacy.cashier.model.OrderPayment;
import com.example.pharmacy.cashier.repository.OrderPaymentRepository;
import com.example.pharmacy.cashier.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class OrderPaymentService {
    private final OrderRepository orderRepository;
    private final OrderPaymentRepository orderPaymentRepository;

    @Autowired
    public OrderPaymentService(OrderRepository orderRepository, OrderPaymentRepository orderPaymentRepository) {
        this.orderRepository = orderRepository;
        this.orderPaymentRepository = orderPaymentRepository;
    }


    public Collection<OrderPayment> getOrderPaymentsByOrder(Long OrderId) {
        return orderRepository.findById(OrderId).orElseThrow(
                () -> new OrderPaymentNotFoundException("Order by id " + OrderId + " was not found")
        ).getOrderPayments();
    }

    public OrderPayment findOrderPayment(Long id) {
        return orderPaymentRepository
                .findById(id)
                .orElseThrow(
                        () -> new OrderPaymentNotFoundException("Order Payment by id " + id + " was not found")
                );
    }
}
