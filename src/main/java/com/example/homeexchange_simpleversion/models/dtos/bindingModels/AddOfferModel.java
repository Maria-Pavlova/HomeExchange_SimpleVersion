package com.example.homeexchange_simpleversion.models.dtos.bindingModels;

import com.example.homeexchange_simpleversion.models.entities.Home;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AddOfferModel implements Serializable {

    private Home home;
    private String title;

}
