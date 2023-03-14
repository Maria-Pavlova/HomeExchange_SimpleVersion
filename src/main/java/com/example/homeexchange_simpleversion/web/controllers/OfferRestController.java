package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferView;
import com.example.homeexchange_simpleversion.services.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/offers")
public class OfferRestController {
    private final OfferService offerService;

    public OfferRestController(OfferService offerService) {
        this.offerService = offerService;
    }


    @GetMapping()
    public ResponseEntity<List<OfferView>> getAllBooks() {
        return ResponseEntity.ok(offerService.getAllOffers());

    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferView> getOfferById(@PathVariable Long id) {
        Optional<OfferView> offer = offerService.findOfferById(id);
        return offer.map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{town}")
    public ResponseEntity<List<OfferView>> getOfferByTown(@PathVariable String town) {
        return ResponseEntity.ok(offerService.getOffersByTown(town));

    }
}
