package com.infosys.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infosys.app.entity.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
