package com.example.pharmacy.cashier.model;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.management.model.Branch;
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
public class Order {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(nullable = false, precision = 12, scale = 2)
    private float amount;
    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private AppUser createdBy;
    @Column
    private Instant cancelledAt;
    @ManyToOne
    @JoinColumn(name = "cancelled_by_id")
    private AppUser cancelledBy;

    public Boolean isCancelled(){
        return this.cancelledAt != null;
    }

    public Boolean isCancellable() {
        return !this.isCancelled();
    }
}
