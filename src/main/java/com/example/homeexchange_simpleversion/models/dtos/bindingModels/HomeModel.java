package com.example.homeexchange_simpleversion.models.dtos.bindingModels;


import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeModel implements Serializable {

    private Long id;
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
    private List<AmenityName> amenities;
    @Future
    private LocalDate availableFrom;
    @Future
    private LocalDate availableTo;

    // TODO: 2.3.2023 г. List<Picture>
    private MultipartFile pictures;
    @NotNull
    private boolean isPublished;


}
