package com.infosys.app.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.infosys.app.dto.UserDTO;
import com.infosys.app.dto.WalletRequestDTO;
import com.infosys.app.entity.Wallet;
import com.infosys.app.repository.BankRepository;
import com.infosys.app.repository.WalletRepository;
import com.infosys.app.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WalletServiceImpl implements WalletService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);

	private final WalletRepository walletRepository;
	private final RestTemplate restTemplate;

	@Value("${manage.users.service.url}")
	private String manageUsersServiceUrl;

	public WalletServiceImpl(WalletRepository walletRepository, BankRepository bankRepository,
			RestTemplate restTemplate) {
		this.walletRepository = walletRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	public String loadWallet(WalletRequestDTO request) {
		String userApiUrl = manageUsersServiceUrl + "/" + request.getUserId();
		LOGGER.info("Fetching user details from: {}", userApiUrl);

		try {
			UserDTO user = restTemplate.getForObject(userApiUrl, UserDTO.class);
			LOGGER.info("User fetched: {}", user);

			// ✅ If user is null, return an error message
			if (user == null) {
				LOGGER.error("User not found: {}", request.getUserId());
				return "User not found. Wallet top-up failed.";
			}
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				LOGGER.error("User not found: {}", request.getUserId());
				return "User not found. Wallet top-up failed.";
			}
			LOGGER.error("User validation failed: {}", e.getMessage());
			return "User validation failed: " + e.getMessage();
		}

		// ✅ Wallet Top-up Logic
		Wallet wallet = walletRepository.findByUserId(request.getUserId());
		if (wallet == null) {
			wallet = new Wallet();
			wallet.setUserId(request.getUserId());
			wallet.setBalance(0.0);
		}

		if (request.getAmount() < 100) {
			return "Minimum top-up amount is 100 USD.";
		}

		wallet.setBalance(wallet.getBalance() + request.getAmount());
		walletRepository.save(wallet);

		return "Wallet top-up successful. New balance: " + wallet.getBalance();
	}
}
