package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long> {
    Optional<Drug> findById(Long id);
    Optional<Drug> findByName(String name);
    Drug findDrugById(Long id);
    Drug findDrugByName(String name);
    void deleteDrugById(Long id);
    void deleteDrugByName(String name);
}
