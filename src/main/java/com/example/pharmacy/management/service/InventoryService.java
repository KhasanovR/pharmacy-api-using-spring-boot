package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.core.util.ThrowableChecker;
import com.example.pharmacy.management.exception.InventoryItemNotCheckedErrorException;
import com.example.pharmacy.management.exception.InventoryNotFoundException;
import com.example.pharmacy.management.exception.ReceiptNotFoundException;
import com.example.pharmacy.management.model.Inventory;
import com.example.pharmacy.management.model.InventoryItem;
import com.example.pharmacy.management.model.Receipt;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class InventoryService {
    private final BranchRepository branchRepository;
    private final InventoryRepository inventoryRepository;
    private final ThrowableChecker throwableChecker;

    @Autowired
    public InventoryService(BranchRepository branchRepository, InventoryRepository inventoryRepository, ThrowableChecker throwableChecker) {
        this.branchRepository = branchRepository;
        this.inventoryRepository = inventoryRepository;
        this.throwableChecker = throwableChecker;
    }


    public Collection<Inventory> getInventoriesByBranch(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new InventoryNotFoundException("Branch by id " + branchId + " was not found")
        ).getInventories();
    }

    public Inventory findInventory(Long id) {
        return inventoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new InventoryNotFoundException("Inventory by id " + id + " was not found")
                );
    }

    public Inventory saveInventory(Long branchId, Inventory receipt) {
        throwableChecker.throwIfAnyActiveInventory();
        Inventory save = inventoryRepository.save(receipt);
        branchRepository.findById(branchId).orElseThrow(
                () -> new InventoryNotFoundException("Branch by id " + branchId + " was not found")
        ).getInventories().add(save);
        return save;
    }

    public void finish(Long id, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Inventory inventory = inventoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new InventoryNotFoundException("Inventory by id " + id + " was not found")
                );
        Set<InventoryItem> inventoryItems = inventory.getInventoryItems();
        inventoryItems.forEach(inventoryItem -> {
            if(inventoryItem.getInventoryItemHistories().size() > 0)
                throw new InventoryItemNotCheckedErrorException("This Inventory cannot be finished until each item in this inventory has been checked!");
        });
        inventory.setFinishedAt(Instant.now());
        inventory.setFinishedBy(user);
    }

    public void cancel(Long id, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Inventory inventory = inventoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new InventoryNotFoundException("Inventory by id " + id + " was not found")
                );
        inventory.setCancelledAt(Instant.now());
        inventory.setCancelledBy(user);
    }
}
