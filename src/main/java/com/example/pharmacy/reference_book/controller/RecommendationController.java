package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.reference_book.model.Recommendation;
import com.example.pharmacy.reference_book.service.RecommendationService;
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
@RequestMapping("/api/recommendations")
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final AppUserService appUserService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService, AppUserService appUserService) {
        this.recommendationService = recommendationService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findRecommendations() {
        Collection<Recommendation> recommendations = this.recommendationService.getRecommendations();
        log.info("listing recommendations: {}", recommendations);
        return ResponseEntity.ok().body(recommendations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRecommendation(@PathVariable("id") Long id) {
        Recommendation recommendation = this.recommendationService.findRecommendationById(id);
        log.info("listing recommendation: {}", recommendation);
        return ResponseEntity.ok().body(recommendation);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerRecommendation(@RequestBody Recommendation recommendation, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding recommendation: {}", recommendation);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Recommendation savedRecommendation = this.recommendationService.saveRecommendation(recommendation, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/recommendations/").toUriString()
                + savedRecommendation.getId());
        return ResponseEntity.created(uri).body(savedRecommendation);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRecommendation(@RequestBody Recommendation recommendation, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating recommendation: {}", recommendation);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Recommendation updatedRecommendation = this.recommendationService.updateRecommendation(recommendation, user);
        return ResponseEntity.ok().body(updatedRecommendation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeRecommendation(@PathVariable("id") Long id){
        log.info("deleting recommendation with id: {}", id);
        this.recommendationService.deleteRecommendationById(id);
        return ResponseEntity.ok().build();
    }
}