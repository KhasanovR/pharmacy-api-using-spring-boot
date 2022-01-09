package com.example.pharmacy.management.repository;

import com.example.pharmacy.management.model.ReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, Long> {
    Optional<ReceiptItem> findById(Long id);
}
