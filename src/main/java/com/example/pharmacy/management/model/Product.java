package com.example.pharmacy.management.model;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.reference_book.model.Drug;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends ReceiptItemAbstract {
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;
    @Column(columnDefinition = "int(11) default 1")
    private Integer initial_quantity;
    @Column(columnDefinition = "bigint default 1")
    private Long quantity;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private AppUser createdBy;
    @Column(nullable = false)
    @LastModifiedDate
    private Instant LastModifiedAt;
    @ManyToOne
    @JoinColumn(name = "last_modified_by_id", nullable = false)
    @LastModifiedBy
    private AppUser LastModifiedBy;
}
