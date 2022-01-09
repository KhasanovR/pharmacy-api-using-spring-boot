package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.core.util.ThrowableChecker;
import com.example.pharmacy.management.enums.ProductEventType;
import com.example.pharmacy.management.exception.ReceiptItemNotFoundException;
import com.example.pharmacy.management.model.ProductEvent;
import com.example.pharmacy.management.model.ReceiptItem;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.ReceiptItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class ReceiptItemService {
    private final BranchRepository branchRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final ThrowableChecker throwableChecker;

    @Autowired
    public ReceiptItemService(BranchRepository branchRepository, ReceiptItemRepository receiptItemRepository, ThrowableChecker throwableChecker) {
        this.branchRepository = branchRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.throwableChecker = throwableChecker;
    }


    public Collection<ReceiptItem> getReceiptItemsByBranch(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new ReceiptItemNotFoundException("Branch by id " + branchId + " was not found")
        ).getReceiptItems();
    }

    public ReceiptItem findReceiptItem(Long id) {
        return receiptItemRepository
                .findById(id)
                .orElseThrow(
                        () -> new ReceiptItemNotFoundException("Receipt Item by id " + id + " was not found")
                );
    }

    public ReceiptItem saveReceiptItem(Long branchId, ReceiptItem receiptItem, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        ReceiptItem save = receiptItemRepository.save(receiptItem);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        branchRepository.findById(branchId).orElseThrow(
                () -> new ReceiptItemNotFoundException("Branch by id " + branchId + " was not found")
        ).getReceiptItems().add(save);
        return save;
    }


    @Transactional
    public void depreciate(Long id, Integer quantity, AppUser user){
        throwableChecker.throwIfAnyActiveInventory();
        ReceiptItem receiptItem = receiptItemRepository
                .findById(id)
                .orElseThrow(
                        () -> new ReceiptItemNotFoundException("Receipt Item by id " + id + " was not found")
                );
        ProductEvent productEvent = new ProductEvent();
        productEvent.setReceiptItem(receiptItem);
        productEvent.setBranch(receiptItem.getBranch());
        productEvent.setType(ProductEventType.RECEIPT_ITEM_DEPRECIATE.name());
        productEvent.setQuantity(quantity);
        productEvent.setCreatedAt(Instant.now());
        productEvent.setCreatedBy(user);
    }

}
