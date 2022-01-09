package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.reference_book.exception.UnitNotFoundException;
import com.example.pharmacy.reference_book.model.Unit;
import com.example.pharmacy.reference_book.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class UnitService {
    private final UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public Unit saveUnit(Unit Unit, AppUser user) {
        Unit save = unitRepository.save(Unit);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<Unit> getUnits() {
        return unitRepository.findAll();
    }

    public Unit findUnitById(Long id) {
        return unitRepository
                .findById(id)
                .orElseThrow(
                        () -> new UnitNotFoundException("Unit by id " + id + " was not found")
                );
    }

    public Unit updateUnit(Unit unit, AppUser user) {
        Unit update = unitRepository.save(unit);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteUnitById(Long id) {
        unitRepository.deleteUnitById(id);
    }

}
