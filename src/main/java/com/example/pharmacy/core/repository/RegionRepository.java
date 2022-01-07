package com.example.pharmacy.core.repository;

import com.example.pharmacy.core.model.Country;
import com.example.pharmacy.core.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findById(Long id);
    Region findRegionById(Long id);
    Collection<Region> findRegionsByCountry(Country country);
    Collection<Region> findRegionsByParent(Region parent);
    void deleteRegionById(Long id);
    void deleteRegionsByCountry(Country country);
    void deleteRegionsByParent(Region parent);
}
