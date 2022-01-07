package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.reference_book.model.InternationalName;
import com.example.pharmacy.reference_book.service.InternationalNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/international-names")
@Slf4j
public class InternationalNameController {

    private final InternationalNameService internationalNameService;

    @Autowired
    public InternationalNameController(InternationalNameService internationalNameService) {
        this.internationalNameService = internationalNameService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findInternationalNames() {
        Collection<InternationalName> internationalNames = this.internationalNameService.getInternationalNames();
        log.info("listing: {}", internationalNames);
        return ResponseEntity.ok().body(internationalNames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findInternationalName(@PathVariable("id") Long id) {
        InternationalName internationalName = this.internationalNameService.findInternationalNameById(id);
        log.info("listing: {}", internationalName);
        return ResponseEntity.ok().body(internationalName);
    }

    @GetMapping("/ru/{name_ru}")
    public ResponseEntity<?> findInternationalName_ru(@PathVariable("name_ru") String name_ru) {
        InternationalName internationalName = this.internationalNameService.findInternationalNameByName_ru(name_ru);
        log.info("listing: {}", internationalName);
        return ResponseEntity.ok().body(internationalName);
    }

    @GetMapping("/en/{name_en}")
    public ResponseEntity<?> findInternationalName_en(@PathVariable("name_en") String name_en) {
        InternationalName internationalName = this.internationalNameService.findInternationalNameByName_en(name_en);
        log.info("listing: {}", internationalName);
        return ResponseEntity.ok().body(internationalName);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerInternationalName(@RequestBody InternationalName drug) {
        InternationalName savedInternationalName = this.internationalNameService.saveInternationalName(drug);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/international-names/").toUriString()
                + savedInternationalName.getId());
        return ResponseEntity.created(uri).body(savedInternationalName);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInternationalName(@RequestBody InternationalName drug) {
        InternationalName updatedInternationalName = this.internationalNameService.updateInternationalName(drug);
        return ResponseEntity.ok().body(updatedInternationalName);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeInternationalName(@PathVariable("id") Long id){
        this.internationalNameService.deleteInternationalNameById(id);
        return ResponseEntity.ok().build();
    }
}