package com.example.pharmacy.core.repository;

import com.example.pharmacy.core.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findById(Long id);
    Optional<Country> findByName(String name);
    Country findCountryById(Long id);
    Country findCountryByName(String name);
    void deleteCountryById(Long id);
    void deleteCountryByName(String name);
}
