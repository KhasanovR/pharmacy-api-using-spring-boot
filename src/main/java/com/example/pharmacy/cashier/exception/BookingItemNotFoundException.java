package com.example.pharmacy.cashier.exception;

public class BookingItemNotFoundException extends RuntimeException {
    public BookingItemNotFoundException(String message) {
        super(message);
    }
}
