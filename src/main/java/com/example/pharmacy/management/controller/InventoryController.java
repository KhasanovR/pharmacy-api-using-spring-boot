package com.example.pharmacy.management.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.management.model.Inventory;
import com.example.pharmacy.management.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/branch/{branchId}/inventories")
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;
    private final AppUserService appUserService;

    @Autowired
    public InventoryController(InventoryService inventoryService, AppUserService appUserService) {
        this.inventoryService = inventoryService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findInventories(@PathVariable("branchId") Long branchId) {
        Collection<Inventory> inventories = this.inventoryService.getInventoriesByBranch(branchId);
        log.info("listing inventories: {}", inventories);
        return ResponseEntity.ok().body(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findInventory(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id) {
        Inventory inventory = this.inventoryService.findInventory(id);
        log.info("listing inventory: {}", inventory);
        return ResponseEntity.ok().body(inventory);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptReceipt(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("accepting inventory with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.inventoryService.finish(id, user);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReceipt(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("cancelling inventory with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.inventoryService.cancel(id, user);
        return ResponseEntity.ok().build();
    }

}