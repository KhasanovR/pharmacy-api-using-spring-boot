package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.reference_book.exception.UnitNotFoundException;
import com.example.pharmacy.reference_book.model.Unit;
import com.example.pharmacy.reference_book.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class UnitService {
    private final UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public Unit saveUnit(Unit Unit) {
        return unitRepository.save(Unit);
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

    public Unit findUnitByName(String name) {
        return unitRepository
                .findByName(name)
                .orElseThrow(
                        () -> new UnitNotFoundException("Unit by name " + name + " was not found")
                );
    }

    public Unit updateUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public void deleteUnitById(Long id) {
        unitRepository.deleteUnitById(id);
    }

    public void deleteUnitByName(String name) {
        unitRepository.deleteUnitByName(name);
    }
}
