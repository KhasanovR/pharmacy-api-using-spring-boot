package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.reference_book.exception.InternationalNameNotFoundException;
import com.example.pharmacy.reference_book.model.InternationalName;
import com.example.pharmacy.reference_book.repository.InternationalNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class InternationalNameService {
    private final InternationalNameRepository internationalNameRepository;

    @Autowired
    public InternationalNameService(InternationalNameRepository internationalNameRepository) {
        this.internationalNameRepository = internationalNameRepository;
    }

    public InternationalName saveInternationalName(InternationalName internationalName) {
        return internationalNameRepository.save(internationalName);
    }

    public Collection<InternationalName> getInternationalNames() {
        return internationalNameRepository.findAll();
    }

    public InternationalName findInternationalNameById(Long id) {
        return internationalNameRepository
                .findById(id)
                .orElseThrow(
                        () -> new InternationalNameNotFoundException("InternationalName by id " + id + " was not found")
                );
    }

    public InternationalName updateInternationalName(InternationalName InternationalName) {
        return internationalNameRepository.save(InternationalName);
    }

    public void deleteInternationalNameById(Long id) {
        internationalNameRepository.deleteInternationalNameById(id);
    }
}
