package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.reference_book.model.Unit;
import com.example.pharmacy.reference_book.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/units")
@Slf4j
public class UnitController {

    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findUnits() {
    Collection<Unit> units = this.unitService.getUnits();
        log.info("listing: {}", units);
        return ResponseEntity.ok().body(units);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUnit(@PathVariable("id") Long id) {
        Unit unit = this.unitService.findUnitById(id);
        log.info("listing: {}", unit);
        return ResponseEntity.ok().body(unit);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerUnit(@RequestBody Unit unit) {
        Unit savedUnit = this.unitService.saveUnit(unit);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/units/").toUriString()
                + savedUnit.getId());
        return ResponseEntity.created(uri).body(savedUnit);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUnit(@RequestBody Unit unit) {
        Unit updatedUnit = this.unitService.updateUnit(unit);
        return ResponseEntity.ok().body(updatedUnit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeUnit(@PathVariable("id") Long id){
        this.unitService.deleteUnitById(id);
        return ResponseEntity.ok().build();
    }
}