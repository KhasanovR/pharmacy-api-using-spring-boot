package com.example.pharmacy.cashier.exception;

public class OrderCancelledException extends RuntimeException {
    public OrderCancelledException(String message) {
        super(message);
    }
}
