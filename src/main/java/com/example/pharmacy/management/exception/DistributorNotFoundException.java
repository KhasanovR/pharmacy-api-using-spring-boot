package com.example.pharmacy.management.exception;

public class DistributorNotFoundException extends RuntimeException {
    public DistributorNotFoundException(String message) {
        super(message);
    }
}
