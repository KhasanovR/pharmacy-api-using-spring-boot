package com.example.pharmacy.reference_book.exception;

public class DrugNotFoundException extends RuntimeException {
    public DrugNotFoundException(String message) {
        super(message);
    }
}
