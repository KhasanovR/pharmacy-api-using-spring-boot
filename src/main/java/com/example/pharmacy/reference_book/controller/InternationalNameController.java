package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.reference_book.model.InternationalName;
import com.example.pharmacy.reference_book.service.InternationalNameService;
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
@RequestMapping("/api/international-names")
@Slf4j
public class InternationalNameController {

    private final InternationalNameService internationalNameService;
    private final AppUserService appUserService;

    @Autowired
    public InternationalNameController(InternationalNameService internationalNameService, AppUserService appUserService) {
        this.internationalNameService = internationalNameService;
        this.appUserService = appUserService;
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

    @PostMapping("/add")
    public ResponseEntity<?> registerInternationalName(@RequestBody InternationalName internationalName, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding drug: {}", internationalName);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        InternationalName savedInternationalName = this.internationalNameService.saveInternationalName(internationalName, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/international-names/").toUriString()
                + savedInternationalName.getId());
        return ResponseEntity.created(uri).body(savedInternationalName);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInternationalName(@RequestBody InternationalName internationalName, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating internationalName: {}", internationalName);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        InternationalName updatedInternationalName = this.internationalNameService.updateInternationalName(internationalName, user);
        return ResponseEntity.ok().body(updatedInternationalName);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeInternationalNameById(@PathVariable("id") Long id){
        log.info("deleting international name with id: {}", id);
        this.internationalNameService.deleteInternationalNameById(id);
        return ResponseEntity.ok().build();
    }
}