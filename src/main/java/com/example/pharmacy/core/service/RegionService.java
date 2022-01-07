package com.example.pharmacy.core.service;

import com.example.pharmacy.core.exception.RegionNotFoundException;
import com.example.pharmacy.core.model.Country;
import com.example.pharmacy.core.model.Region;
import com.example.pharmacy.core.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class RegionService {
    private final RegionRepository RegionRepository;

    @Autowired
    public RegionService(RegionRepository RegionRepository) {
        this.RegionRepository = RegionRepository;
    }

    public Region saveRegion(Region Region) {
        return RegionRepository.save(Region);
    }

    public Collection<Region> getCountries() {
        return RegionRepository.findAll();
    }

    public Region findRegion(Long id) {
        return RegionRepository
                .findById(id)
                .orElseThrow(
                        () -> new RegionNotFoundException("Region by id " + id + " was not found")
                );
    }

    public Collection<Region> findRegionsByCountry(Country country) {
        return RegionRepository
                .findRegionsByCountry(country);
    }

    public Collection<Region> findRegionsByParent(Region parent) {
        return RegionRepository
                .findRegionsByParent(parent);
    }

    public Region updateRegion(Region Region) {
        return RegionRepository.save(Region);
    }

    public void deleteRegionById(Long id) {
        RegionRepository.deleteRegionById(id);
    }

    public void deleteRegionsByCountry(Country country) {
        RegionRepository.deleteRegionsByCountry(country);
    }

    public void deleteRegionsByParent(Region parent) {
        RegionRepository.deleteRegionsByParent(parent);
    }


}
