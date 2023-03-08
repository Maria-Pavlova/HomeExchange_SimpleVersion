package com.example.homeexchange_simpleversion.repositories;

import com.example.homeexchange_simpleversion.models.entities.Offer;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByHome_Type(HomeType homeType);
    //   List<Offer> findAllByHome_Type(HomeType homeType);
}
