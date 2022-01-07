package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.reference_book.model.Manufacturer;
import com.example.pharmacy.reference_book.service.ManufacturerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/manufacturers")
@Slf4j
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findManufacturers() {
        Collection<Manufacturer> manufacturers = this.manufacturerService.getManufacturers();
        log.info("listing: {}", manufacturers);
        return ResponseEntity.ok().body(manufacturers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findManufacturer(@PathVariable("id") Long id) {
        Manufacturer drug = this.manufacturerService.findManufacturerById(id);
        log.info("listing: {}", drug);
        return ResponseEntity.ok().body(drug);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer savedManufacturer = this.manufacturerService.saveManufacturer(manufacturer);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/manufacturers/").toUriString()
                + savedManufacturer.getId());
        return ResponseEntity.created(uri).body(savedManufacturer);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer updatedManufacturer = this.manufacturerService.updateManufacturer(manufacturer);
        return ResponseEntity.ok().body(updatedManufacturer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeManufacturer(@PathVariable("id") Long id){
        this.manufacturerService.deleteManufacturerById(id);
        return ResponseEntity.ok().build();
    }
}