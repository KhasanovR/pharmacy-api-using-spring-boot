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

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "receipt_item_id")
    private ReceiptItem receiptItem;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Column(nullable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;
    @Column(nullable = false, precision = 12, scale = 2)
    private Float price;
    @Column(nullable = false, columnDefinition = "int(11) default 1")
    private Integer quantity;
    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private AppUser createdBy;
}
