package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import com.example.homeexchange_simpleversion.services.HomeService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/homes")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ModelAttribute("homeModel")
    public AddHomeModel homeModel() {
        return new AddHomeModel();
    }

    @GetMapping("/add")
    public String getAddHome(){
        return "add-home2";
    }

    @PostMapping("/add")
    public String addHomeStepOne(@Valid AddHomeModel homeModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("homeModel", homeModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeModel", bindingResult);
            return "redirect:/homes/add";
        }
        homeService.addHome(homeModel, userDetails);
        return "redirect:/pages/all";

    }

//    @PostMapping("/add")
//    public String addHomeStepTwo(@Valid AddHomeModel homeModel,
//                          BindingResult bindingResult,
//                          RedirectAttributes redirectAttributes,
//                          @AuthenticationPrincipal UserDetails userDetails) throws IOException {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("homeModel", homeModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeModel", bindingResult);
//            return "redirect:/add";
//        }
//        //homeService.addHome(homeModel, userDetails);
//        return "redirect:/pages/all";
//    }

//    @GetMapping("/all")
//    public String getAllOffers(Model model) {
//        model.addAttribute("offers", offerService.findAllOffers());
//        return "offers";
//    }
}
