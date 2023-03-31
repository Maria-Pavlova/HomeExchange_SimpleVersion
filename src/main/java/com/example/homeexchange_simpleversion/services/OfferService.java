package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.exceptions.ObjectNotFoundException;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferView;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.Offer;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.OfferRepository;
import com.example.homeexchange_simpleversion.utils.PublishHomeEvent;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private UserDetails userDetails;
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);

    @Autowired
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
        List<Offer> offers = offerRepository.findAll();
        return mapToOfferView(offers);
    }

//    @CacheEvict(cacheNames = "offers", allEntries = true)
//    public void refreshOffers(){
//
//        // TODO: 10.3.2023 г. this have to be in rest controller or in delete, post
//    }

    @Cacheable("offersByTown")
    public List<OfferView> getOffersByTown(String town) {
        return mapToOfferView(offerRepository.findAllByHome_Town(town));

    }

    public OfferView getOfferDetailsById(Long id) {
        return findOfferById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Offer"));
    }

    public List<OfferView> getLastOffers() {
        return offerRepository.findAllByOrderByOfferCreatedDesc()
                .stream()
                .limit(5)
                .map(offer -> {
                    OfferView offerView = modelMapper.map(offer, OfferView.class);
                    offerView.getHome().setPicture(offer.getHome().getPictureImagePath());
                    return offerView;
                })
                .toList();
    }

    public Offer getOfferById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Offer"));
    }

    public Optional<OfferView> findOfferById(Long id) {
        return
                offerRepository.findById(id)
                        .map(offer -> {
                            OfferView offerView = modelMapper.map(offer, OfferView.class);
                            offerView.getHome().setPicture(offer.getHome().getPictureImagePath());
                            return offerView;
                        });
    }

    public void deleteExpiredOffers() {
        List<Offer> expiredOffers = offerRepository.findAllByHome_AvailableTo(LocalDate.now());
        offerRepository.deleteAll(expiredOffers);
    }

    public boolean isOwner(UserDetails userDetails, Long id) {
        if (id == null || userDetails == null) {
            return false;
        }
        Home home = getOfferById(id).getHome();
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

    public void deleteOffer(Long id) {
        Offer offer = getOfferById(id);
        offerRepository.delete(offer);
    }

    private List<OfferView> mapToOfferView(List<Offer> offers) {
        return offers.stream()
                .map(offer -> {
                    OfferView offerView = modelMapper.map(offer, OfferView.class);
                    offerView.getHome().setPicture(offer.getHome().getPictureImagePath());
                    return offerView;
                })
                .toList();
    }
}

