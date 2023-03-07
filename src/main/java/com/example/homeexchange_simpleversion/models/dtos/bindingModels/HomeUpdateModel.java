package com.example.homeexchange_simpleversion.models.dtos.bindingModels;


import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
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
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeUpdateModel implements Serializable {

    private Long id;
    @NotEmpty
    @Size(min = 2, max = 20, message = "Title length must be between 2 and 20 characters")
    private String title;
    @NotNull
    private HomeType homeType;
    @NotNull
    private ResidenceType residenceType;
    @NotNull
    @Positive
    private Integer peopleFor;
    @NotNull
    @Size(min = 5, message = "Description length must be more than 5 characters")
    private String description;
    @NotNull
    private List<AmenityName> amenities;
    @NotNull
    @Future
    private LocalDate availableFrom;
    @NotNull
    @Future
    private LocalDate availableTo;

    // TODO: 2.3.2023 г. List<Picture>



}
