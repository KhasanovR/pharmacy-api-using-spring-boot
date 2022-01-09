package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findById(Long id);
    void deleteUnitById(Long id);
}
