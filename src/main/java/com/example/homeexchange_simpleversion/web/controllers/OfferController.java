package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferView;
import com.example.homeexchange_simpleversion.services.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OfferController {
    private final OfferService offerService;


    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/offers/all")
    public String allOffers(Model model) {
        List<OfferView> offers = offerService.getAllOffers();
        model.addAttribute("offers", offers);
        return "all-offers";
    }

//    @GetMapping("/offers/all/evict")
//    public String refreshAllOffers() {
//       offerService.refreshOffers();
//        return "all-offers";
//    }

    @GetMapping("/offers/byTown/{town}")
    public String getOffersByTown(Model model, @PathVariable String town) {
        model.addAttribute("offersByTown", offerService.getOffersByTown(town));
        return "offers-byTown";
    }

    @GetMapping("/offers/details/{id}")
    public String getOfferDetails(@PathVariable Long id, Model model) {
        model.addAttribute("details", offerService.getDetailsById(id));
        return "offered-home-details";
// TODO: 3.3.2023 г. display amenities and authorise: on click -> login!!!
    }
}
