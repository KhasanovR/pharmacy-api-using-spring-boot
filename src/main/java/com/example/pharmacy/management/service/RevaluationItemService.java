package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.management.exception.RevaluationItemNotFoundException;
import com.example.pharmacy.management.model.RevaluationItem;
import com.example.pharmacy.management.repository.BranchRepository;
import com.example.pharmacy.management.repository.RevaluationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class RevaluationItemService {
    private final BranchRepository branchRepository;
    private final RevaluationItemRepository revaluationItemRepository;

    @Autowired
    public RevaluationItemService(BranchRepository branchRepository, RevaluationItemRepository revaluationItemRepository) {
        this.branchRepository = branchRepository;
        this.revaluationItemRepository = revaluationItemRepository;
    }


    public Collection<RevaluationItem> getRevaluationItemsByBranch(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                () -> new RevaluationItemNotFoundException("Branch by id " + branchId + " was not found")
        ).getRevaluationItems();
    }

    public RevaluationItem findRevaluationItem(Long id) {
        return revaluationItemRepository
                .findById(id)
                .orElseThrow(
                        () -> new RevaluationItemNotFoundException("Revaluation Item by id " + id + " was not found")
                );
    }

    // active_inventory ?
    public RevaluationItem saveRevaluationItem(Long branchId, RevaluationItem revaluationItem) {
        RevaluationItem save = revaluationItemRepository.save(revaluationItem);
        branchRepository.findById(branchId).orElseThrow(
                () -> new RevaluationItemNotFoundException("Branch by id " + branchId + " was not found")
        ).getRevaluationItems().add(save);
        return save;
    }

    // active_inventory ?
    public RevaluationItem updateRevaluationItem(RevaluationItem revaluationItem) {
        return revaluationItemRepository.save(revaluationItem);
    }

    public void list_products(Long id, AppUser user){

    }

}
