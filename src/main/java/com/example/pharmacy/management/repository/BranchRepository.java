package com.example.pharmacy.management.repository;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.management.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findById(Long id);
    void deleteBranchById(Long id);
}
