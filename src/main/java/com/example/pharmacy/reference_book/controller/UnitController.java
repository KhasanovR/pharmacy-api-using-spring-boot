package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.reference_book.model.Unit;
import com.example.pharmacy.reference_book.service.UnitService;
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
@RequestMapping("/api/units")
@Slf4j
public class UnitController {

    private final UnitService unitService;
    private final AppUserService appUserService;

    @Autowired
    public UnitController(UnitService unitService, AppUserService appUserService) {
        this.unitService = unitService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findUnits() {
    Collection<Unit> units = this.unitService.getUnits();
        log.info("listing units: {}", units);
        return ResponseEntity.ok().body(units);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUnit(@PathVariable("id") Long id) {
        Unit unit = this.unitService.findUnitById(id);
        log.info("listing unit: {}", unit);
        return ResponseEntity.ok().body(unit);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerUnit(@RequestBody Unit unit, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding unit: {}", unit);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Unit savedUnit = this.unitService.saveUnit(unit, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/units/").toUriString()
                + savedUnit.getId());
        return ResponseEntity.created(uri).body(savedUnit);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUnit(@RequestBody Unit unit, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating unit: {}", unit);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Unit updatedUnit = this.unitService.updateUnit(unit, user);
        return ResponseEntity.ok().body(updatedUnit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeUnit(@PathVariable("id") Long id){
        log.info("deleting unit with id: {}", id);
        this.unitService.deleteUnitById(id);
        return ResponseEntity.ok().build();
    }
}