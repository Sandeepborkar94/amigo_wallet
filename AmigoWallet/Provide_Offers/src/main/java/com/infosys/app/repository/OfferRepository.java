package com.infosys.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infosys.app.entity.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByRegion(String region);
}
