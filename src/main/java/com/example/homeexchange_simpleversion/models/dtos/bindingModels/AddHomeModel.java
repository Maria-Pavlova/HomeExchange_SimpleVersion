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
    // TODO: 3.3.2023 г. Custom validation From < To
    @Future(message = "The date cannot be in the past!")
    private LocalDate availableTo;

    // TODO: 2.3.2023 г. List<Picture>

    private MultipartFile picture;

    private boolean isPublished;


}
