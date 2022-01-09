package com.example.pharmacy.core.util;

import com.example.pharmacy.core.exception.ActiveInventoryException;
import com.example.pharmacy.management.repository.InventoryRepository;
import org.springframework.stereotype.Component;

@Component
public class ThrowableChecker {
    private final InventoryRepository inventoryRepository;

    public ThrowableChecker(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void throwIfAnyActiveInventory() {
        this.inventoryRepository.
                findAll()
                .forEach(
                        inventory -> {
                            if (inventory.isActive()) {
                                throw new ActiveInventoryException("Sorry, but while there is an active inventory, this action cannot be processed!");
                            }
                        }
                );
    }
}
