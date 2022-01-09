package com.example.pharmacy.core.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.core.model.Region;
import com.example.pharmacy.core.service.RegionService;
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
@RequestMapping("/api/regions")
@Slf4j
public class RegionController {

    private final RegionService RegionService;
    private final AppUserService appUserService;

    @Autowired
    public RegionController(RegionService RegionService, AppUserService appUserService) {
        this.RegionService = RegionService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findRegions() {
        Collection<Region> regions = this.RegionService.getRegions();
        log.info("listing regions: {}", regions);
        return ResponseEntity.ok().body(regions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRegion(@PathVariable("id") Long id) {
        Region Region = this.RegionService.findRegion(id);
        log.info("listing region: {}", Region);
        return ResponseEntity.ok().body(Region);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerRegion(@RequestBody Region region, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding region: {}", region);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Region savedRegion = this.RegionService.saveRegion(region, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/regions/").toUriString()
                + savedRegion.getId());
        return ResponseEntity.created(uri).body(savedRegion);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRegion(@RequestBody Region region, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating region: {}", region);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Region updatedRegion = this.RegionService.updateRegion(region, user);
        return ResponseEntity.ok().body(updatedRegion);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeRegion(@PathVariable("id") Long id){
        log.info("deleting region with id: {}", id);
        this.RegionService.deleteRegionById(id);
        return ResponseEntity.ok().build();
    }
}