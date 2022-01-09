package com.example.pharmacy.management.exception;

public class InventoryItemNotCheckedErrorException extends RuntimeException {
    public InventoryItemNotCheckedErrorException(String message) {
        super(message);
    }
}
