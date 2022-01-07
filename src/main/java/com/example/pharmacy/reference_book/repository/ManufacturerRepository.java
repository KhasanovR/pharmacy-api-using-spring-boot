package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.InternationalName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<InternationalName, Long> {
    Optional<InternationalName> findById(Long id);
    Optional<InternationalName> findByName(String name);
    InternationalName findInternationalNameById(Long id);
    InternationalName findInternationalNameByName(String name);
    void deleteInternationalNameById(Long id);
    void deleteInternationalNameByName(String name);
}
