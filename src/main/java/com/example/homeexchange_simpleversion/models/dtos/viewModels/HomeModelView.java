package com.example.homeexchange_simpleversion.models.dtos.viewModels;


import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
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
  private UserProfile owner;


}
