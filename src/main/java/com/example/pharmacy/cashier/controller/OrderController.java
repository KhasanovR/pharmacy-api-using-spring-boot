package com.example.pharmacy.cashier.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.cashier.model.Order;
import com.example.pharmacy.cashier.service.OrderService;
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
@RequestMapping("/api/branch/{branchId}/orders")
@Slf4j
public class OrderController {

    private final OrderService OrderService;
    private final AppUserService appUserService;

    @Autowired
    public OrderController(OrderService OrderService, AppUserService appUserService) {
        this.OrderService = OrderService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findInventories(@PathVariable("branchId") Long branchId) {
        Collection<Order> Orders = this.OrderService.getOrders(branchId);
        log.info("listing Orders: {}", Orders);
        return ResponseEntity.ok().body(Orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrder(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id) {
        Order Order = this.OrderService.findOrderById(id);
        log.info("listing Order: {}", Order);
        return ResponseEntity.ok().body(Order);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerOrder(@PathVariable("branchId") Long branchId, @RequestBody Order Order, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding country: {}", Order);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Order saveOrder = this.OrderService.saveOrder(branchId, Order, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/branches/").toUriString()
                + branchId + "/Orders/"
                + saveOrder.getId());
        return ResponseEntity.created(uri).body(saveOrder);
    }


    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("cancelling Order with id {}", id);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        this.OrderService.cancel(id, user);
        return ResponseEntity.ok().build();
    }

}