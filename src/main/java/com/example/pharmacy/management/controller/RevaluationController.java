package com.example.pharmacy.management.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.management.model.Revaluation;
import com.example.pharmacy.management.service.RevaluationService;
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
@RequestMapping("/api/branch/{branchId}/revaluations")
@Slf4j
public class RevaluationController {

    private final RevaluationService revaluationService;
    private final AppUserService appUserService;

    @Autowired
    public RevaluationController(RevaluationService revaluationService, AppUserService appUserService) {
        this.revaluationService = revaluationService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findRevaluations(@PathVariable("branchId") Long branchId) {
        Collection<Revaluation> revaluations = this.revaluationService.getRevaluationsByBranch(branchId);
        log.info("listing revaluations: {}", revaluations);
        return ResponseEntity.ok().body(revaluations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRevaluation(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id) {
        Revaluation revaluation = this.revaluationService.findRevaluation(id);
        log.info("listing revaluation: {}", revaluation);
        return ResponseEntity.ok().body(revaluation);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerRevaluation(@PathVariable("branchId") Long branchId, @RequestBody Revaluation revaluation, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding revaluation: {}", revaluation);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Revaluation savedRevaluation = this.revaluationService.saveRevaluation(branchId, revaluation, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/Revaluations/").toUriString()
                + savedRevaluation.getId());
        return ResponseEntity.created(uri).body(savedRevaluation);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRevaluation(@PathVariable("branchId") Long branchId, @RequestBody Revaluation revaluation) {
        log.info("updating revaluation: {}", revaluation);
        Revaluation updatedRevaluation = this.revaluationService.updateRevaluation(revaluation);
        return ResponseEntity.ok().body(updatedRevaluation);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptRevaluation(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("accepting: revaluation with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.revaluationService.accept(id, user);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelRevaluation(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("cancelling: revaluation with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.revaluationService.cancel(id, user);
        return ResponseEntity.ok().build();
    }

}