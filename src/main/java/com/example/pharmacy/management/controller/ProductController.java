package com.example.pharmacy.management.controller;

import com.example.pharmacy.management.model.Product;
import com.example.pharmacy.management.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/branch/{branchId}/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findProducts(@PathVariable("branchId") Long branchId) {
        Collection<Product> products = this.productService.getProductsByBranch(branchId);
        log.info("listing products: {}", products);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProduct(@PathVariable("branchId") Long branchId, @PathVariable("id") Long id) {
        Product product = this.productService.findProduct(id);
        log.info("listing product: {}", product);
        return ResponseEntity.ok().body(product);
    }
}