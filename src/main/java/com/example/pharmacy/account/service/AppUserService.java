package com.example.pharmacy.account.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.model.UserRole;

import java.util.List;

public interface AppUserService {
    AppUser saveAppUser(AppUser user);
    UserRole saveUserRole(UserRole role);
    void addUserRoleToAppUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
