package com.example.homeexchange_simpleversion.models.entities;

import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "amenity")
public class Amenity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private AmenityName name;
}