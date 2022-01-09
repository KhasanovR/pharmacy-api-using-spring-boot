package com.example.pharmacy.core.service;

import com.example.pharmacy.core.exception.CountryNotFoundException;
import com.example.pharmacy.core.model.Country;
import com.example.pharmacy.core.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    public Collection<Country> getCountries() {
        return countryRepository.findAll();
    }

    public Country findCountryById(Long id) {
        return countryRepository
                .findById(id)
                .orElseThrow(
                        () -> new CountryNotFoundException("Country by id " + id + " was not found")
                );
    }

    public Country updateCountry(Country country, AppUser user) {
        Country update = countryRepository.save(country);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteCountryById(Long id) {
        countryRepository.deleteCountryById(id);
    }
}
