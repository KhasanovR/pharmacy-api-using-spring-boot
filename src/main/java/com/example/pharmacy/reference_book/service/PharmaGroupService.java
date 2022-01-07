package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.reference_book.exception.PharmaGroupNotFoundException;
import com.example.pharmacy.reference_book.model.PharmaGroup;
import com.example.pharmacy.reference_book.repository.PharmaGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class PharmaGroupService {
    private final PharmaGroupRepository pharmaGroupRepository;

    @Autowired
    public PharmaGroupService(PharmaGroupRepository pharmaGroupRepository) {
        this.pharmaGroupRepository = pharmaGroupRepository;
    }

    public PharmaGroup savePharmaGroup(PharmaGroup pharmaGroup) {
        return pharmaGroupRepository.save(pharmaGroup);
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

    public PharmaGroup findPharmaGroupByName(String name) {
        return pharmaGroupRepository
                .findByName(name)
                .orElseThrow(
                        () -> new PharmaGroupNotFoundException("PharmaGroup by name " + name + " was not found")
                );
    }

    public PharmaGroup updatePharmaGroup(PharmaGroup pharmaGroup) {
        return pharmaGroupRepository.save(pharmaGroup);
    }

    public void deletePharmaGroupById(Long id) {
        pharmaGroupRepository.deletePharmaGroupById(id);
    }

    public void deletePharmaGroupByName(String name) {
        pharmaGroupRepository.deletePharmaGroupByName(name);
    }
}
