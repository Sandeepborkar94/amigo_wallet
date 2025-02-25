package com.infosys.app.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.infosys.app.dto.BillPaymentRequestDTO;
import com.infosys.app.entity.Merchant;
import com.infosys.app.entity.Wallet;
import com.infosys.app.repository.MerchantRepository;
import com.infosys.app.repository.WalletRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillPaymentService {
    
    private final WalletRepository walletRepository;
    private final MerchantRepository merchantRepository;
    public String payBill(BillPaymentRequestDTO request) {
        // Fetch customer wallet
        Optional<Wallet> customerWalletOpt = walletRepository.findByUserId(request.getUserId());
        if (customerWalletOpt.isEmpty()) {
            return "Customer wallet not found";
        }
        Wallet customerWallet = customerWalletOpt.get();

        // Fetch merchant details
        Optional<Merchant> merchantOpt = merchantRepository.findById(request.getMerchantId());
        if (merchantOpt.isEmpty()) {
            return "Merchant not found";
        }
        Merchant merchant = merchantOpt.get();

        // Fetch merchant wallet
        Optional<Wallet> merchantWalletOpt = walletRepository.findByUserId(merchant.getWalletId());
        if (merchantWalletOpt.isEmpty()) {
            return "Merchant wallet not found";
        }
        Wallet merchantWallet = merchantWalletOpt.get();

        // Generate random bill amount (50-200 USD)
        double billAmount = 50 + new Random().nextInt(151);

        // Check if customer has enough balance
        if (customerWallet.getBalance() < billAmount) {
            return "Insufficient balance";
        }

        // Deduct from customer wallet
        customerWallet.setBalance(customerWallet.getBalance() - billAmount);
        walletRepository.save(customerWallet);

        // Credit to merchant wallet
        merchantWallet.setBalance(merchantWallet.getBalance() + billAmount);
        walletRepository.save(merchantWallet);

        // Calculate reward points (1 point for every $10 spent)
        int rewardPoints = (int) (billAmount / 10);

        return "Bill Paid Successfully! Amount: $" + billAmount + " | Reward Points Earned: " + rewardPoints;
    }
}
