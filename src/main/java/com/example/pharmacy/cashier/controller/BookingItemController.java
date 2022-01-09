package com.example.pharmacy.cashier.controller;

import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.cashier.model.BookingItem;
import com.example.pharmacy.cashier.service.BookingItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/branches/{branchId}/bookings/{bookingId}/booking-items")
@Slf4j
public class BookingItemController {

    private final BookingItemService bookingItemService;

    @Autowired
    public BookingItemController(BookingItemService bookingItemService, AppUserService appUserService) {
        this.bookingItemService = bookingItemService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findBookingItems(@PathVariable("branchId") Long branchId, @PathVariable("bookingId") Long bookingId) {
        Collection<BookingItem> bookingItems = this.bookingItemService.getBookingItemsByBooking(bookingId);
        log.info("listing booking items: {}", bookingItems);
        return ResponseEntity.ok().body(bookingItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBookingItem(@PathVariable("branchId") Long branchId, @PathVariable("bookingId") Long bookingId, @PathVariable("id") Long id) {
        BookingItem BookingItem = this.bookingItemService.findBookingItem(id);
        log.info("listing booking item: {}", BookingItem);
        return ResponseEntity.ok().body(BookingItem);
    }
}