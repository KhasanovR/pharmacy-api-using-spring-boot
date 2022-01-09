package com.example.pharmacy.management.model;

import com.example.pharmacy.account.model.AppUser;
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
public class RevaluationItem {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "revaluation_id")
    private Revaluation revaluation;
    @ManyToOne
    @JoinColumn(name = "receipt_item_id")
    private ReceiptItem receiptItem;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Column(precision = 12, scale = 2)
    private Float price;
    @Column(precision = 3, scale = 2)
    private Float new_percentage;
    @Column(precision = 12, scale = 2)
    private Float newPrice;
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
