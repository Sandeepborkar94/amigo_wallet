package com.infosys.app.service.implementatio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.app.dto.UserDTO;
import com.infosys.app.entity.Offer;
import com.infosys.app.repository.OfferRepository;
import com.infosys.app.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final RestTemplate restTemplate;

    public OfferServiceImpl(OfferRepository offerRepository, RestTemplate restTemplate) {
        this.offerRepository = offerRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Offer addOffer(Offer offer, Long adminId) {
        // Call manage-users API to check if user is an admin
        String userApiUrl = "http://localhost:8000/api/users/" + adminId;
        UserDTO user = restTemplate.getForObject(userApiUrl, UserDTO.class);

        if (user == null || !user.getUsername().equalsIgnoreCase("admin")) {
            throw new RuntimeException("User is not an admin!");
        }

        return offerRepository.save(offer);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> getOffersByRegion(String region) {
        return offerRepository.findByRegion(region);
    }
}
