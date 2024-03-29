package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.services.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    private final OfferService offerService;

    public PagesController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("newOffers", offerService.getLastOffers());
        return "home-page";

    }


    @GetMapping("/pages/admin")
    public String admin() {
        return "admin";
    }
}
