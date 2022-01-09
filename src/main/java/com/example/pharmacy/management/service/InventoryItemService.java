package com.example.pharmacy.management.service;

import com.example.pharmacy.management.exception.BranchNotFoundException;
import com.example.pharmacy.management.exception.InventoryItemNotFoundException;
import com.example.pharmacy.management.exception.InventoryNotFoundException;
import com.example.pharmacy.management.model.InventoryItem;
import com.example.pharmacy.management.model.InventoryItemHistory;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.InventoryItemHistoryRepository;
import com.example.pharmacy.management.repository.InventoryItemRepository;
import com.example.pharmacy.management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class InventoryItemService {
    private final BranchRepository branchRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryItemRepository inventoryItemRepository;

    @Autowired
    public InventoryItemService(BranchRepository branchRepository, InventoryRepository inventoryRepository, InventoryItemRepository inventoryItemRepository) {
        this.branchRepository = branchRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }


    public Collection<InventoryItem> getInventoryItemsByBranch(Long InventoryId) {
        return inventoryRepository.findById(InventoryId).orElseThrow(
                () -> new InventoryNotFoundException("Inventory by id " + InventoryId + " was not found")
        ).getInventoryItems();
    }

    public InventoryItem findInventoryItem(Long id) {
        return inventoryItemRepository
                .findById(id)
                .orElseThrow(
                        () -> new InventoryItemNotFoundException("Inventory Item by id " + id + " was not found")
                );
    }

    public Collection<InventoryItemHistory> retrieveHistory(Long inventoryItemId){
        return inventoryItemRepository.findById(inventoryItemId).orElseThrow(
                () -> new BranchNotFoundException("Inventory Item by id " + inventoryItemId + " was not found")
        ).getInventoryItemHistories();
    }

}
