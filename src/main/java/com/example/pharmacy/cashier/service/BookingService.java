package com.example.pharmacy.cashier.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.cashier.exception.BookingCancelledException;
import com.example.pharmacy.cashier.exception.BookingFinishedException;
import com.example.pharmacy.cashier.exception.BookingNotFoundException;
import com.example.pharmacy.cashier.model.Booking;
import com.example.pharmacy.cashier.repository.BookingRepository;
import com.example.pharmacy.management.enums.ProductEventType;
import com.example.pharmacy.management.exception.ReceiptNotFoundException;
import com.example.pharmacy.management.model.ProductEvent;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.ProductEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BranchRepository branchRepository;
    private final ProductEventRepository productEventRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BranchRepository branchRepository, ProductEventRepository productEventRepository) {
        this.bookingRepository = bookingRepository;
        this.branchRepository = branchRepository;
        this.productEventRepository = productEventRepository;
    }

    public Booking saveBooking(Long branchId, Booking booking, AppUser user) {
        Booking save = bookingRepository.save(booking);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        branchRepository.findById(branchId).orElseThrow(
                () -> new ReceiptNotFoundException("Branch by id " + branchId + " was not found")
        ).getBookings().add(save);
        return save;
    }

    public Collection<Booking> getBookingsByBranch(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new ReceiptNotFoundException("Branch by id " + branchId + " was not found")
        ).getBookings();
    }

    public Booking findBookingById(Long id) {
        return bookingRepository
                .findById(id)
                .orElseThrow(
                        () -> new BookingNotFoundException("Booking by id " + id + " was not found")
                );
    }

    public void finish(Long id, AppUser user) {
        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(
                        () -> new BookingNotFoundException("Booking by id " + id + " was not found")
                );
        if (booking.isCancelled())
            throw new BookingCancelledException("Booking by id " + id + " has been already cancelled");
        if (booking.isFinished())
            throw new BookingFinishedException("Booking by id " + id + " has been already finished");
        booking.getBookingItems().forEach(bookingItem -> {
            float price = bookingItem.getReceiptItem().getPrice() * bookingItem.getQuantity();
            ProductEvent productEvent = new ProductEvent();
            productEvent.setReceiptItem(bookingItem.getReceiptItem());
            productEvent.setType(ProductEventType.BOOKING_FINISH.name());
            productEvent.setQuantity(-1*bookingItem.getQuantity());
            productEvent.setCreatedAt(Instant.now());
            productEvent.setCreatedBy(user);
            productEventRepository.save(productEvent);
        });
        booking.setFinishedAt(Instant.now());
        booking.setFinishedBy(user);
    }

    public void cancel(Long id, AppUser user) {
        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(
                        () -> new BookingNotFoundException("Booking by id " + id + " was not found")
                );
        if (booking.isCancelled())
            throw new BookingCancelledException("Booking by id " + id + " has been already cancelled");
        if (booking.isFinished())
            throw new BookingFinishedException("Booking by id " + id + " has been already finished");
        booking.setCancelledAt(Instant.now());
        booking.setCancelledBy(user);
    }

}
