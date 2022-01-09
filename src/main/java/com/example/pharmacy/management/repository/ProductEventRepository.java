package com.example.pharmacy.management.repository;

import com.example.pharmacy.management.model.ProductEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductEventRepository extends JpaRepository<ProductEvent, Long> {
    Optional<ProductEvent> findById(Long id);
}
