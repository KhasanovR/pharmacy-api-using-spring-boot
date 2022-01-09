package com.example.pharmacy.cashier.repository;

import com.example.pharmacy.cashier.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findById(Long id);
    void deleteById(Long id);
}
