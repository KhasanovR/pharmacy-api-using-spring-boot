package com.example.pharmacy.reference_book.model;

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
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drug {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
    @ManyToOne
    @JoinColumn(name = "international_name_id")
    private InternationalName internationalName;
    @ManyToOne
    @JoinColumn(name = "pharma_group_id")
    private PharmaGroup pharmaGroup;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @Column
    private String dose;
    @Column(columnDefinition = "INT(11) DEFAULT 1")
    private String barcode;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean special;
    private Integer piece;
    private String description;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean system;
    @ManyToMany
    @JoinTable(
            name = "belonging_recommendations",
            joinColumns = @JoinColumn(name = "drug_id"),
            inverseJoinColumns = @JoinColumn(name = "recommendation_id"))
    private Set<Recommendation> recommendations;
    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private AppUser createdBy;
    @LastModifiedDate
    private Instant LastModifiedAt;
    @ManyToOne
    @JoinColumn(name = "last_modified_by_id")
    @LastModifiedBy
    private AppUser LastModifiedBy;
}
