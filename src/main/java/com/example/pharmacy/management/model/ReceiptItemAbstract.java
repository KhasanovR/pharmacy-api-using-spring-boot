package com.example.pharmacy.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.AUTO;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptItemAbstract {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(precision = 12, scale = 2)
    private Float basePrice;
    @Column(nullable = false, precision = 12, scale = 2)
    private Float incomingPrice;
    @Column(nullable = false, precision = 3, scale = 2)
    private Float markup;
    @Column(nullable = false, precision = 12, scale = 2)
    private Float price;
    @Column(nullable = false)
    private String serial;
    @Column
    private String location;
    @Column(nullable = false, precision = 12, scale = 2, columnDefinition = "numeric(12,2) default 0.0")
    private Float vat;
    @Column
    private LocalDate expirationDate;
}
