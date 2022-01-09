package com.example.pharmacy.management.enums;

public enum ProductEventType {
    ORDER_CREATE("order_create"),
    ORDER_CANCEL("order_cancel"),
    BOOKING_FINISH("booking_finish"),
    RECEIPT_ITEM_DEPRECIATE("receipt_item_depreciate"),
    RECEIPT_ACCEPT("receipt_accept");

    private final String action;

    ProductEventType(String action) {

        this.action = action;
    }
}
