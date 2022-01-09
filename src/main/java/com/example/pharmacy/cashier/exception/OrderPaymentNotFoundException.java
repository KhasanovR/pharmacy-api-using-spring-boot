package com.example.pharmacy.cashier.exception;

public class OrderPaymentNotFoundException extends RuntimeException {
    public OrderPaymentNotFoundException(String message) {
        super(message);
    }
}
