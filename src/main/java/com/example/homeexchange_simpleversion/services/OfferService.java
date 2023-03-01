package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.entities.Offer;
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


    public OfferService(OfferRepository offerRepository, ModelMapper modelMapper,
                        UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;

        this.userRepository = userRepository;
    }

//    public void addOffer(AddOfferDto offerDto, UserDetails userDetails) {
//
//        Offer offer = modelMapper.map(offerDto, Offer.class);
//        offer.setUser(userRepository.findByUsername(userDetails.getUsername()).orElseThrow());
//
//    // todo ADD OFFER   offer.setUser()
//
//        offerRepository.save(offer);
//        System.out.println(offer);
//    }
//
//
//    public List<OfferModel> findAllOffers() {
//       return offerRepository.findAll()
//               .stream()
//                .map(offer ->{
//                    OfferModel offerModel = modelMapper.map(offer, OfferModel.class);
//                    offerModel.setPictureUrl(offer.getHome().getPictures().stream().findFirst().get().getUrl());
//                    return offerModel;
//                } )
//                .toList();
//
//    }
//
//
//    public List<OfferModel> getOffersByHomeType(HomeType homeType){
//        return offerRepository.findAllByHome_Type(homeType)
//                .stream().map(offer ->  modelMapper.map(offer, OfferModel.class)).toList();
//    }
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

