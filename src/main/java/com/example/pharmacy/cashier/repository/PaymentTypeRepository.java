package com.example.pharmacy.cashier.repository;

import com.example.pharmacy.cashier.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    Optional<PaymentType> findById(Long id);
    void deleteById(Long id);
}
