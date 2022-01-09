package com.example.pharmacy.management.repository;

import com.example.pharmacy.management.model.Revaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevaluationRepository extends JpaRepository<Revaluation, Long> {
    Optional<Revaluation> findById(Long id);
}
