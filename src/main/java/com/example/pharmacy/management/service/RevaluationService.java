package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.core.util.ThrowableChecker;
import com.example.pharmacy.management.exception.ReceiptNotFoundException;
import com.example.pharmacy.management.exception.RevaluationNotFoundException;
import com.example.pharmacy.management.model.Receipt;
import com.example.pharmacy.management.model.ReceiptItem;
import com.example.pharmacy.management.model.Revaluation;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.RevaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class RevaluationService {
    private final BranchRepository branchRepository;
    private final RevaluationRepository revaluationRepository;
    private final ThrowableChecker throwableChecker;

    @Autowired
    public RevaluationService(BranchRepository branchRepository, RevaluationRepository revaluationRepository, ThrowableChecker throwableChecker) {
        this.branchRepository = branchRepository;
        this.revaluationRepository = revaluationRepository;
        this.throwableChecker = throwableChecker;
    }


    public Collection<Revaluation> getRevaluationsByBranch(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new RevaluationNotFoundException("Branch by id " + branchId + " was not found")
        ).getRevaluations();
    }

    public Revaluation findRevaluation(Long id) {
        return revaluationRepository
                .findById(id)
                .orElseThrow(
                        () -> new RevaluationNotFoundException("Revaluation by id " + id + " was not found")
                );
    }

    public Revaluation saveRevaluation(Long branchId, Revaluation revaluation, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Revaluation save = revaluationRepository.save(revaluation);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        branchRepository.findById(branchId).orElseThrow(
                () -> new RevaluationNotFoundException("Branch by id " + branchId + " was not found")
        ).getRevaluations().add(save);
        return save;
    }

    public Revaluation updateRevaluation(Revaluation revaluation) {
        throwableChecker.throwIfAnyActiveInventory();
        return revaluationRepository.save(revaluation);
    }

    public void accept(Long id, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Revaluation revaluation = revaluationRepository
                .findById(id)
                .orElseThrow(
                        () -> new RevaluationNotFoundException("Revaluation by id " + id + " was not found")
                );
        revaluation.getRevaluationItems()
                .forEach(
                        revaluationItem -> {
                            ReceiptItem receiptItem = revaluationItem.getReceiptItem();
                            receiptItem.setPrice(revaluationItem.getPrice());
                            if (revaluationItem.getNew_percentage() == null)
                                revaluationItem.setNew_percentage(1 - revaluationItem.getNewPrice() / revaluationItem.getPrice());
                            else
                                revaluationItem.setNewPrice((1+revaluationItem.getNew_percentage())*receiptItem.getPrice());
                            receiptItem.setPrice(revaluationItem.getNewPrice());
                        }
                );
        revaluation.setAcceptedAt(Instant.now());
        revaluation.setAcceptedBy(user);
    }

    public void cancel(Long id, AppUser user) {
        throwableChecker.throwIfAnyActiveInventory();
        Revaluation revaluation = revaluationRepository
                .findById(id)
                .orElseThrow(
                        () -> new RevaluationNotFoundException("Revaluation by id " + id + " was not found")
                );
        revaluation.setCancelledAt(Instant.now());
        revaluation.setCancelledBy(user);
    }
}
