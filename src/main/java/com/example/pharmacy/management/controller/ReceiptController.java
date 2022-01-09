package com.example.pharmacy.management.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.management.model.Receipt;
import com.example.pharmacy.management.service.ReceiptService;
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
@RequestMapping("/api/branch/{branchId}/receipts")
@Slf4j
public class ReceiptController {

    private final ReceiptService receiptService;
    private final AppUserService appUserService;

    @Autowired
    public ReceiptController(ReceiptService receiptService, AppUserService appUserService) {
        this.receiptService = receiptService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findReceipts(@PathVariable("branchId") Long branchId) {
        Collection<Receipt> receipts = this.receiptService.getReceiptsByBranch(branchId);
        log.info("listing receipts: {}", receipts);
        return ResponseEntity.ok().body(receipts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findReceipt(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id) {
        Receipt receipt = this.receiptService.findReceipt(id);
        log.info("listing receipt: {}", receipt);
        return ResponseEntity.ok().body(receipt);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerReceipt(@PathVariable("branchId") Long branchId, @RequestBody Receipt receipt, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding receipt: {}", receipt);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Receipt saveReceipt = this.receiptService.saveReceipt(branchId, receipt, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/branches/").toUriString()
                +branchId+"/receipts/"
                + saveReceipt.getId());
        return ResponseEntity.created(uri).body(saveReceipt);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateReceipt(@PathVariable("branchId") Long branchId, @RequestBody Receipt receipt, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding receipt: {}", receipt);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Receipt updateReceiptReceipt = this.receiptService.updateReceipt(receipt, user);
        return ResponseEntity.ok().body(updateReceiptReceipt);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptReceipt(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("accepting: receipt with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.receiptService.accept(id, user);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReceipt(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("cancelling: receipt with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.receiptService.cancel(id, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> performDestroy(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id) {
        log.info("destroying: receipt with id {} and its items", id);
        this.receiptService.performDestroy(id);
        return ResponseEntity.ok().build();
    }
}