package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.management.exception.ProductNotFoundException;
import com.example.pharmacy.management.model.Product;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ProductService {
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(BranchRepository branchRepository, ProductRepository productRepository) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
    }


    public Collection<Product> getProductsByBranch(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new ProductNotFoundException("Branch by id " + branchId + " was not found")
        ).getProducts();
    }

    public Product findProduct(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product by id " + id + " was not found")
                );
    }
}
