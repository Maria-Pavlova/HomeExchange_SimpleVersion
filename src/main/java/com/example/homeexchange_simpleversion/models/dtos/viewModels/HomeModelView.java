package com.example.homeexchange_simpleversion.models.dtos.viewModels;


import com.example.homeexchange_simpleversion.models.entities.Amenity;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeModelView implements Serializable {

    private long id;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private Integer bathrooms;
    private Integer bedrooms;
    private String country;
    private String description;
    private Integer guestPoints;
    private HomeType homeType;
    private boolean isPublished;
    private Integer peopleFor;
    private String picture;
    private ResidenceType residenceType;
    private String town;
    private String title;

    private List<Amenity> amenities;
    private UserProfile owner;

    public HomeModelView setId(long id) {
        this.id = id;
        return this;
    }

    public HomeModelView setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
        return this;
    }

    public HomeModelView setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
        return this;
    }

    public HomeModelView setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public HomeModelView setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public HomeModelView setCountry(String country) {
        this.country = country;
        return this;
    }

    public HomeModelView setDescription(String description) {
        this.description = description;
        return this;
    }

    public HomeModelView setGuestPoints(Integer guestPoints) {
        this.guestPoints = guestPoints;
        return this;
    }

    public HomeModelView setHomeType(HomeType homeType) {
        this.homeType = homeType;
        return this;
    }

    public HomeModelView setPublished(boolean published) {
        isPublished = published;
        return this;
    }

    public HomeModelView setPeopleFor(Integer peopleFor) {
        this.peopleFor = peopleFor;
        return this;
    }

    public HomeModelView setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public HomeModelView setResidenceType(ResidenceType residenceType) {
        this.residenceType = residenceType;
        return this;
    }

    public HomeModelView setTown(String town) {
        this.town = town;
        return this;
    }

    public HomeModelView setTitle(String title) {
        this.title = title;
        return this;
    }

    public HomeModelView setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public HomeModelView setOwner(UserProfile owner) {
        this.owner = owner;
        return this;
    }
}
