package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import com.example.homeexchange_simpleversion.models.entities.Amenity;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class HomeDetailsModel implements Serializable {
    private Long id;
    private String title;
    private HomeType homeType;
    private ResidenceType residenceType;
    private String town;
    private String country;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer peopleFor;
    private String description;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private String picture;
    private boolean isPublished;
    private Integer guestPoints;
    private List<Amenity> amenities;
}
