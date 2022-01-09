package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.reference_book.model.Manufacturer;
import com.example.pharmacy.reference_book.service.ManufacturerService;
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
@RequestMapping("/api/manufacturers")
@Slf4j
public class ManufacturerController {

    private final ManufacturerService manufacturerService;
    private final AppUserService appUserService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService, AppUserService appUserService) {
        this.manufacturerService = manufacturerService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findManufacturers() {
        Collection<Manufacturer> manufacturers = this.manufacturerService.getManufacturers();
        log.info("listing manufacturers: {}", manufacturers);
        return ResponseEntity.ok().body(manufacturers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findManufacturer(@PathVariable("id") Long id) {
        Manufacturer drug = this.manufacturerService.findManufacturerById(id);
        log.info("listing manufacturer: {}", drug);
        return ResponseEntity.ok().body(drug);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerManufacturer(@RequestBody Manufacturer manufacturer, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding manufacturer: {}", manufacturer);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Manufacturer savedManufacturer = this.manufacturerService.saveManufacturer(manufacturer, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/manufacturers/").toUriString()
                + savedManufacturer.getId());
        return ResponseEntity.created(uri).body(savedManufacturer);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateManufacturer(@RequestBody Manufacturer manufacturer, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating manufacturer: {}", manufacturer);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Manufacturer updatedManufacturer = this.manufacturerService.updateManufacturer(manufacturer, user);
        return ResponseEntity.ok().body(updatedManufacturer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeManufacturer(@PathVariable("id") Long id){
        log.info("deleting manufacturer with id: {}", id);
        this.manufacturerService.deleteManufacturerById(id);
        return ResponseEntity.ok().build();
    }
}