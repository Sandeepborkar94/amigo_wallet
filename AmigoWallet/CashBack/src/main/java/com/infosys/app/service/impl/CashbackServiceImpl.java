package com.infosys.app.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.app.entity.CashbackRequestDTO;
import com.infosys.app.entityDto.CashbackOfferDTO;
import com.infosys.app.entityDto.CashbackResponseDTO;
import com.infosys.app.entityDto.UserDTO;
import com.infosys.app.service.CashbackService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CashbackServiceImpl implements CashbackService {

    private final RestTemplate restTemplate;
    
    public CashbackServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}



	@Override
    public CashbackResponseDTO processCashback(CashbackRequestDTO request) {
        Long userId = request.getUserId();
        Double billAmount = request.getBillAmount();
        String utilityType = request.getUtilityType();

        String userUrl = "http://localhost:8000/api/users/" + userId;
        ResponseEntity<UserDTO> userResponse = restTemplate.getForEntity(userUrl, UserDTO.class);
        if (!userResponse.getStatusCode().is2xxSuccessful() || userResponse.getBody() == null) {
            return new CashbackResponseDTO("User not found", 0.0, 0.0);
        }
        log.info("User verified: {}", userResponse.getBody());

        String offerUrl = "http://localhost:8001/api/offers/cashback?utilityType=" + utilityType;
        ResponseEntity<CashbackOfferDTO> offerResponse = restTemplate.getForEntity(offerUrl, CashbackOfferDTO.class);
        Double cashbackPercentage = 0.0;
        if (offerResponse.getStatusCode().is2xxSuccessful() && offerResponse.getBody() != null) {
            cashbackPercentage = offerResponse.getBody().getCashbackPercentage();
            log.info("Cashback offer received: {}%", cashbackPercentage);
        } else {
            log.warn("No cashback offer available for utility: {}", utilityType);
        }

        // Step 3: Calculate cashback amount
        Double cashbackAmount = billAmount * (cashbackPercentage / 100.0);
        log.info("Calculated cashback amount: {}", cashbackAmount);

       
        String walletUrl = "http://localhost:8002/api/wallet/cashback";
        java.util.Map<String, Object> walletPayload = new java.util.HashMap<>();
        walletPayload.put("userId", userId);
        walletPayload.put("amount", cashbackAmount);

        ResponseEntity<java.util.Map> walletResponse = restTemplate.postForEntity(walletUrl, walletPayload, java.util.Map.class);
        Double newWalletBalance = 0.0;
        if (walletResponse.getStatusCode().is2xxSuccessful() && walletResponse.getBody() != null) {
            Object balanceObj = walletResponse.getBody().get("newWalletBalance");
            if (balanceObj instanceof Number) {
                newWalletBalance = ((Number) balanceObj).doubleValue();
            }
        } else {
            return new CashbackResponseDTO("Failed to credit cashback to wallet", cashbackAmount, 0.0);
        }

       
        return new CashbackResponseDTO("Cashback credited successfully", cashbackAmount, newWalletBalance);
    }
}
