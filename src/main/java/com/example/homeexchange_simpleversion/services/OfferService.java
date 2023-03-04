package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddOfferModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeModel;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.Offer;
import com.example.homeexchange_simpleversion.repositories.OfferRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final HomeService homeService;


    public OfferService(OfferRepository offerRepository, ModelMapper modelMapper,
                        UserRepository userRepository, HomeService homeService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.homeService = homeService;
    }

//    public void addOffer222222(AddOfferModel offerModel, UserDetails userDetails) {
//
//        Offer offer = modelMapper.map(offerModel, Offer.class);
//
//        offer.setOwner(userRepository.findByUsername(userDetails.getUsername()).orElseThrow());
//
//        offerRepository.save(offer);
//        System.out.println();
//    }

    public AddOfferModel createOfferModel(Long id) {
        Home home = homeService.findHomeById(id);
        home.setPublished(true);
        HomeModel homeModel = modelMapper.map(home, HomeModel.class);
        AddOfferModel offerModel = new AddOfferModel();
        offerModel.setHome(homeModel);

        return offerModel;
    }

    public void addOffer(Long id) {
        Home home = homeService.findHomeById(id);
        home.setPublished(true);
        Offer offer = new Offer();
        offer.setHome(home);
       // offer.setOwner(userRepository.findByUsername(userDetails.getUsername()).get());
        offerRepository.save(offer);


    }


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

