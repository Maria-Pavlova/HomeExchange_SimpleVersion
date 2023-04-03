package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.exceptions.ForbiddenException;
import com.example.homeexchange_simpleversion.exceptions.ObjectNotFoundException;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.HomeUpdateModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeDetailsModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.MyHomeModel;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import com.example.homeexchange_simpleversion.utils.FileUploadUtil;
import com.example.homeexchange_simpleversion.utils.PublishHomeEvent;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@Service
public class HomeService {
    private final HomeRepository homeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AmenityService amenityService;
    private final CloudinaryService cloudinaryService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeService.class);

    @Autowired
    public HomeService(HomeRepository homeRepository, ModelMapper modelMapper,
                       UserService userService, AmenityService amenityService,
                       CloudinaryService cloudinaryService,
                       ApplicationEventPublisher applicationEventPublisher) {
        this.homeRepository = homeRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.amenityService = amenityService;
        this.cloudinaryService = cloudinaryService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void addHome(AddHomeModel homeModel, UserDetails userDetails, MultipartFile multipartFile) throws IOException {

        String imageUrl = cloudinaryService.uploadImage(multipartFile);

        Home home = modelMapper.map(homeModel, Home.class);
        home.setOwner(userService.findByUsername(userDetails.getUsername()).get());
        home.setPublished(false);

        home.setAmenities(homeModel.getAmenities()
                .stream()
                .map(amenityService::findAmenityByName)
                .toList());
        home.setPicture(imageUrl);
        homeRepository.save(home);

    }

    public void updateHome(HomeUpdateModel homeUpdateModel, UserDetails userDetails, MultipartFile multipartFile) throws IOException {
        String imageUrl = cloudinaryService.uploadImage(multipartFile);

        Home home = homeRepository.findById(homeUpdateModel.getId()).orElseThrow();
        home
                .setTitle(homeUpdateModel.getTitle())
                .setHomeType(homeUpdateModel.getHomeType())
                .setResidenceType(homeUpdateModel.getResidenceType())
                .setAvailableFrom(homeUpdateModel.getAvailableFrom())
                .setAvailableTo(homeUpdateModel.getAvailableTo())
                .setDescription(homeUpdateModel.getDescription())
                .setPeopleFor(homeUpdateModel.getPeopleFor())
                .setPicture(imageUrl)
                .setAmenities(homeUpdateModel.getAmenities()
                        .stream()
                        .map(amenityService::findAmenityByName)
                        .toList());

        homeRepository.save(home);
    }

    public List<MyHomeModel> getMyHomes(UserDetails userDetails) {
        List<Home> myHomes = homeRepository.findAllByOwner_Username(userDetails.getUsername());
        return myHomes.stream()
                .map(home -> {
                    MyHomeModel model = modelMapper.map(home, MyHomeModel.class);
                    if (model.getPicture() != null) {
                        model.setPicture(home.getPicture());
                    }
                    return model;
                })
                .toList();
    }


    public HomeDetailsModel getDetailsById(Long id) {
        Home home = findHomeById(id);
        HomeDetailsModel detailsModel = modelMapper.map(home, HomeDetailsModel.class);
        detailsModel.setPicture(home.getPicture());
        return detailsModel;
    }

    public void deleteHome(Long id)  {
        if (findHomeById(id).isPublished()) {
            throw new ForbiddenException();
        }
        homeRepository.deleteById(id);
    }

    public Home findHomeById(Long id) {
        return homeRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(id, "Home"));
    }

    public void publishHome(Long id) {
        Home home = findHomeById(id);
        home.setPublished(true);

        PublishHomeEvent publishHomeEvent = new PublishHomeEvent(this).setHome(home);

        homeRepository.save(home);
        LOGGER.info("User publish home with ID " + id);
        applicationEventPublisher.publishEvent(publishHomeEvent);
    }

    public boolean isOwner(UserDetails userDetails, Long id) {
        if (id == null || userDetails == null) {
            return  false;
        }
        Home home = homeRepository.findById(id).orElse(null);
        if (home == null) {
            return false;
        }
        return userDetails.getUsername().equals(home.getOwner().getUsername()) ||
                isUserAdmin(userDetails);
    }

    private boolean isUserAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                anyMatch(a -> a.equals("ROLE_" + Role.ADMIN.name()));
    }
}
