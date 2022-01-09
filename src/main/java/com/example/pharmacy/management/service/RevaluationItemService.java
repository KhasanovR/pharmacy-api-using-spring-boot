package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.core.util.ThrowableChecker;
import com.example.pharmacy.management.exception.ProductNotFoundException;
import com.example.pharmacy.management.exception.RevaluationItemNotFoundException;
import com.example.pharmacy.management.model.Product;
import com.example.pharmacy.management.model.Revaluation;
import com.example.pharmacy.management.model.RevaluationItem;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.ProductRepository;
import com.example.pharmacy.management.repository.RevaluationItemRepository;
import com.example.pharmacy.management.repository.RevaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class RevaluationItemService {
    private final BranchRepository branchRepository;
    private final RevaluationRepository revaluationRepository;
    private final RevaluationItemRepository revaluationItemRepository;
    private final ProductRepository productRepository;
    private final ThrowableChecker throwableChecker;

    @Autowired
    public RevaluationItemService(BranchRepository branchRepository, RevaluationRepository revaluationRepository, RevaluationItemRepository revaluationItemRepository, ProductRepository productRepository, ThrowableChecker throwableChecker) {
        this.branchRepository = branchRepository;
        this.revaluationRepository = revaluationRepository;
        this.revaluationItemRepository = revaluationItemRepository;
        this.productRepository = productRepository;
        this.throwableChecker = throwableChecker;
    }


    public Collection<RevaluationItem> getRevaluationItemsByRevaluation(Long revaluationId) {
        return revaluationRepository.findById(revaluationId).orElseThrow(
                () -> new RevaluationItemNotFoundException("Revaluation by id " + revaluationId + " was not found")
        ).getRevaluationItems();
    }

    public RevaluationItem findRevaluationItem(Long id) {
        return revaluationItemRepository
                .findById(id)
                .orElseThrow(
                        () -> new RevaluationItemNotFoundException("Revaluation Item by id " + id + " was not found")
                );
    }

    public RevaluationItem saveRevaluationItem(Long branchId, Long revaluationId, RevaluationItem revaluationItem, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        RevaluationItem save = revaluationItemRepository.save(revaluationItem);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        revaluationRepository.findById(revaluationId).orElseThrow(
                () -> new RevaluationItemNotFoundException("Revaluation by id " + revaluationId + " was not found")
        ).getRevaluationItems().add(save);
        branchRepository.findById(branchId).orElseThrow(
                () -> new RevaluationItemNotFoundException("Branch by id " + branchId + " was not found")
        ).getRevaluationItems().add(save);
        return save;
    }

    public RevaluationItem updateRevaluationItem(RevaluationItem revaluationItem) {
        throwableChecker.throwIfAnyActiveInventory();
        return revaluationItemRepository.save(revaluationItem);
    }

    public List<Product> listProducts(Long revaluationId) {
        Revaluation revaluation = revaluationRepository
                .findById(revaluationId)
                .orElseThrow(
                        () -> new RevaluationItemNotFoundException("Revaluation by id " + revaluationId + " was not found")
                );
        List<Product> products = new ArrayList<>();
        revaluation.getRevaluationItems().forEach(revaluationItem -> {
            Long productId = revaluationItem.getReceiptItem().getId();

            products.add(
                    productRepository.findById(productId)
                            .orElseThrow(
                                    () -> new ProductNotFoundException("Revaluation by id " + productId + " was not found")
                            )
            );
        });
        return products;
    }

}
