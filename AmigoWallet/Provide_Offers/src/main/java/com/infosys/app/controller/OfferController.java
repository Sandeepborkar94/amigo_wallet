package com.infosys.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.app.entity.Offer;
import com.infosys.app.service.OfferService;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    // Add new offer (Admin only)
    @PostMapping("/add/{adminId}")
    public ResponseEntity<Offer> addOffer(@PathVariable Long adminId, @RequestBody Offer offer) {
        return ResponseEntity.ok(offerService.addOffer(offer, adminId));
    }

    // Get all offers
    @GetMapping("/all")
    public ResponseEntity<List<Offer>> getAllOffers() {
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    // Get offers by region (Future support)
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Offer>> getOffersByRegion(@PathVariable String region) {
        return ResponseEntity.ok(offerService.getOffersByRegion(region));
    }
}
