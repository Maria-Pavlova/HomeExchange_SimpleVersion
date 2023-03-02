package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.HomeModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.MyHomeModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferedHomeModel;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import com.example.homeexchange_simpleversion.utils.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class HomeService {
    private final HomeRepository homeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AmenityService amenityService;


    public HomeService(HomeRepository homeRepository, ModelMapper modelMapper,
                       UserService userService, AmenityService amenityService) {
        this.homeRepository = homeRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.amenityService = amenityService;
    }

    public void addHome(AddHomeModel homeModel, UserDetails userDetails, MultipartFile multipartFile) throws IOException {

        Home home = modelMapper.map(homeModel, Home.class);
        home.setOwner(userService.findByUsername(userDetails.getUsername()).get());
        home.setPublished(false);

        home.setAmenities(homeModel.getAmenities()
                .stream()
                .map(amenityService::findAmenityByName)
                .toList());
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
      //  Picture picture = new Picture(fileName);
        // TODO: 2.3.2023 г. check for potential loop


        home.setPicture(fileName);

        homeRepository.save(home);

        String uploadDir = "home-photos/" + home.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        //todo set guestPoints

    }

    public List<OfferedHomeModel> getAllOfferedHomes() {
        //todo
        return null;
    }

    public HomeModel findById(Long id) {
       return modelMapper.map(homeRepository.findById(id).get(), HomeModel.class);
    }

    public void updateHome(HomeModel homeModel, UserDetails userDetails) {
        // TODO: 2.3.2023 г. get details->map to homeModel -> then update 
        Home home = homeRepository.findById(homeModel.getId()).orElseThrow();
        home.setHomeType(homeModel.getHomeType())
                .setResidenceType(homeModel.getResidenceType())
                // todo         .setPictures(homeModel.getPictures())
                // todo MAP  .setAmenities(homeModel.getAmenities())
                .setBathrooms(homeModel.getBathrooms())
                .setAvailableFrom(homeModel.getAvailableFrom())
                .setAvailableTo(homeModel.getAvailableTo())
                .setDescription(homeModel.getDescription())
                .setBedrooms(homeModel.getBedrooms())
                .setCountry(homeModel.getCountry())
                .setTown(homeModel.getTown())
                .setPeopleFor(homeModel.getPeopleFor())
                .setPublished(true);
        home.setOwner(userService.findByUsername(userDetails.getUsername()).get());

            homeRepository.save(home);


    }

    public List<MyHomeModel> getMyHomes(UserDetails userDetails) {
        List<Home> myHomes = homeRepository.findAllByOwner_Username(userDetails.getUsername());
        List<MyHomeModel> homeModels = myHomes.stream()
                .map(home -> {
                    MyHomeModel model = modelMapper.map(home, MyHomeModel.class);
                    model.setPicture(home.getPictureImagePath());
                    return model;
                })
                .toList();

        return homeModels;


    }
}
