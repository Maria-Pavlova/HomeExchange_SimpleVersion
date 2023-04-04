package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.entities.Amenity;
import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.repositories.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;
    @Autowired
    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }



    public Amenity findAmenityByName(AmenityName amenityName) {
        return amenityRepository.findAmenityByName(amenityName).orElseThrow();
    }
}
