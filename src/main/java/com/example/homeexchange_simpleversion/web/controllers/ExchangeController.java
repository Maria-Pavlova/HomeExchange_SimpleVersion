package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.services.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ExchangeController {

    private final HomeService homeService;

    public ExchangeController(HomeService homeService) {
        this.homeService = homeService;
    }

//    @GetMapping("/pages/all")
//    public String getAllPublishedHomes(Model model){
//
//        model.addAttribute("all", homeService.getAllOfferedHomes());
//        return "all";
// TODO: 1.3.2023 г. for ADMINS + get all homes


}
