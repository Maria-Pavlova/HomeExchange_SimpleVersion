package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import com.example.homeexchange_simpleversion.models.enums.HomeType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MyHomeModel implements Serializable {

    private Long id;
    private String title;
    private HomeType homeType;

    private String town;

    private String country;

    private String picture;

    private boolean isPublished;


    @Transient
    public String getPictureImagePath() {
        if (picture == null || this.getId() == null) return null;
        return "/home-photos/" + this.getId() + "/" + picture;
    }

    @Override
    public String toString() {
        return "MyHomeModel{" +
                "homeType=" + homeType +
                ", town='" + town + '\'' +
                ", country='" + country + '\'' +
                ", pictureImage='" + picture + '\'' +
                '}';
    }
}
