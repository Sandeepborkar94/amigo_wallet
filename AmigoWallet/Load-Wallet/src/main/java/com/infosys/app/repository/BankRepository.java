package com.infosys.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.app.entity.BankAccount;

public interface BankRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByUserId(Long userId);
}
