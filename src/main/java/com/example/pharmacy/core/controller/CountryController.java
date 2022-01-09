package com.example.pharmacy.core.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.core.model.Country;
import com.example.pharmacy.core.service.CountryService;
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
@RequestMapping("/api/countries")
@Slf4j
public class CountryController {

    private final CountryService countryService;
    private final AppUserService appUserService;

    @Autowired
    public CountryController(CountryService countryService, AppUserService appUserService) {
        this.countryService = countryService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findCountries() {
        Collection<Country> countries = this.countryService.getCountries();
        log.info("listing countries: {}", countries);
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCountry(@PathVariable("id") Long id) {
        Country country = this.countryService.findCountryById(id);
        log.info("listing country: {}", country);
        return ResponseEntity.ok().body(country);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerCountry(@RequestBody Country country, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding country: {}", country);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Country savedCountry = this.countryService.saveCountry(country, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/countries/").toUriString()
                + savedCountry.getId());
        return ResponseEntity.created(uri).body(savedCountry);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCountry(@RequestBody Country country, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating country: {}", country);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Country updatedCountry = this.countryService.updateCountry(country, user);
        return ResponseEntity.ok().body(updatedCountry);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeCountry(@PathVariable("id") Long id){
        log.info("deleting country with id: {}", id);
        this.countryService.deleteCountryById(id);
        return ResponseEntity.ok().build();
    }
}