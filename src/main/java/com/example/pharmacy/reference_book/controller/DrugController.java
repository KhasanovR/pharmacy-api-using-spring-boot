package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.reference_book.model.Drug;
import com.example.pharmacy.reference_book.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/drugs")
@Slf4j
public class DrugController {

    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findDrugs() {
        Collection<Drug> drugs = this.drugService.getDrugs();
        log.info("listing: {}", drugs);
        return ResponseEntity.ok().body(drugs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDrug(@PathVariable("id") Long id) {
        Drug drug = this.drugService.findDrugById(id);
        log.info("listing: {}", drug);
        return ResponseEntity.ok().body(drug);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerDrug(@RequestBody Drug drug) {
        Drug savedDrug = this.drugService.saveDrug(drug);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/drugs/").toUriString()
                + savedDrug.getId());
        return ResponseEntity.created(uri).body(savedDrug);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDrug(@RequestBody Drug drug) {
        Drug updatedDrug = this.drugService.updateDrug(drug);
        return ResponseEntity.ok().body(updatedDrug);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeDrug(@PathVariable("id") Long id){
        this.drugService.deleteDrugById(id);
        return ResponseEntity.ok().build();
    }
}