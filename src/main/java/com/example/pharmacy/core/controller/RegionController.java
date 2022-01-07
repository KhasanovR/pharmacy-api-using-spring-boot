package com.example.pharmacy.core.controller;

import com.example.pharmacy.core.model.Region;
import com.example.pharmacy.core.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/regions")
@Slf4j
public class RegionController {

    private final RegionService RegionService;

    @Autowired
    public RegionController(RegionService RegionService) {
        this.RegionService = RegionService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findRegions() {
        Collection<Region> regions = this.RegionService.getCountries();
        log.info("listing: {}", regions);
        return ResponseEntity.ok().body(regions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRegion(@PathVariable("id") Long id) {
        Region Region = this.RegionService.findRegion(id);
        log.info("listing: {}", Region);
        return ResponseEntity.ok().body(Region);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerRegion(@RequestBody Region Region) {
        Region savedRegion = this.RegionService.saveRegion(Region);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/regions/").toUriString()
                + savedRegion.getId());
        return ResponseEntity.created(uri).body(savedRegion);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRegion(@RequestBody Region Region) {
        Region updatedRegion = this.RegionService.updateRegion(Region);
        return ResponseEntity.ok().body(updatedRegion);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeRegion(@PathVariable("id") Long id){
        this.RegionService.deleteRegionById(id);
        return ResponseEntity.ok().build();
    }
}