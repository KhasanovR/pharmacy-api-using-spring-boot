package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findById(Long id);
    void deleteManufacturerById(Long id);
}
