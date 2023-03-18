package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.services.HomeService;
import com.example.homeexchange_simpleversion.services.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    private final HomeService homeService;
    private final OfferService offerService;

    public PagesController(HomeService homeService, OfferService offerService) {
        this.homeService = homeService;
        this.offerService = offerService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @GetMapping("/welcome")
//    public String welcome() {
//        return "welcome";
//    }


    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("newOffers", offerService.getLastOffers());
        return "home-page";

    }

    @GetMapping("/pages/moderator")
    public String moderator() {
        return "moderator";
    }

    @GetMapping("/pages/admin")
    public String admin() {
        return "admin";
    }
}
