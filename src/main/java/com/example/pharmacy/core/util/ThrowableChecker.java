package com.example.pharmacy.core.util;

import com.example.pharmacy.core.exception.ActiveInventoryException;
import com.example.pharmacy.management.service.InventoryService;
import org.springframework.stereotype.Component;

@Component
public class ThrowableChecker {
    private final InventoryService inventoryService;

    public ThrowableChecker(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void throwIfAnyActiveInventory(){
        if (this.inventoryService.isAnyActive()) {
            throw new ActiveInventoryException("Sorry, but while there is an active inventory, this action cannot be processed!");
        }
    }
}
