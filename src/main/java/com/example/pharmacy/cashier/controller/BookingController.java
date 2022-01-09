package com.example.pharmacy.cashier.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.cashier.model.Booking;
import com.example.pharmacy.cashier.service.BookingService;
import com.example.pharmacy.core.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/branch/{branchId}/bookings")
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    private final AppUserService appUserService;

    @Autowired
    public BookingController(BookingService bookingService, AppUserService appUserService) {
        this.bookingService = bookingService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findInventories(@PathVariable("branchId") Long branchId) {
        Collection<Booking> bookings = this.bookingService.getBookingsByBranch(branchId);
        log.info("listing bookings: {}", bookings);
        return ResponseEntity.ok().body(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBooking(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id) {
        Booking booking = this.bookingService.findBookingById(id);
        log.info("listing booking: {}", booking);
        return ResponseEntity.ok().body(booking);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerBooking(@PathVariable("branchId") Long branchId, @RequestBody Booking booking, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding country: {}", booking);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Booking saveBooking = this.bookingService.saveBooking(branchId, booking, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/branches/").toUriString()
                + branchId + "/bookings/"
                + saveBooking.getId());
        return ResponseEntity.created(uri).body(saveBooking);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> finishBooking(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("accepting booking with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.bookingService.finish(id, user);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("cancelling booking with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.bookingService.cancel(id, user);
        return ResponseEntity.ok().build();
    }

}