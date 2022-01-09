package com.example.pharmacy.management.exception;

public class ReceiptItemNotFoundException extends RuntimeException {
    public ReceiptItemNotFoundException(String message) {
        super(message);
    }
}
