package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findById(Long id);
    Optional<Manufacturer> findByName(String name);
    Manufacturer findManufacturerById(Long id);
    Manufacturer findManufacturerByName(String name);
    void deleteManufacturerById(Long id);
    void deleteManufacturerByName(String name);
}
