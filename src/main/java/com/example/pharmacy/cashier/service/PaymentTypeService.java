package com.example.pharmacy.cashier.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.cashier.exception.PaymentTypeNotFoundException;
import com.example.pharmacy.cashier.model.PaymentType;
import com.example.pharmacy.cashier.repository.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;

    @Autowired
    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public PaymentType savePaymentType(PaymentType paymentType, AppUser user) {
        PaymentType save = paymentTypeRepository.save(paymentType);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<PaymentType> getPaymentTypes() {
        return paymentTypeRepository.findAll();
    }

    public PaymentType findPaymentTypeById(Long id) {
        return paymentTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> new PaymentTypeNotFoundException("Payment Type by id " + id + " was not found")
                );
    }

    public PaymentType updatePaymentType(PaymentType paymentType, AppUser user) {
        PaymentType update = paymentTypeRepository.save(paymentType);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deletePaymentTypeById(Long id) {
        paymentTypeRepository.deleteById(id);
    }
}
