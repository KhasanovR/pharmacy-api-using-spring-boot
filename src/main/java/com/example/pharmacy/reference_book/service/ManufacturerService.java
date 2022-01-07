package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.reference_book.exception.ManufacturerNotFoundException;
import com.example.pharmacy.reference_book.model.Manufacturer;
import com.example.pharmacy.reference_book.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
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

    public Manufacturer findManufacturerByName(String name) {
        return manufacturerRepository
                .findByName(name)
                .orElseThrow(
                        () -> new ManufacturerNotFoundException("Manufacturer by name " + name + " was not found")
                );
    }

    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturerById(Long id) {
        manufacturerRepository.deleteManufacturerById(id);
    }

    public void deleteManufacturerByName(String name) {
        manufacturerRepository.deleteManufacturerByName(name);
    }
}
