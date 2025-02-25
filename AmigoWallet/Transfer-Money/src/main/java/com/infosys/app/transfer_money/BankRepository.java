package com.infosys.app.transfer_money;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByUserId(Long userId);
}
