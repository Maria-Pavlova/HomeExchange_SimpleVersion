package com.example.myhomeexchange.web.controllers;

import com.example.homeexchange_simpleversion.services.OfferService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }


//    @ModelAttribute("offerDto")
//    public AddOfferDto offerDto() {
//        return new AddOfferDto();
//    }
//    @GetMapping("/add")
//    public String getAddOffer(){
//        return "offers-add";
//    }
//
//    @PostMapping("/add")
//    public String addOffer(@Valid AddOfferDto offerDto,
//                           BindingResult bindingResult,
//                           RedirectAttributes redirectAttributes,
//                           @AuthenticationPrincipal UserDetails userDetails) throws IOException {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("offerDto", offerDto);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerDto", bindingResult);
//            return "redirect:/offers/add";
//        }
//        offerService.addOffer(offerDto, userDetails);
//        return "redirect:/offers/all";
//    }

    @GetMapping("/all")
    public String getAllOffers(Model model) {
        model.addAttribute("offers", offerService.findAllOffers());
        return "all-offers";
    }


    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable Long id, Model model) {
        model.addAttribute("details", offerService.getDetails(id));
        return "offer-details";
    }


}
