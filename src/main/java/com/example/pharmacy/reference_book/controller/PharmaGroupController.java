package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.reference_book.model.PharmaGroup;
import com.example.pharmacy.reference_book.service.PharmaGroupService;
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
@RequestMapping("/api/pharma-groups")
@Slf4j
public class PharmaGroupController {

    private final PharmaGroupService pharmaGroupService;
    private final AppUserService appUserService;

    @Autowired
    public PharmaGroupController(PharmaGroupService pharmaGroupService, AppUserService appUserService) {
        this.pharmaGroupService = pharmaGroupService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findPharmaGroups() {
        Collection<PharmaGroup> pharmaGroups = this.pharmaGroupService.getPharmaGroups();
        log.info("listing pharma groups: {}", pharmaGroups);
        return ResponseEntity.ok().body(pharmaGroups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPharmaGroup(@PathVariable("id") Long id) {
        PharmaGroup pharmaGroup = this.pharmaGroupService.findPharmaGroupById(id);
        log.info("listing pharma group: {}", pharmaGroup);
        return ResponseEntity.ok().body(pharmaGroup);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerPharmaGroup(@RequestBody PharmaGroup pharmaGroup, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding pharma group: {}", pharmaGroup);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        PharmaGroup savedPharmaGroup = this.pharmaGroupService.savePharmaGroup(pharmaGroup, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/pharma-groups/").toUriString()
                + savedPharmaGroup.getId());
        return ResponseEntity.created(uri).body(savedPharmaGroup);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePharmaGroup(@RequestBody PharmaGroup pharmaGroup, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating pharma group: {}", pharmaGroup);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        PharmaGroup updatedPharmaGroup = this.pharmaGroupService.updatePharmaGroup(pharmaGroup, user);
        return ResponseEntity.ok().body(updatedPharmaGroup);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removePharmaGroup(@PathVariable("id") Long id){
        log.info("deleting pharma group with id: {}", id);
        this.pharmaGroupService.deletePharmaGroupById(id);
        return ResponseEntity.ok().build();
    }
}