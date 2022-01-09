package com.example.pharmacy.management.model;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.cashier.model.Booking;
import com.example.pharmacy.cashier.model.Order;
import com.example.pharmacy.core.model.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column
    private String phone;
    @Column
    private String fax;
    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private String address;
    @Column
    private String tin;
    @Column
    private String mfo;
    @Column
    private String okonkh;
    @Column
    private String bank_name;
    @Column(precision = 12, scale = 2)
    private Float markup;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
    @ManyToMany
    @JoinTable(
            name = "branch_users",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id"))
    private List<AppUser> users;
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

    @OneToMany(targetEntity = Product.class)
    private Set<Product> products;
    @OneToMany(targetEntity = ProductEvent.class)
    private Set<ProductEvent> productEvents;
    @OneToMany(targetEntity = Inventory.class)
    private Set<Inventory> inventories;
    @OneToMany(targetEntity = InventoryItem.class)
    private Set<InventoryItem> inventoryItems;
    @OneToMany(targetEntity = InventoryItemHistory.class)
    private Set<InventoryItemHistory> inventoryItemHistories;
    @OneToMany(targetEntity = Receipt.class)
    private Set<Receipt> receipts;
    @OneToMany(targetEntity = ReceiptItem.class)
    private Set<ReceiptItem> receiptItems;
    @OneToMany(targetEntity = Revaluation.class)
    private Set<Revaluation> revaluations;
    @OneToMany(targetEntity = RevaluationItem.class)
    private Set<RevaluationItem> revaluationItems;
    @OneToMany(targetEntity = Booking.class)
    private Set<Booking> bookings;
    @OneToMany(targetEntity = Order.class)
    private Set<Order> orders;

}
