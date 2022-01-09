package com.example.pharmacy;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.model.UserRole;
import com.example.pharmacy.account.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class PharmacyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacyApiApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(AppUserService userService) {
        return args -> {
            userService.saveUserRole(new UserRole(null, "ROLE_PHARMACIST"));
            userService.saveUserRole(new UserRole(null, "ROLE_ADMIN"));

            userService.saveAppUser(new AppUser(null, "John Travolt", "john", "john@gmail.com", "1234", new ArrayList<>()));
            userService.saveAppUser(new AppUser(null, "Will Smith", "will", "will@gmail.com", "1234", new ArrayList<>()));
            userService.saveAppUser(new AppUser(null, "Jim Carry", "jim", "jim@gmail.com", "1234", new ArrayList<>()));
            userService.saveAppUser(new AppUser(null, "Arnold Toms", "arnold", "arnold@gmail.com", "1234", new ArrayList<>()));

            userService.addUserRoleToAppUser("john", "ROLE_PHARMACIST");
            userService.addUserRoleToAppUser("john", "ROLE_PHARMACIST");
            userService.addUserRoleToAppUser("will", "ROLE_PHARMACIST");
            userService.addUserRoleToAppUser("arnold", "ROLE_PHARMACIST");
            userService.addUserRoleToAppUser("arnold", "ROLE_PHARMACIST");
            userService.addUserRoleToAppUser("arnold", "ROLE_ADMIN");
            userService.addUserRoleToAppUser("jim", "ROLE_ADMIN");
        };
    }
}
