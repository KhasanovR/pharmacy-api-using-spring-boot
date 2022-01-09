package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.reference_book.model.Drug;
import com.example.pharmacy.reference_book.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/drugs")
@Slf4j
public class DrugController {

    private final DrugService drugService;
    private final AppUserService appUserService;

    @Autowired
    public DrugController(DrugService drugService, AppUserService appUserService) {
        this.drugService = drugService;
        this.appUserService = appUserService;
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
    public ResponseEntity<?> registerDrug(@RequestBody Drug drug, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding drug: {}", drug);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Drug savedDrug = this.drugService.saveDrug(drug, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/drugs/").toUriString()
                + savedDrug.getId());
        return ResponseEntity.created(uri).body(savedDrug);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDrug(@RequestBody Drug drug, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating drug: {}", drug);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Drug updatedDrug = this.drugService.updateDrug(drug, user);
        return ResponseEntity.ok().body(updatedDrug);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeDrug(@PathVariable("id") Long id){
        log.info("deleting drug: {}", id);
        this.drugService.deleteDrugById(id);
        return ResponseEntity.ok().build();
    }
}