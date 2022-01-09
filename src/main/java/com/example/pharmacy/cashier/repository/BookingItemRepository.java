package com.example.pharmacy.cashier.repository;

import com.example.pharmacy.cashier.model.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {
    Optional<BookingItem> findById(Long id);
    void deleteById(Long id);
}
