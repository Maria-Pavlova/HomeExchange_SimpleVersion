package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeModel;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.HomeUpdateModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeDetailsModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.MyHomeModel;
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
import java.util.Optional;

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
        home.setPicture(fileName);

        homeRepository.save(home);
        String uploadDir = "home-photos/" + home.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        // todo set guestPoints

    }

    public List<HomeModel> getAllOfferedHomes() {
      return homeRepository.findAllByIsPublishedTrue()
              .stream()
              .map(home -> {
                  HomeModel model = modelMapper.map(home, HomeModel.class);
                  model.setPicture(home.getPictureImagePath());
                  return model;
              })
              .toList();


    }

    public HomeUpdateModel findById(Long id) {
        return modelMapper.map(homeRepository.findById(id).get(), HomeUpdateModel.class);
    }

    public void updateHome(HomeUpdateModel homeUpdateModel, UserDetails userDetails) throws IOException {

        Home home = homeRepository.findById(homeUpdateModel.getId()).orElseThrow();
        home
                .setTitle(homeUpdateModel.getTitle())
                .setHomeType(homeUpdateModel.getHomeType())
                .setResidenceType(homeUpdateModel.getResidenceType())
                .setAvailableFrom(homeUpdateModel.getAvailableFrom())
                .setAvailableTo(homeUpdateModel.getAvailableTo())
                .setDescription(homeUpdateModel.getDescription())
                .setPeopleFor(homeUpdateModel.getPeopleFor())
                .setAmenities(homeUpdateModel.getAmenities()
                        .stream()
                        .map(amenityService::findAmenityByName)
                        .toList());

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

    public HomeDetailsModel getDetailsById(Long id) {
        Optional<Home> home = homeRepository.findById(id);
        HomeDetailsModel detailsModel = modelMapper.map(home.get(), HomeDetailsModel.class);
        detailsModel.setPicture(home.get().getPictureImagePath());
        return detailsModel;
    }

    public void deleteHome(Long id) {
        homeRepository.deleteById(id);
    }

    public Home findHomeById(Long id) {
       return homeRepository.findById(id).orElseThrow();
    }

    public void offerHome(Long id) {
        Home home = findHomeById(id);
        home.setPublished(true);
        homeRepository.save(home);
    }
}
