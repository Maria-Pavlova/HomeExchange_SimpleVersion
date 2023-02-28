package com.example.homeexchange_simpleversion.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location extends BaseEntity {

    @Column(nullable = false)
    private String town;
    @Column(nullable = false)
    private String country;


    public String getTown() {
        return town;
    }

    public Location setTown(String town) {
        this.town = town;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Location setCountry(String country) {
        this.country = country;
        return this;
    }
}