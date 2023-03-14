package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.ObjectNotFoundException;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeDetailsModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferView;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.Offer;
import com.example.homeexchange_simpleversion.repositories.OfferRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.utils.PublishHomeEvent;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private UserDetails userDetails;
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);


    public OfferService(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

    @EventListener(PublishHomeEvent.class)
    public void onPublishHomeCreateOffer(PublishHomeEvent event) {
        Offer offer = new Offer();
        offer.setHome(event.getHome());
        LOGGER.info("Created offer");
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


//    @CacheEvict(cacheNames = "offers", allEntries = true)
//    public void refreshOffers(){
//
//        // TODO: 10.3.2023 г. this have to be in rest controller or in delete, post
//    }

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

    public HomeDetailsModel getDetailsById(Long id) {
        Offer offer = getOfferById(id);
        Home home = offer.getHome();
        HomeDetailsModel detailsModel = modelMapper.map(home, HomeDetailsModel.class);
        detailsModel.setPicture(home.getPictureImagePath());
        return detailsModel;
    }

    public List<OfferView> getLastOffers() {
      return  offerRepository.findAllByOrderByOfferCreatedDesc()
                .stream()
                .limit(5)
                .map(offer -> {
                    OfferView offerView = modelMapper.map(offer, OfferView.class);
                    offerView.getHome().setPicture(offer.getHome().getPictureImagePath());
                    return offerView;
                })
                .toList();
    }

    public Offer getOfferById(Long id){
       return offerRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(id, "Offer"));
    }

    public Optional<OfferView> findOfferById(Long id) {
       return offerRepository.findById(id)
                  .map(offer -> {
            OfferView offerView = modelMapper.map(offer, OfferView.class);
            offerView.getHome().setPicture(offer.getHome().getPictureImagePath());
            return offerView;
        });
    }

//
//
//    public OfferDetailsModel getDetails(Long id) {
//        Offer offer = offerRepository.findById(id)
//                .get();
//       return modelMapper.map(offer, OfferDetailsModel.class);
//
//    }
}

