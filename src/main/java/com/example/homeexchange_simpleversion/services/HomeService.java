package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import com.example.homeexchange_simpleversion.models.entities.Amenity;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.Picture;
import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HomeService {
    private final HomeRepository homeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AmenityService amenityService;

    public HomeService(HomeRepository homeRepository, ModelMapper modelMapper, UserService userService, AmenityService amenityService) {
        this.homeRepository = homeRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.amenityService = amenityService;
    }

    public void addHome(AddHomeModel homeModel, UserDetails userDetails) throws IOException {

        Home home = modelMapper.map(homeModel, Home.class);
        home.setOwner(userService.findByUsername(userDetails.getUsername()).get());
        home.setPublished(false);

        home.setAmenities(homeModel.getAmenities()
                .stream()
                .map(amenityService::findAmenityByName)
                .toList());

//        String url = new String(homeModel.getPictures().getBytes());
//        Picture picture = new Picture().setUrl(url);
//        home.setPictures(List.of(picture));    todo

        homeRepository.save(home);
    }
}
