package com.example.pharmacy.reference_book.repository;

import com.example.pharmacy.reference_book.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Optional<Recommendation> findById(Long id);
    Optional<Recommendation> findByName(String name);
    Recommendation findRecommendationById(Long id);
    Recommendation findRecommendationByName(String name);
    void deleteRecommendationById(Long id);
    void deleteRecommendationByName(String name);
}
