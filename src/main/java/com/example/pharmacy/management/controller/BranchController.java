package com.example.pharmacy.management.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.management.model.Branch;
import com.example.pharmacy.management.service.BranchService;
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
@RequestMapping("/api/branches")
@Slf4j
public class BranchController {

    private final BranchService branchService;
    private final AppUserService appUserService;

    @Autowired
    public BranchController(BranchService branchService, AppUserService appUserService) {
        this.branchService = branchService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findCountries() {
        Collection<Branch> branches = this.branchService.getBranches();
        log.info("listing branches: {}", branches);
        return ResponseEntity.ok().body(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBranch(@PathVariable("id") Long id) {
        Branch branch = this.branchService.findBranch(id);
        log.info("listing branch: {}", branch);
        return ResponseEntity.ok().body(branch);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerBranch(@RequestBody Branch branch, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding branch: {}", branch);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Branch savedBranch = this.branchService.saveBranch(branch, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/branches/").toUriString()
                + savedBranch.getId());
        return ResponseEntity.created(uri).body(savedBranch);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBranch(@RequestBody Branch branch, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating branch: {}", branch);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Branch updatedBranch = this.branchService.updateBranch(branch, user);
        return ResponseEntity.ok().body(updatedBranch);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeBranch(@PathVariable("id") Long id){
        log.info("deleting branch with id {}", id);
        this.branchService.deleteBranchById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<?> listUsers(@PathVariable("id") Long id) {
        Collection<AppUser> users = branchService.findUsersFromBatchId(id);
        log.info("listing users from branch id: {}", users);
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/{id}/users/add")
    public ResponseEntity<?> addUser(@PathVariable("id") Long id, @RequestBody AppUser user) {
        this.branchService.saveUserToBranch(id, user);
        log.info("adding user to branch: {}", user);
        return ResponseEntity.ok().body(user);
    }
}