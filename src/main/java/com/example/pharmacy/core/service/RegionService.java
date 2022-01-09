package com.example.pharmacy.core.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.core.exception.RegionNotFoundException;
import com.example.pharmacy.core.model.Country;
import com.example.pharmacy.core.model.Region;
import com.example.pharmacy.core.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class RegionService {

    private final RegionRepository RegionRepository;

    @Autowired
    public RegionService(RegionRepository RegionRepository) {
        this.RegionRepository = RegionRepository;
    }

    public Region saveRegion(Region Region, AppUser user) {
        Region save = RegionRepository.save(Region);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<Region> getRegions() {
        return RegionRepository.findAll();
    }

    public Region findRegion(Long id) {
        return RegionRepository
                .findById(id)
                .orElseThrow(
                        () -> new RegionNotFoundException("Region by id " + id + " was not found")
                );
    }

    public Region updateRegion(Region Region, AppUser user) {
        Region update = RegionRepository.save(Region);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteRegionById(Long id) {
        RegionRepository.deleteRegionById(id);
    }
}
