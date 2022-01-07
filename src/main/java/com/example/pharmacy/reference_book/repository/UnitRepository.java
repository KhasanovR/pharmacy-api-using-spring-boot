package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findById(Long id);
    Optional<Unit> findByName(String name);
    Unit findUnitById(Long id);
    Unit findUnitByName(String name);
    void deleteUnitById(Long id);
    void deleteUnitByName(String name);
}
