package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.entities.Amenity;
import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.repositories.AmenityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }




    public Amenity findAmenityByName(AmenityName amenityName) {
        return amenityRepository.findAmenityByName(amenityName).orElseThrow();
    }
}
