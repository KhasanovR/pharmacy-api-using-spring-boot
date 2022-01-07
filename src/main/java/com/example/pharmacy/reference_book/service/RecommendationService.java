package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.reference_book.exception.RecommendationNotFoundException;
import com.example.pharmacy.reference_book.model.Recommendation;
import com.example.pharmacy.reference_book.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public Recommendation saveRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
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

    public Recommendation findRecommendationByName(String name) {
        return recommendationRepository
                .findByName(name)
                .orElseThrow(
                        () -> new RecommendationNotFoundException("Recommendation by name " + name + " was not found")
                );
    }

    public Recommendation updateRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public void deleteRecommendationById(Long id) {
        recommendationRepository.deleteRecommendationById(id);
    }

    public void deleteRecommendationByName(String name) {
        recommendationRepository.deleteRecommendationByName(name);
    }
}
