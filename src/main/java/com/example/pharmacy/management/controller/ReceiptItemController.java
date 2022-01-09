package com.example.pharmacy.management.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.management.model.ReceiptItem;
import com.example.pharmacy.management.service.ReceiptItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/branches/{branchId}/receipts/{receiptId}/receipt-items")
@Slf4j
public class ReceiptItemController {

    private final ReceiptItemService receiptItemService;
    private final AppUserService appUserService;

    @Autowired
    public ReceiptItemController(ReceiptItemService receiptItemService, AppUserService appUserService) {
        this.receiptItemService = receiptItemService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findReceiptItems(@PathVariable("branchId") Long branchId, @PathVariable("receiptId") Long receiptId) {
        Collection<ReceiptItem> receiptItems = this.receiptItemService.getReceiptItemsByBranch(branchId);
        log.info("listing receipt items: {}", receiptItems);
        return ResponseEntity.ok().body(receiptItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findReceiptItem(@PathVariable("branchId") Long branchId, @PathVariable("receiptId") Long receiptId, @PathVariable("id") Long id) {
        ReceiptItem receiptItem = this.receiptItemService.findReceiptItem(id);
        log.info("listing receipt item: {}", receiptItem);
        return ResponseEntity.ok().body(receiptItem);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerReceiptItem(@PathVariable("branchId") Long branchId, @PathVariable("receiptId") Long receiptId, @RequestBody ReceiptItem receiptItem, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding receipt item: {}", receiptItem);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        ReceiptItem saveReceiptItem = this.receiptItemService.saveReceiptItem(branchId, receiptId, receiptItem, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/branches/").toUriString()
                + branchId + "/receipts/"
                + receiptId + "/receipt-items/"
                + saveReceiptItem.getId());
        return ResponseEntity.created(uri).body(saveReceiptItem);
    }

    @PostMapping("/depreciate/{id}")
    public ResponseEntity<?> depreciate(@PathVariable("branchId") Long branchId, @PathVariable("receiptId") Long receiptId, @PathVariable("id") Long id, Integer quantity, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("depreciating receipt item with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.receiptItemService.depreciate(id, quantity, user);
        return ResponseEntity.ok().build();
    }
}