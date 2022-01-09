package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.core.util.ThrowableChecker;
import com.example.pharmacy.management.enums.ProductEventType;
import com.example.pharmacy.management.exception.ReceiptNotFoundException;
import com.example.pharmacy.management.model.ProductEvent;
import com.example.pharmacy.management.model.Receipt;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.ProductEventRepository;
import com.example.pharmacy.management.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class ReceiptService {
    private final BranchRepository branchRepository;
    private final ReceiptRepository receiptRepository;
    private final ProductEventRepository productEventRepository;
    private final ThrowableChecker throwableChecker;

    @Autowired
    public ReceiptService(BranchRepository branchRepository, ReceiptRepository receiptRepository, ProductEventRepository productEventRepository, ThrowableChecker throwableChecker) {
        this.branchRepository = branchRepository;
        this.receiptRepository = receiptRepository;
        this.productEventRepository = productEventRepository;
        this.throwableChecker = throwableChecker;
    }


    public Collection<Receipt> getReceiptsByBranch(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new ReceiptNotFoundException("Branch by id " + branchId + " was not found")
        ).getReceipts();
    }

    public Receipt findReceipt(Long id) {
        return receiptRepository
                .findById(id)
                .orElseThrow(
                        () -> new ReceiptNotFoundException("Receipt by id " + id + " was not found")
                );
    }

    public Receipt saveReceipt(Long branchId, Receipt receipt, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Receipt save = receiptRepository.save(receipt);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        branchRepository.findById(branchId).orElseThrow(
                () -> new ReceiptNotFoundException("Branch by id " + branchId + " was not found")
        ).getReceipts().add(save);
        return save;
    }

    public Receipt updateReceipt(Receipt receipt, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Receipt update = receiptRepository.save(receipt);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void accept(Long id, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Receipt receipt = receiptRepository
                .findById(id)
                .orElseThrow(
                        () -> new ReceiptNotFoundException("Receipt by id " + id + " was not found")
                );
        receipt.setAcceptedAt(Instant.now());
        receipt.setAcceptedBy(user);
        Collection<ProductEvent> productEvents = new ArrayList<>();
        receipt.getReceiptItems().forEach(receiptItem -> {
            ProductEvent productEvent = new ProductEvent();
            productEvent.setReceiptItem(receiptItem);
            productEvent.setBranch(receiptItem.getBranch());
            productEvent.setType(ProductEventType.RECEIPT_ACCEPT.name());
            productEvent.setQuantity(receiptItem.getQuantity());
            productEvent.setCreatedBy(user);
            productEventRepository.save(productEvent);
        });
    }

    public void cancel(Long id, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Receipt receipt = receiptRepository
                .findById(id)
                .orElseThrow(
                        () -> new ReceiptNotFoundException("Receipt by id " + id + " was not found")
                );
        receipt.setCancelledAt(Instant.now());
        receipt.setCancelledBy(user);
    }

    public void performDestroy(Long id) {
        throwableChecker.throwIfAnyActiveInventory();
        Receipt receipt = receiptRepository
                .findById(id)
                .orElseThrow(
                        () -> new ReceiptNotFoundException("Receipt by id " + id + " was not found")
                );
        receipt.getReceiptItems().clear();
        receiptRepository.deleteReceiptById(id);
    }
}
