package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    Optional<Drug> findById(Long id);
    void deleteDrugById(Long id);
}
