package com.infosys.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.app.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>
{
    Wallet findByUserId(Long userId);
}
