package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.HomeUpdateModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeDetailsModel;
import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.services.HomeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;

@Controller
@RequestMapping("/homes")
public class HomeController {

    private final HomeService homeService;
    private final ModelMapper modelMapper;

    public HomeController(HomeService homeService, ModelMapper modelMapper) {
        this.homeService = homeService;
        this.modelMapper = modelMapper;
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
                          @RequestParam(value = "image", required = false)  MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("homeModel", homeModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeModel", bindingResult);
            return "redirect:/homes/add";
        }
        homeService.addHome(homeModel, userDetails, multipartFile);
        return "redirect:/home";

    }

    @GetMapping("/{username}")
    public String getMyHomes(@PathVariable String username, Model model,
                             @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("myHomes", homeService.getMyHomes(userDetails));
        return "users-homes";
    }

    @GetMapping("/{id}/details")
    public String homeDetails(@PathVariable Long id, Model model) {
        model.addAttribute("details", homeService.getDetailsById(id));
        return "home-details";

    }

    @GetMapping("/{id}/offered/details")
    public String offeredHomeDetails(@PathVariable Long id, Model model) {
        model.addAttribute("details", homeService.getDetailsById(id));
        model.addAttribute("amenities", AmenityName.values());
        return "offered-home-details";


    }
    @GetMapping("/{id}/update")
    public String updateHome(@PathVariable Long id, Model model) {
        HomeDetailsModel details = homeService.getDetailsById(id);
        HomeUpdateModel homeUpdateModel = modelMapper.map(details, HomeUpdateModel.class);
        homeUpdateModel.setId(id);

        model.addAttribute("homeUpdateModel", homeUpdateModel);
        model.addAttribute("homeType", HomeType.values());
        model.addAttribute("residenceType", ResidenceType.values());
        model.addAttribute("amenityName", AmenityName.values());
        return "update-home";
    }

    @GetMapping("/{id}/update/errors")
    public String updateHomeErrors(@PathVariable Long id, Model model) {
        model.addAttribute("homeType", HomeType.values());
        model.addAttribute("residenceType", ResidenceType.values());
        model.addAttribute("amenityName", AmenityName.values());
        return "update-home";

    }

    @PatchMapping("/{id}/update")
    public String updateHome(@PathVariable Long id,
                              @Valid HomeUpdateModel homeUpdateModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(value = "image", required = false)  MultipartFile multipartFile) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("homeModel", homeUpdateModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeModel", bindingResult);
            return "redirect:/homes/" + id + "/update/errors";

        }
        homeService.updateHome(homeUpdateModel, userDetails, multipartFile);
        return "redirect:/homes/" + id + "/details";

    }

    @DeleteMapping("/delete/{id}")
    public String deleteHome(@PathVariable Long id) throws OperationNotSupportedException {
        homeService.deleteHome(id);
        return "redirect:/home";

    }

    @GetMapping("/post/{id}")
    public String publishHome(@PathVariable Long id) {
        homeService.publishHome(id);
        return "redirect:/home";

    }

}
