package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.reference_book.exception.ManufacturerNotFoundException;
import com.example.pharmacy.reference_book.model.Manufacturer;
import com.example.pharmacy.reference_book.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer, AppUser user) {
        Manufacturer save = manufacturerRepository.save(manufacturer);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<Manufacturer> getManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer findManufacturerById(Long id) {
        return manufacturerRepository
                .findById(id)
                .orElseThrow(
                        () -> new ManufacturerNotFoundException("Manufacturer by id " + id + " was not found")
                );
    }

    public Manufacturer updateManufacturer(Manufacturer manufacturer, AppUser user) {
        Manufacturer update = manufacturerRepository.save(manufacturer);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteManufacturerById(Long id) {
        manufacturerRepository.deleteManufacturerById(id);
    }

}
