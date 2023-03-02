package com.example.homeexchange_simpleversion.models.entities;


import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "homes")
public class Home extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HomeType homeType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResidenceType residenceType;
    @Column(nullable = false)
    private String town;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private Integer bedrooms;
    @Column(nullable = false)
    private Integer bathrooms;
    @Column(nullable = false)
    private Integer peopleFor;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Amenity> amenities = new ArrayList<>();
    @Column(nullable = false)
    private String description;
    @Column
    private LocalDate availableFrom;
    @Column
    private LocalDate availableTo;

    private String picture;
    @Column
    private boolean isPublished;
    @Column
    private Integer guestPoints;
    @ManyToOne
    private User owner;

    public String getPictures() {
        return picture;
    }

    @Transient
    public String getPictureImagePath() {
        if (picture == null || getId() == null) return null;

        return "/home-photos/" + getId() + "/" + picture;
    }

    public String getTitle() {
        return title;
    }

    public Home setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public HomeType getHomeType() {
        return homeType;
    }

    public Home setHomeType(HomeType homeType) {
        this.homeType = homeType;
        return this;
    }

    public ResidenceType getResidenceType() {
        return residenceType;
    }

    public Home setResidenceType(ResidenceType residenceType) {
        this.residenceType = residenceType;
        return this;
    }

    public String getTown() {
        return town;
    }

    public Home setTown(String town) {
        this.town = town;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Home setCountry(String country) {
        this.country = country;
        return this;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public Home setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public Home setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public Integer getPeopleFor() {
        return peopleFor;
    }

    public Home setPeopleFor(Integer peopleFor) {
        this.peopleFor = peopleFor;
        return this;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public Home setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Home setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public Home setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
        return this;
    }

    public LocalDate getAvailableTo() {
        return availableTo;
    }

    public Home setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
        return this;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public Home setPublished(boolean published) {
        isPublished = published;
        return this;
    }

    public Integer getGuestPoints() {
        return guestPoints;
    }

    public Home setGuestPoints(Integer guestPoints) {
        this.guestPoints = guestPoints;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Home setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public Home setPicture(String picture) {
        this.picture = picture;
        return this;
    }
}
