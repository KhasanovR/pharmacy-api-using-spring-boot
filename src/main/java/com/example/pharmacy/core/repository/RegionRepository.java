package com.example.pharmacy.core.repository;

import com.example.pharmacy.core.model.Country;
import com.example.pharmacy.core.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findById(Long id);
    void deleteRegionById(Long id);
}
