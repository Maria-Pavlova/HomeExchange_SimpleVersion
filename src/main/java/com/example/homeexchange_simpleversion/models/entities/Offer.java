package com.example.homeexchange_simpleversion.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    @ManyToOne
    private Home home;
    @Column
    private LocalDateTime offerCreated;



    @PrePersist
    public void prePersist(){
        setOfferCreated(LocalDateTime.now());
     }




}
