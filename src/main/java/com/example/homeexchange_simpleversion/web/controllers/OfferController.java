package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferView;
import com.example.homeexchange_simpleversion.services.OfferService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.naming.OperationNotSupportedException;
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


    @GetMapping("/offers/byTown/{town}")
    public String getOffersByTown(Model model, @PathVariable String town) {
        model.addAttribute("offersByTown", offerService.getOffersByTown(town));
        return "offers-byTown";
    }

    @GetMapping("/offers/details/{id}")
    public String getOfferDetails( @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, Model model) {
        model.addAttribute("details", offerService.getOfferDetailsById(id));
        model.addAttribute("canDelete", offerService.isOwner(userDetails, id));
        return "offered-home-details";
    }

    @PreAuthorize("@offerService.isOwner(#userDetails, #id)")
    @DeleteMapping("/offers/delete/{id}")
    public String deleteOffer(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id ){
        offerService.deleteOffer(id);
        return "redirect:/offers/all";

    }
}
