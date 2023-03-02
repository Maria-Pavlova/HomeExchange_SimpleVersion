package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.HomeModel;
import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.services.HomeService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public String addHome(@Valid AddHomeModel homeModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("homeModel", homeModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeModel", bindingResult);
            return "redirect:/homes/add";
        }
        homeService.addHome(homeModel, userDetails, multipartFile);
        return "redirect:/pages/all";

    }

    @GetMapping("/{id}/update")
    public String updateHome(@PathVariable Long id, Model model) {
        HomeModel homeModel = homeService.findById(id);
        homeModel.setId(id);

        model.addAttribute("homeModel", homeModel);
        return "home-update"; //todo
    }

    @GetMapping("/{id}/update/errors")
    public String updateHomeErrors(@PathVariable Long id, Model model) {
        model.addAttribute("homeType", HomeType.values());
        model.addAttribute("residenceType", ResidenceType.values());
        model.addAttribute("amenityName", AmenityName.values());
        return "home-update";

    }

    @PatchMapping("/{id}/update")
    public String updateOffer(@PathVariable Long id,
                              @Valid HomeModel homeModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("homeModel", homeModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeModel", bindingResult);
            return "redirect:/homes" + id + "/update/errors";

        }
        homeService.updateHome(homeModel, userDetails);
        return "redirect:/homes" + id + "/details";
        // TODO: 2.3.2023 г. from details -> make public
    }

}
