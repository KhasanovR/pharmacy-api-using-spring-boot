package com.example.pharmacy.reference_book.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.reference_book.exception.InternationalNameNotFoundException;
import com.example.pharmacy.reference_book.model.InternationalName;
import com.example.pharmacy.reference_book.repository.InternationalNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class InternationalNameService {
    private final InternationalNameRepository internationalNameRepository;

    @Autowired
    public InternationalNameService(InternationalNameRepository internationalNameRepository) {
        this.internationalNameRepository = internationalNameRepository;
    }

    public InternationalName saveInternationalName(InternationalName internationalName, AppUser user) {
        InternationalName save = internationalNameRepository.save(internationalName);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
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

    public InternationalName updateInternationalName(InternationalName InternationalName, AppUser user) {
        InternationalName update = internationalNameRepository.save(InternationalName);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteInternationalNameById(Long id) {
        internationalNameRepository.deleteInternationalNameById(id);
    }
}
