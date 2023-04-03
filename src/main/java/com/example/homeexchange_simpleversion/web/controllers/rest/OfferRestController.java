package com.example.homeexchange_simpleversion.web.controllers.rest;

import com.example.homeexchange_simpleversion.exceptions.OfferNotFoundException;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferErrorDTO;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferView;
import com.example.homeexchange_simpleversion.services.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OfferRestController {
    private final OfferService offerService;

    public OfferRestController(OfferService offerService) {
        this.offerService = offerService;
    }


    @GetMapping("/offers")
    public ResponseEntity<List<OfferView>> getAllOffers() {

        return ResponseEntity.ok(offerService.getAllOffers());
    }

    @GetMapping("offers/{id}")
    public ResponseEntity<OfferView> getOfferById(@PathVariable Long id) throws OfferNotFoundException {
        Optional<OfferView> offer = offerService.findOfferById(id);
        if (offer.isEmpty()) {
            throw new OfferNotFoundException(id, "Offer");
        }
            return ResponseEntity.ok(offer.get());

    }

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<OfferErrorDTO> onOfferNotFound(OfferNotFoundException onfe) {
        OfferErrorDTO offerErrorDTO = new OfferErrorDTO(onfe.getOfferId(), "Offer not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(offerErrorDTO);
    }


}
