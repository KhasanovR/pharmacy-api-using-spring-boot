package com.example.pharmacy.cashier.repository;

import com.example.pharmacy.cashier.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(Long id);
    void deleteById(Long id);
}
