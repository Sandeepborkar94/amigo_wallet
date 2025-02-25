package com.infosys.app.controller;

import org.hibernate.query.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.app.entityDTO.TransactionDTO;
import com.infosys.app.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    
    private final TransactionService transactionService;

    @GetMapping("/{userId}")
    public ResponseEntity<org.springframework.data.domain.Page<TransactionDTO>> getUserTransactions(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ) {
        org.springframework.data.domain.Page<TransactionDTO> transactions = transactionService.getUserTransactions(userId, page, size);
        return ResponseEntity.ok(transactions);
    }
}
