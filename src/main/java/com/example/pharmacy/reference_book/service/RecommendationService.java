package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.reference_book.exception.RecommendationNotFoundException;
import com.example.pharmacy.reference_book.model.Recommendation;
import com.example.pharmacy.reference_book.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public Recommendation saveRecommendation(Recommendation recommendation, AppUser user) {
        Recommendation save = recommendationRepository.save(recommendation);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<Recommendation> getRecommendations() {
        return recommendationRepository.findAll();
    }

    public Recommendation findRecommendationById(Long id) {
        return recommendationRepository
                .findById(id)
                .orElseThrow(
                        () -> new RecommendationNotFoundException("Recommendation by id " + id + " was not found")
                );
    }

    public Recommendation updateRecommendation(Recommendation recommendation, AppUser user) {
        Recommendation update = recommendationRepository.save(recommendation);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteRecommendationById(Long id) {
        recommendationRepository.deleteRecommendationById(id);
    }

}
