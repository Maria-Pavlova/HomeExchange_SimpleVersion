package com.example.myhomeexchange.web.controllers;

import com.example.homeexchange_simpleversion.services.HomeService;
import com.example.homeexchange_simpleversion.services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private final HomeService homeService;
    private final ModelMapper modelMapper;

    public OfferController(OfferService offerService, HomeService homeService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.homeService = homeService;
        this.modelMapper = modelMapper;
    }


//    @ModelAttribute("offerModel")
//    public AddOfferModel offerModel() {
//        return new AddOfferModel();
//    }
    @GetMapping("offers/post/{id}")
    public String getAddOffer(@PathVariable Long id){
   offerService.addOffer(id);
      return "redirect:/pages/all";
       // offerService.createOfferModel(id);
     //   return "redirect:/offers/post/" + id;
        // TODO: 4.3.2023 г. do not work
    }




//    @PostMapping(value = "/post/{id}", params = "Post Exchange offer")
//    public String addOffer( @PathVariable Long id,
//                           AddOfferModel offerModel,
//                           BindingResult bindingResult,
//                           RedirectAttributes redirectAttributes,
//                           @AuthenticationPrincipal UserDetails userDetails) throws IOException {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("offerModel", offerModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerModel", bindingResult);
//            return "redirect:/homes/" + id + "/details";
//        }
//        offerService.addOffer(offerModel, userDetails);
//        return "redirect:/offers/all";
//    }

//    @GetMapping("/all")
//    public String getAllOffers(Model model) {
//        model.addAttribute("offers", offerService.findAllOffers());
//        return "all-offers";
//    }
//
//
//    @GetMapping("/details/{id}")
//    public String getDetails(@PathVariable Long id, Model model) {
//        model.addAttribute("details", offerService.getDetails(id));
//        return "offer-details";
//    }


}
