package com.example.pharmacy.cashier.service;

import com.example.pharmacy.cashier.exception.OrderItemNotFoundException;
import com.example.pharmacy.cashier.model.OrderItem;
import com.example.pharmacy.cashier.repository.OrderItemRepository;
import com.example.pharmacy.cashier.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class OrderItemService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }


    public Collection<OrderItem> getOrderItemsByOrder(Long OrderId) {
        return orderRepository.findById(OrderId).orElseThrow(
                () -> new OrderItemNotFoundException("Order by id " + OrderId + " was not found")
        ).getOrderItems();
    }

    public OrderItem findOrderItem(Long id) {
        return orderItemRepository
                .findById(id)
                .orElseThrow(
                        () -> new OrderItemNotFoundException("Order Item by id " + id + " was not found")
                );
    }
}
