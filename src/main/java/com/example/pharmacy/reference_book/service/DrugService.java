package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.reference_book.exception.DrugNotFoundException;
import com.example.pharmacy.reference_book.model.Drug;
import com.example.pharmacy.reference_book.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class DrugService {
    private final DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public Drug saveDrug(Drug drug) {
        return drugRepository.save(drug);
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

    public Drug findDrugByName(String name) {
        return drugRepository
                .findByName(name)
                .orElseThrow(
                        () -> new DrugNotFoundException("Drug by name " + name + " was not found")
                );
    }

    public Drug updateDrug(Drug drug) {
        return drugRepository.save(drug);
    }

    public void deleteDrugById(Long id) {
        drugRepository.deleteDrugById(id);
    }

    public void deleteDrugByName(String name) {
        drugRepository.deleteDrugByName(name);
    }
}
