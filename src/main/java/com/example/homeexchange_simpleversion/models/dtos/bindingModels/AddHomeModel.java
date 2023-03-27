package com.example.homeexchange_simpleversion.models.dtos.bindingModels;


import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.utils.validation.DatesConstraint;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DatesConstraint
public class AddHomeModel implements Serializable {
    @NotEmpty
    @Size(min = 2, max = 20, message = "Title length must be between 2 and 20 characters")
    private String title;
    @NotNull
    private HomeType homeType;
    @NotNull
    private ResidenceType residenceType;
    @NotNull
    @Size(min = 2, max = 20, message = "Town length must be between 2 and 20 characters")
    private String town;
    @NotNull
    @Size(min = 2, max = 20, message = "Country length must be between 2 and 20 characters!")
    private String country;
    @NotNull
    @Positive(message = "Number of Bedrooms must be positive number!")
    private Integer bedrooms;
    @NotNull
    @Positive(message = "Number of Bathrooms must be positive number!")
    private Integer bathrooms;
    @NotNull
    @Positive(message = "Number of People must be positive number!")
    private Integer peopleFor;
    @NotNull
    @Size(min = 5, message = "Description length must be more than 5 characters")
    private String description;
    private List<AmenityName> amenities;
    @Future(message = "The date cannot be in the past!")
    private LocalDate availableFrom;

    @Future(message = "The date cannot be in the past!")
    private LocalDate availableTo;

    private MultipartFile picture;

    private boolean isPublished;

    public AddHomeModel setTitle(String title) {
        this.title = title;
        return this;
    }

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

    public AddHomeModel setAmenities(List<AmenityName> amenities) {
        this.amenities = amenities;
        return this;
    }

    public AddHomeModel setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
        return this;
    }

    public AddHomeModel setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
        return this;
    }

    public AddHomeModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }

    public AddHomeModel setPublished(boolean published) {
        isPublished = published;
        return this;
    }
}
