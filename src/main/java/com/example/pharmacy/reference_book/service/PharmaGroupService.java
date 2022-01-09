package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.reference_book.exception.PharmaGroupNotFoundException;
import com.example.pharmacy.reference_book.model.PharmaGroup;
import com.example.pharmacy.reference_book.repository.PharmaGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class PharmaGroupService {
    private final PharmaGroupRepository pharmaGroupRepository;

    @Autowired
    public PharmaGroupService(PharmaGroupRepository pharmaGroupRepository) {
        this.pharmaGroupRepository = pharmaGroupRepository;
    }

    public PharmaGroup savePharmaGroup(PharmaGroup pharmaGroup, AppUser user) {
        PharmaGroup save = pharmaGroupRepository.save(pharmaGroup);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<PharmaGroup> getPharmaGroups() {
        return pharmaGroupRepository.findAll();
    }

    public PharmaGroup findPharmaGroupById(Long id) {
        return pharmaGroupRepository
                .findById(id)
                .orElseThrow(
                        () -> new PharmaGroupNotFoundException("PharmaGroup by id " + id + " was not found")
                );
    }

    public PharmaGroup updatePharmaGroup(PharmaGroup pharmaGroup, AppUser user) {
        PharmaGroup update = pharmaGroupRepository.save(pharmaGroup);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deletePharmaGroupById(Long id) {
        pharmaGroupRepository.deletePharmaGroupById(id);
    }

}
