package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeModelView;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferView;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.Offer;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.repositories.OfferRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
//    private final HomeService homeService;
    private UserDetails userDetails;


    public OfferService(OfferRepository offerRepository, ModelMapper modelMapper,
                        UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
//        this.homeService = homeService;
    }


    public void addOffer(Home home) {
        Offer offer = new Offer();
        offer.setHome(home);
        offerRepository.save(offer);
    }

    @Cacheable("offers")
    public List<OfferView> getAllOffers() {
       return offerRepository.findAll()
               .stream()
               .map(offer -> {
                   OfferView offerView = modelMapper.map(offer, OfferView.class);
                   offerView.getHome().setPicture(offer.getHome().getPictureImagePath());
                   return offerView;
               })
               .toList();
    }

    public List<OfferView> getOffersByTown(String town) {
        return offerRepository.findAllByHome_Town(town)
                .stream()
                .map(offer -> {
                    OfferView offerView = modelMapper.map(offer, OfferView.class);
                    offerView.getHome().setPicture(offer.getHome().getPictureImagePath());
                    return offerView;
                })
                .toList();
    }

//
//
//    public OfferDetailsModel getDetails(Long id) {
//        Offer offer = offerRepository.findById(id)
//                .get();
//
//       return modelMapper.map(offer, OfferDetailsModel.class);
//
//    }
}

