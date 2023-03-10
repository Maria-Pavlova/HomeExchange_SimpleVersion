package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.services.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OfferController {
    private final OfferService offerService;


    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/offers/all")
    public String allOffers(Model model) {
        model.addAttribute("offers", offerService.getAllOffers());
        return "all-offers";
    }

    @GetMapping("/offers/byTown/{town}")
    public String getOffersByTown(Model model, @PathVariable String town) {
        model.addAttribute("offersByTown", offerService.getOffersByTown(town));
        return "offers-byTown";
    }
}
