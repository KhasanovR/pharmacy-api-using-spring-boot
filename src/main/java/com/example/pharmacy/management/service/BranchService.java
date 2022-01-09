package com.example.pharmacy.management.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.management.exception.BranchNotFoundException;
import com.example.pharmacy.management.model.Branch;
import com.example.pharmacy.management.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class BranchService {
    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch saveBranch(Branch branch, AppUser user) {
        Branch save = branchRepository.save(branch);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<Branch> getBranches() {
        return branchRepository.findAll();
    }

    public Branch findBranch(Long id) {
        return branchRepository
                .findById(id)
                .orElseThrow(
                        () -> new BranchNotFoundException("Branch by id " + id + " was not found")
                );
    }

    public Branch updateBranch(Branch branch, AppUser user) {
        Branch update = branchRepository.save(branch);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteBranchById(Long id) {
        branchRepository.deleteBranchById(id);
    }

    public Collection<AppUser> findUsersFromBatchId(Long id){
        Branch branch = branchRepository
                .findById(id)
                .orElseThrow(
                        () -> new BranchNotFoundException("Branch by id " + id + " was not found")
                );
        return branch.getUsers();
    }

    public AppUser saveUserToBranch(Long id, AppUser user){
        Branch branch = branchRepository
                .findById(id)
                .orElseThrow(
                        () -> new BranchNotFoundException("Branch by id " + id + " was not found")
                );
        branch.getUsers().add(user);
        return user;
    }
}
