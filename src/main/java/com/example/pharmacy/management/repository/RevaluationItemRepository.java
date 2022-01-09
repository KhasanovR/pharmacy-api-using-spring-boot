package com.example.pharmacy.management.repository;

import com.example.pharmacy.management.model.RevaluationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevaluationItemRepository extends JpaRepository<RevaluationItem, Long> {
    Optional<RevaluationItem> findById(Long id);
}
