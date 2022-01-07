package com.example.pharmacy.account.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.model.UserRole;
import com.example.pharmacy.account.service.AppUserServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @GetMapping("/users/{username}")
    public ResponseEntity<AppUser> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveAppUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveAppUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<UserRole> saveUserRole(@RequestBody UserRole role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUserRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addUserRoleToAppUser(@RequestBody RoleToUserForm form) {
        userService.addUserRoleToAppUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}