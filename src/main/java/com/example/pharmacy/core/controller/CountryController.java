package com.example.pharmacy.core.controller;

import com.example.pharmacy.core.model.Country;
import com.example.pharmacy.core.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/countries")
@Slf4j
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findCountries() {
        Collection<Country> countries = this.countryService.getCountries();
        log.info("listing: {}", countries);
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCountry(@PathVariable("id") Long id) {
        Country country = this.countryService.findCountryById(id);
        log.info("listing: {}", country);
        return ResponseEntity.ok().body(country);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerCountry(@RequestBody Country country) {
        Country savedCountry = this.countryService.saveCountry(country);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/countries/").toUriString()
                + savedCountry.getId());
        return ResponseEntity.created(uri).body(savedCountry);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCountry(@RequestBody Country country) {
        Country updatedCountry = this.countryService.updateCountry(country);
        return ResponseEntity.ok().body(updatedCountry);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeCountry(@PathVariable("id") Long id){
        this.countryService.deleteCountryById(id);
        return ResponseEntity.ok().build();
    }
}