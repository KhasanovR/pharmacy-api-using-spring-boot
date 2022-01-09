package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.management.exception.DistributorNotFoundException;
import com.example.pharmacy.management.model.Distributor;
import com.example.pharmacy.management.repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class DistributorService {
    private final DistributorRepository distributorRepository;

    @Autowired
    public DistributorService(DistributorRepository distributorRepository) {
        this.distributorRepository = distributorRepository;
    }

    public Distributor saveDistributor(Distributor distributor, AppUser user) {
        Distributor save = distributorRepository.save(distributor);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<Distributor> getDistributors() {
        return distributorRepository.findAll();
    }

    public Distributor findDistributor(Long id) {
        return distributorRepository
                .findById(id)
                .orElseThrow(
                        () -> new DistributorNotFoundException("Distributor by id " + id + " was not found")
                );
    }

    public Distributor updateDistributor(Distributor distributor, AppUser user) {
        Distributor update = distributorRepository.save(distributor);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteDistributorById(Long id) {
        distributorRepository.deleteDistributorById(id);
    }
}
