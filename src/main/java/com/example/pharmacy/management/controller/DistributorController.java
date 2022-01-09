package com.example.pharmacy.management.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.management.model.Distributor;
import com.example.pharmacy.management.service.DistributorService;
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
@RequestMapping("/api/distributors")
@Slf4j
public class DistributorController {

    private final DistributorService distributorService;
    private final AppUserService appUserService;

    @Autowired
    public DistributorController(DistributorService distributorService, AppUserService appUserService) {
        this.distributorService = distributorService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findDistributors() {
        Collection<Distributor> distributors = this.distributorService.getDistributors();
        log.info("listing distributors: {}", distributors);
        return ResponseEntity.ok().body(distributors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDistributor(@PathVariable("id") Long id) {
        Distributor distributor = this.distributorService.findDistributor(id);
        log.info("listing distributor: {}", distributor);
        return ResponseEntity.ok().body(distributor);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerDistributor(@RequestBody Distributor distributor, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding distributor: {}", distributor);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Distributor savedDistributor = this.distributorService.saveDistributor(distributor, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/distributors/").toUriString()
                + savedDistributor.getId());
        return ResponseEntity.created(uri).body(savedDistributor);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDistributor(@RequestBody Distributor distributor, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating distributor: {}", distributor);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Distributor updatedDistributor = this.distributorService.updateDistributor(distributor, user);
        return ResponseEntity.ok().body(updatedDistributor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeDistributor(@PathVariable("id") Long id){
        log.info("deleting distributor with id: {}", id);
        this.distributorService.deleteDistributorById(id);
        return ResponseEntity.ok().build();
    }
}