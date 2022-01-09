package com.example.pharmacy.management.repository;

import com.example.pharmacy.management.model.InventoryItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryItemHistoryRepository extends JpaRepository<InventoryItemHistory, Long> {
    Optional<InventoryItemHistory> findById(Long id);
}
