package com.example.pharmacy.cashier.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.cashier.model.PaymentType;
import com.example.pharmacy.cashier.service.PaymentTypeService;
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
@RequestMapping("/api/payment-types")
@Slf4j
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;
    private final AppUserService appUserService;

    @Autowired
    public PaymentTypeController(PaymentTypeService paymentTypeService, AppUserService appUserService) {
        this.paymentTypeService = paymentTypeService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findPaymentTypes() {
    Collection<PaymentType> paymentTypes = this.paymentTypeService.getPaymentTypes();
        log.info("listing payment types: {}", paymentTypes);
        return ResponseEntity.ok().body(paymentTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPaymentType(@PathVariable("id") Long id) {
        PaymentType paymentType = this.paymentTypeService.findPaymentTypeById(id);
        log.info("listing payment type: {}", paymentType);
        return ResponseEntity.ok().body(paymentType);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerPaymentType(@RequestBody PaymentType paymentType, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding payment type: {}", paymentType);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        PaymentType savedPaymentType = this.paymentTypeService.savePaymentType(paymentType, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/payment-types/").toUriString()
                + savedPaymentType.getId());
        return ResponseEntity.created(uri).body(savedPaymentType);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePaymentType(@RequestBody PaymentType paymentType, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating payment type: {}", paymentType);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        PaymentType updatedPaymentType = this.paymentTypeService.updatePaymentType(paymentType, user);
        return ResponseEntity.ok().body(updatedPaymentType);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removePaymentType(@PathVariable("id") Long id){
        log.info("deleting payment type with id: {}", id);
        this.paymentTypeService.deletePaymentTypeById(id);
        return ResponseEntity.ok().build();
    }
}