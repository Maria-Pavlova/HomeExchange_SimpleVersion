package com.example.homeexchange_simpleversion.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{
    @Column
    private String image;

//    @ManyToOne
//    @JoinColumn(name = "home_id")
//    private Home home;


}