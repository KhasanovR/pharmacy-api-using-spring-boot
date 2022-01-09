package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.PharmaGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PharmaGroupRepository extends JpaRepository<PharmaGroup, Long> {
    Optional<PharmaGroup> findById(Long id);
    void deletePharmaGroupById(Long id);
}
