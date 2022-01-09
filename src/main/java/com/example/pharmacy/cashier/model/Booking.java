package com.example.pharmacy.cashier.model;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.management.model.Branch;
import com.example.pharmacy.management.model.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(nullable = false)
    private LocalDate arrivalDate;
    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private AppUser createdBy;
    @Column
    private Instant finishedAt;
    @ManyToOne
    @JoinColumn(name = "finished_by_id")
    private AppUser finishedBy;
    @Column
    private Instant cancelledAt;
    @ManyToOne
    @JoinColumn(name = "cancelled_by_id")
    private AppUser cancelledBy;

    @OneToMany(targetEntity = InventoryItem.class)
    private Set<InventoryItem> inventoryItems;

    public Boolean isFinished(){
        return this.finishedAt != null;
    }

    public Boolean isCancelled(){
        return this.cancelledAt != null;
    }

    public Boolean isFinishable() {
        return !this.isFinished() && !this.isCancelled();
    }

    public Boolean isCancellable() {
        return this.isFinishable();
    }
}
