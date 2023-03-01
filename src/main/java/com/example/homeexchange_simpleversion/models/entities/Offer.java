package com.example.homeexchange_simpleversion.models.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @ManyToOne
    private Home home;
    @Column
    private String description;
    @Column(nullable = false)
    private LocalDate availableFrom;
    @Column(nullable = false)
    private LocalDate availableTo;
    @ManyToOne
    private User owner;



}
