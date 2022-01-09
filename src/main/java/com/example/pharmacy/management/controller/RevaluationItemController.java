package com.example.pharmacy.management.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.management.model.Product;
import com.example.pharmacy.management.model.RevaluationItem;
import com.example.pharmacy.management.service.RevaluationItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/branch/{branchId}/revaluations/{revaluationId}/revaluations")
@Slf4j
public class RevaluationItemController {

    private final RevaluationItemService revaluationItemService;
    private final AppUserService appUserService;

    @Autowired
    public RevaluationItemController(RevaluationItemService revaluationItemService, AppUserService appUserService) {
        this.revaluationItemService = revaluationItemService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findRevaluationItems(@PathVariable("branchId") Long branchId, @PathVariable("revaluationId") Long revaluationId) {
        Collection<RevaluationItem> RevaluationItems = this.revaluationItemService.getRevaluationItemsByRevaluation(revaluationId);
        log.info("listing RevaluationItems: {}", RevaluationItems);
        return ResponseEntity.ok().body(RevaluationItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRevaluationItem(@PathVariable("branchId") Long branchId, @PathVariable("revaluationId") Long revaluationId, @PathVariable("id") Long id) {
        RevaluationItem RevaluationItem = this.revaluationItemService.findRevaluationItem(id);
        log.info("listing RevaluationItem: {}", RevaluationItem);
        return ResponseEntity.ok().body(RevaluationItem);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerRevaluationItem(@PathVariable("branchId") Long branchId, @PathVariable("revaluationId") Long revaluationId, @RequestBody RevaluationItem revaluationItem, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding revaluation item: {}", revaluationItem);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        RevaluationItem savedRevaluationItem = this.revaluationItemService.saveRevaluationItem(branchId, revaluationId, revaluationItem, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/RevaluationItems/").toUriString()
                + branchId + "/receipts/"
                + revaluationId + "/receipt-items/"
                + revaluationItem.getId());
        return ResponseEntity.created(uri).body(savedRevaluationItem);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRevaluationItem(@PathVariable("branchId") Long branchId, @PathVariable("revaluationId") Long revaluationId, @RequestBody RevaluationItem revaluationItem, @RequestBody RevaluationItem RevaluationItem) {
        log.info("updating RevaluationItem: {}", RevaluationItem);
        RevaluationItem updatedRevaluationItem = this.revaluationItemService.updateRevaluationItem(RevaluationItem);
        return ResponseEntity.ok().body(updatedRevaluationItem);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<?> listProducts(@PathVariable("branchId") Long branchId, @PathVariable("revaluationId") Long revaluationId, @PathVariable("id") Long id) {
        List<Product> products = this.revaluationItemService.listProducts(revaluationId);
        log.info("listing products of revaluation: {}", products);
        return ResponseEntity.ok().body(products);
    }

}