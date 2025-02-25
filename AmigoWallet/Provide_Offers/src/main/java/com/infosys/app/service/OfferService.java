package com.infosys.app.service;

import java.util.List;

import com.infosys.app.entity.Offer;

public interface OfferService 
{
    Offer addOffer(Offer offer, Long adminId);
    List<Offer> getAllOffers();
    List<Offer> getOffersByRegion(String region);
}
