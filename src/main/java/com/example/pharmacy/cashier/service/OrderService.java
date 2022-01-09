package com.example.pharmacy.cashier.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.cashier.exception.OrderCancelledException;
import com.example.pharmacy.cashier.exception.OrderNotFoundException;
import com.example.pharmacy.cashier.model.Order;
import com.example.pharmacy.cashier.repository.OrderRepository;
import com.example.pharmacy.core.util.ThrowableChecker;
import com.example.pharmacy.management.exception.ReceiptNotFoundException;
import com.example.pharmacy.management.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final BranchRepository branchRepository;
    private final ThrowableChecker throwableChecker;

    @Autowired
    public OrderService(OrderRepository orderRepository, BranchRepository branchRepository, ThrowableChecker throwableChecker) {
        this.orderRepository = orderRepository;
        this.branchRepository = branchRepository;
        this.throwableChecker = throwableChecker;
    }

    public Order saveOrder(Long branchId, Order Order, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Order save = orderRepository.save(Order);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        branchRepository.findById(branchId).orElseThrow(
                () -> new ReceiptNotFoundException("Branch by id " + branchId + " was not found")
        ).getOrders().add(save);
        return save;
    }

    public Collection<Order> getOrders(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new ReceiptNotFoundException("Branch by id " + branchId + " was not found")
        ).getOrders();
    }

    public Order findOrderById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(
                        () -> new OrderNotFoundException("Order by id " + id + " was not found")
                );
    }

    public void cancel(Long id, AppUser user) {
        Order Order = orderRepository
                .findById(id)
                .orElseThrow(
                        () -> new OrderNotFoundException("Order by id " + id + " was not found")
                );
        if (Order.isCancelled())
            throw new OrderCancelledException("Order by id " + id + " has been already cancelled");
        Order.setCancelledAt(Instant.now());
        Order.setCancelledBy(user);
    }

}
