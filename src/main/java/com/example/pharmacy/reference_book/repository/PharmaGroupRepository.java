package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.PharmaGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PharmaGroupRepository extends JpaRepository<PharmaGroup, Long> {
    Optional<PharmaGroup> findById(Long id);
    Optional<PharmaGroup> findByName(String name);
    PharmaGroup findPharmaGroupById(Long id);
    PharmaGroup findPharmaGroupByName(String name);
    void deletePharmaGroupById(Long id);
    void deletePharmaGroupByName(String name);
}
