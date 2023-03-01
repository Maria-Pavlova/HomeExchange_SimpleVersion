package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import com.example.homeexchange_simpleversion.models.entities.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@NoArgsConstructor
public class OfferedHomeModel implements Serializable {

    private String title;
    private Picture picture;
    private String town;
    private String country;
    private Integer guestPoints;


}
