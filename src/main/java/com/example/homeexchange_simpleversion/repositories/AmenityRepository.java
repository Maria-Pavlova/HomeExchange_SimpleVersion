package com.example.homeexchange_simpleversion.repositories;

import com.example.homeexchange_simpleversion.models.entities.Amenity;
import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    Optional<Amenity> findAmenityByName (AmenityName amenityName);
}
