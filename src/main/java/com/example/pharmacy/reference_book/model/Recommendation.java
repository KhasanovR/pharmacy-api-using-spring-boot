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
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "recommendations")
    Set<Drug> drugs;
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