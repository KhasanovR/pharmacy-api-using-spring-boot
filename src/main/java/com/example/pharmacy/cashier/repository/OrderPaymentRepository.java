package com.example.pharmacy.cashier.repository;

import com.example.pharmacy.cashier.model.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
    Optional<OrderPayment> findById(Long id);
    void deleteById(Long id);
}
