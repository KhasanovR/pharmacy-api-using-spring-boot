package com.example.pharmacy.management.controller;

import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.management.model.InventoryItem;
import com.example.pharmacy.management.model.InventoryItemHistory;
import com.example.pharmacy.management.service.InventoryItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/branches/{branchId}/inventory/{inventoryId}/inventory-items")
@Slf4j
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;
    private final AppUserService appUserService;

    @Autowired
    public InventoryItemController(InventoryItemService inventoryItemService, AppUserService appUserService) {
        this.inventoryItemService = inventoryItemService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findInventoryItems(@PathVariable("branchId") Long branchId, @PathVariable("inventoryId") Long inventoryId) {
        Collection<InventoryItem> inventoryItems = this.inventoryItemService.getInventoryItemsByIventory(inventoryId);
        log.info("listing inventory items: {}", inventoryItems);
        return ResponseEntity.ok().body(inventoryItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findInventoryItem(@PathVariable("branchId") Long branchId, @PathVariable("inventoryId") Long inventoryId, @PathVariable("id") Long id) {
        InventoryItem inventoryItem = this.inventoryItemService.findInventoryItem(id);
        log.info("listing inventory item: {}", inventoryItem);
        return ResponseEntity.ok().body(inventoryItem);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<?> findHistory(@PathVariable("branchId") Long branchId, @PathVariable("inventoryId") Long inventoryId, @PathVariable("id") Long id) {
        Collection<InventoryItemHistory> inventoryItemHistories = this.inventoryItemService.retrieveHistory(id);
        log.info("listing history of inventory item: {}", inventoryItemHistories);
        return ResponseEntity.ok().body(inventoryItemHistories);
    }

    @PostMapping("/{id}/history/add")
    public ResponseEntity<?> addHistory(@PathVariable("branchId") Long branchId, @PathVariable("inventoryId") Long inventoryId, @PathVariable("id") Long id) {
        // TODO:
        log.info("adding history into inventory item with id: {}", id);
        return ResponseEntity.ok().build();
    }

}