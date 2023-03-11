package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.services.HomeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    private final HomeService homeService;

    public PagesController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("all", homeService.getAllOfferedHomes());
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
