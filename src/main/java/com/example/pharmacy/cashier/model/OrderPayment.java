package com.example.pharmacy.cashier.model;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.management.model.Branch;
import com.example.pharmacy.management.model.ReceiptItem;
import com.example.pharmacy.reference_book.model.Drug;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;
    @Column(nullable = false, precision = 12, scale = 2)
    private Float amount;
    @Column(precision = 12, scale = 2, columnDefinition = "numeric(12,2) default 0.0")
    private Float returnedAmount;
    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private AppUser createdBy;
    @LastModifiedDate
    private Instant LastModifiedAt;
    @ManyToOne
    @JoinColumn(name = "last_modified_by_id", nullable = false)
    @org.springframework.data.annotation.LastModifiedBy
    private AppUser LastModifiedBy;
}
