package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.reference_book.model.PharmaGroup;
import com.example.pharmacy.reference_book.service.PharmaGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/pharma-groups")
@Slf4j
public class PharmaGroupController {

    private final PharmaGroupService pharmaGroupService;

    @Autowired
    public PharmaGroupController(PharmaGroupService pharmaGroupService) {
        this.pharmaGroupService = pharmaGroupService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findPharmaGroups() {
        Collection<PharmaGroup> pharmaGroups = this.pharmaGroupService.getPharmaGroups();
        log.info("listing: {}", pharmaGroups);
        return ResponseEntity.ok().body(pharmaGroups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPharmaGroup(@PathVariable("id") Long id) {
        PharmaGroup pharmaGroup = this.pharmaGroupService.findPharmaGroupById(id);
        log.info("listing: {}", pharmaGroup);
        return ResponseEntity.ok().body(pharmaGroup);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerPharmaGroup(@RequestBody PharmaGroup drug) {
        PharmaGroup savedPharmaGroup = this.pharmaGroupService.savePharmaGroup(drug);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/pharma-groups/").toUriString()
                + savedPharmaGroup.getId());
        return ResponseEntity.created(uri).body(savedPharmaGroup);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePharmaGroup(@RequestBody PharmaGroup drug) {
        PharmaGroup updatedPharmaGroup = this.pharmaGroupService.updatePharmaGroup(drug);
        return ResponseEntity.ok().body(updatedPharmaGroup);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removePharmaGroup(@PathVariable("id") Long id){
        this.pharmaGroupService.deletePharmaGroupById(id);
        return ResponseEntity.ok().build();
    }
}