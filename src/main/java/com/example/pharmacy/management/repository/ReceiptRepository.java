package com.example.pharmacy.management.repository;

import com.example.pharmacy.management.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findById(Long id);
    void deleteReceiptById(Long id);
}
