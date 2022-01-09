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
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Revaluation {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Column
    private String number;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private AppUser createdBy;
    @Column
    private Instant acceptedAt;
    @ManyToOne
    @JoinColumn(name = "accepted_by_id")
    private AppUser acceptedBy;
    @Column
    private Instant cancelledAt;
    @ManyToOne
    @JoinColumn(name = "cancelled_by_id")
    private AppUser cancelledBy;

    @OneToMany(targetEntity = RevaluationItem.class)
    private Set<RevaluationItem> revaluationItems;

    public Boolean isAccepted(){
        return this.acceptedAt != null;
    }

    public Boolean isCancelled(){
        return this.cancelledAt != null;
    }

    public Boolean isAcceptable() {
        return !this.isAccepted() && !this.isCancelled();
    }

    public Boolean isCancellable() {
        return this.isAcceptable();
    }

    public Boolean isPending(){
        return this.isCancellable();
    }

}
