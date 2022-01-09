package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.reference_book.exception.DrugNotFoundException;
import com.example.pharmacy.reference_book.model.Drug;
import com.example.pharmacy.reference_book.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class DrugService {
    private final DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public Drug saveDrug(Drug drug, AppUser user) {
        Drug save = drugRepository.save(drug);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<Drug> getDrugs() {
        return drugRepository.findAll();
    }

    public Drug findDrugById(Long id) {
        return drugRepository
                .findById(id)
                .orElseThrow(
                        () -> new DrugNotFoundException("Drug by id " + id + " was not found")
                );
    }

    public Drug updateDrug(Drug drug, AppUser user) {
        Drug update = drugRepository.save(drug);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteDrugById(Long id) {
        drugRepository.deleteDrugById(id);
    }
}
