package com.example.pharmacy.cashier.service;

import com.example.pharmacy.cashier.exception.BookingItemNotFoundException;
import com.example.pharmacy.cashier.model.BookingItem;
import com.example.pharmacy.cashier.repository.BookingRepository;
import com.example.pharmacy.cashier.repository.BookingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class BookingItemService {
    private final BookingRepository bookingRepository;
    private final BookingItemRepository BookingItemRepository;

    @Autowired
    public BookingItemService(BookingRepository bookingRepository, BookingItemRepository BookingItemRepository) {
        this.bookingRepository = bookingRepository;
        this.BookingItemRepository = BookingItemRepository;
    }


    public Collection<BookingItem> getBookingItemsByBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(
                () -> new BookingItemNotFoundException("Booking by id " + bookingId + " was not found")
        ).getBookingItems();
    }

    public BookingItem findBookingItem(Long id) {
        return BookingItemRepository
                .findById(id)
                .orElseThrow(
                        () -> new BookingItemNotFoundException("BookingItem by id " + id + " was not found")
                );
    }
}
