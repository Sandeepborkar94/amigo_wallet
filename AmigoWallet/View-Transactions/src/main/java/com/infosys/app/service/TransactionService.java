package com.infosys.app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.app.entityDTO.TransactionDTO;
import com.infosys.app.entityDTO.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final RestTemplate restTemplate;

    public Page<TransactionDTO> getUserTransactions(Long userId, int page, int size) {
        // Verify Customer from Manage Users (8000)
        ResponseEntity<UserDTO> response = restTemplate.getForEntity(
            "http://localhost:8000/api/users/" + userId, UserDTO.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("User not found");
        }

        // Fetch Wallet Transactions from Load Wallet (8002)
        ResponseEntity<List<TransactionDTO>> walletTransactions = restTemplate.exchange(
            "http://localhost:8002/api/wallets/" + userId + "/transactions",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<TransactionDTO>>() {});

        // Fetch Money Transfers from Transfer Money (8003)
        ResponseEntity<List<TransactionDTO>> transfers = restTemplate.exchange(
            "http://localhost:8003/api/transfer/" + userId + "/transactions",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<TransactionDTO>>() {});

        // Fetch Bill Payments from Pay Bills (8004)
        ResponseEntity<List<TransactionDTO>> bills = restTemplate.exchange(
            "http://localhost:8004/api/bills/" + userId + "/transactions",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<TransactionDTO>>() {});

        // Combine all transactions
        List<TransactionDTO> allTransactions = new ArrayList<>();
        if (walletTransactions.getBody() != null) allTransactions.addAll(walletTransactions.getBody());
        if (transfers.getBody() != null) allTransactions.addAll(transfers.getBody());
        if (bills.getBody() != null) allTransactions.addAll(bills.getBody());

        allTransactions.sort(Comparator.comparing(TransactionDTO::getTransactionDateTime).reversed());

        int start = Math.min(page * size, allTransactions.size());
        int end = Math.min(start + size, allTransactions.size());
        List<TransactionDTO> paginatedTransactions = allTransactions.subList(start, end);

        return new PageImpl<>(paginatedTransactions, PageRequest.of(page, size), allTransactions.size());
    }
}
