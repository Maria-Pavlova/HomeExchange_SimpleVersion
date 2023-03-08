package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeModelView;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.Offer;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.repositories.OfferRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import org.modelmapper.ModelMapper;
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


    public List<HomeModelView> getAllOffers() {
       return offerRepository.findAll()
               .stream()
                .map(offer -> modelMapper.map(offer, HomeModelView.class))
                .toList();
    }


    public List<HomeModelView> getOffersByHomeType(HomeType homeType){
        return offerRepository.findAllByHome_Type(homeType)
                .stream()
                .map(offer ->  modelMapper.map(offer, HomeModelView.class))
                .toList();
        // TODO: 8.3.2023 г. check is it work correctly
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

