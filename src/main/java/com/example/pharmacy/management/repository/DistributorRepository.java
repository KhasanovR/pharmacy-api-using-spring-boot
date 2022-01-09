package com.example.pharmacy.management.repository;

import com.example.pharmacy.management.model.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {
    Optional<Distributor> findById(Long id);
    void deleteDistributorById(Long id);
}
