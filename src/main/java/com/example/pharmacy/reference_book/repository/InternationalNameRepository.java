package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.InternationalName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternationalNameRepository extends JpaRepository<InternationalName, Long> {
    Optional<InternationalName> findById(Long id);
    Optional<InternationalName> findByName_en(String name);
    Optional<InternationalName> findByName_ru(String name);
    InternationalName findInternationalNameById(Long id);
    InternationalName findInternationalNameByName(String name);
    void deleteInternationalNameById(Long id);
    void deleteInternationalNameByName_en(String name);
    void deleteInternationalNameByName_ru(String name);
}
