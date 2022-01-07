package com.example.pharmacy.reference_book.controller;

import com.example.pharmacy.reference_book.model.Recommendation;
import com.example.pharmacy.reference_book.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/recommendations")
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findRecommendations() {
        Collection<Recommendation> recommendations = this.recommendationService.getRecommendations();
        log.info("listing: {}", recommendations);
        return ResponseEntity.ok().body(recommendations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRecommendation(@PathVariable("id") Long id) {
        Recommendation recommendation = this.recommendationService.findRecommendationById(id);
        log.info("listing: {}", recommendation);
        return ResponseEntity.ok().body(recommendation);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerRecommendation(@RequestBody Recommendation recommendation) {
        Recommendation savedRecommendation = this.recommendationService.saveRecommendation(recommendation);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/recommendations/").toUriString()
                + savedRecommendation.getId());
        return ResponseEntity.created(uri).body(savedRecommendation);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRecommendation(@RequestBody Recommendation recommendation) {
        Recommendation updatedRecommendation = this.recommendationService.updateRecommendation(recommendation);
        return ResponseEntity.ok().body(updatedRecommendation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeRecommendation(@PathVariable("id") Long id){
        this.recommendationService.deleteRecommendationById(id);
        return ResponseEntity.ok().build();
    }
}