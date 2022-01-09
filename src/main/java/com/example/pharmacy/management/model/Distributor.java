package com.example.pharmacy.management.model;

import com.example.pharmacy.account.model.AppUser;
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

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Distributor {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String person;
    @Column
    private String phone;
    @Column
    private String fax;
    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private String address;
    @Column
    private String settlement_account;
    @Column
    private String tin;
    @Column
    private String mfo;
    @Column
    private String okonkh;
    @Column
    private String bank_name;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
    @Column
    private String description;
    @Column
    private String location;
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
