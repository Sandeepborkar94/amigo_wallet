package com.infosys.app.transfer_money;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class MoneyTransferServiceImpl implements MoneyTransferService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyTransferServiceImpl.class);

    private final WalletRepository walletRepository;
    private final BankRepository bankRepository;
    private final RestTemplate restTemplate;

    public MoneyTransferServiceImpl(WalletRepository walletRepository, BankRepository bankRepository, RestTemplate restTemplate) {
        this.walletRepository = walletRepository;
        this.bankRepository = bankRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public MoneyTransferResponseDTO transferMoney(MoneyTransferRequestDTO request) {
        try {
            // Validate Input
            if (request.getAmount() <= 0) {
                return new MoneyTransferResponseDTO("FAILURE", "Amount must be greater than 0");
            }

            // Fetch sender's wallet
            Optional<Wallet> senderWalletOpt = walletRepository.findByUserId(request.getSenderId());
            if (senderWalletOpt.isEmpty()) {
                return new MoneyTransferResponseDTO("FAILURE", "Sender wallet not found");
            }

            Wallet senderWallet = senderWalletOpt.get();

            // Check if sender has sufficient balance
            if (senderWallet.getBalance() < request.getAmount()) {
                return new MoneyTransferResponseDTO("FAILURE", "Insufficient balance");
            }

            if (request.getTransferType().equalsIgnoreCase("BANK")) {
                // Transfer to Bank Account
                return transferToBank(request, senderWallet);
            } else if (request.getTransferType().equalsIgnoreCase("WALLET")) {
                // Transfer to another Wallet
                return transferToWallet(request, senderWallet);
            }

            return new MoneyTransferResponseDTO("FAILURE", "Invalid transfer type");

        } catch (Exception e) {
            LOGGER.error("Error during money transfer: {}", e.getMessage());
            return new MoneyTransferResponseDTO("FAILURE", "Transaction failed: " + e.getMessage());
        }
    }

    private MoneyTransferResponseDTO transferToBank(MoneyTransferRequestDTO request, Wallet senderWallet) {
        // Fetch Bank Account
        Optional<BankAccount> bankAccountOpt = bankRepository.findByUserId(senderWallet.getUserId());
        if (bankAccountOpt.isEmpty()) {
            return new MoneyTransferResponseDTO("FAILURE", "Bank account not found");
        }

        BankAccount bankAccount = bankAccountOpt.get();

        // Perform Transaction
        senderWallet.setBalance(senderWallet.getBalance() - request.getAmount());
        bankAccount.setBalance(bankAccount.getBalance() + request.getAmount());

        walletRepository.save(senderWallet);
        bankRepository.save(bankAccount);

        return new MoneyTransferResponseDTO("SUCCESS", "Money transferred to bank successfully");
    }

    private MoneyTransferResponseDTO transferToWallet(MoneyTransferRequestDTO request, Wallet senderWallet) {
        // Fetch recipient details from manage-users API
        String userApiUrl = "http://localhost:8000/api/users/email/" + request.getRecipientEmail();
        LOGGER.info("Fetching recipient user details from: {}", userApiUrl);

        try {
            UserDTO recipientUser = restTemplate.getForObject(userApiUrl, UserDTO.class);

            // Fetch recipient wallet
            Optional<Wallet> recipientWalletOpt = walletRepository.findByUserId(recipientUser.getId());
            if (recipientWalletOpt.isEmpty()) {
                return new MoneyTransferResponseDTO("FAILURE", "Recipient wallet not found");
            }

            Wallet recipientWallet = recipientWalletOpt.get();

            // Perform Transaction
            senderWallet.setBalance(senderWallet.getBalance() - request.getAmount());
            recipientWallet.setBalance(recipientWallet.getBalance() + request.getAmount());

            walletRepository.save(senderWallet);
            walletRepository.save(recipientWallet);

            return new MoneyTransferResponseDTO("SUCCESS", "Money transferred to recipient successfully");

        } catch (HttpClientErrorException e) {
            return new MoneyTransferResponseDTO("FAILURE", "Recipient not found");
        }
    }
}
