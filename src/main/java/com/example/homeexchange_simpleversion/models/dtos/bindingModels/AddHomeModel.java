package com.example.homeexchange_simpleversion.models.dtos.bindingModels;

import com.example.homeexchange_simpleversion.models.entities.Location;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddHomeModel implements Serializable {

    @NotNull
    private HomeType homeType;
    @NotNull
    private ResidenceType residenceType;
    @NotNull
    private String town;
    @NotNull
    private String country;
    @NotNull
    @Positive
    private Integer bedrooms;
    @NotNull
    @Positive
    private Integer bathrooms;
    @NotNull
    @Positive
    private Integer peopleFor;
    @NotNull
    private String description;


    public AddHomeModel setHomeType(HomeType homeType) {
        this.homeType = homeType;
        return this;
    }

    public AddHomeModel setResidenceType(ResidenceType residenceType) {
        this.residenceType = residenceType;
        return this;
    }

    public AddHomeModel setTown(String town) {
        this.town = town;
        return this;
    }

    public AddHomeModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public AddHomeModel setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public AddHomeModel setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public AddHomeModel setPeopleFor(Integer peopleFor) {
        this.peopleFor = peopleFor;
        return this;
    }

    public AddHomeModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
