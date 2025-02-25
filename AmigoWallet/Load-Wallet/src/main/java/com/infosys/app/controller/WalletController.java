package com.infosys.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.app.dto.WalletRequestDTO;
import com.infosys.app.service.WalletService;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/topup")
    public ResponseEntity<String> topUpWallet(@RequestBody WalletRequestDTO request) {
        String response = walletService.loadWallet(request);
        return ResponseEntity.ok(response);
    }
}
