package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.InternationalName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternationalNameRepository extends JpaRepository<InternationalName, Long> {
    Optional<InternationalName> findById(Long id);
    void deleteInternationalNameById(Long id);
}
