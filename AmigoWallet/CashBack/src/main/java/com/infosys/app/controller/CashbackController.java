package com.infosys.app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.app.entity.CashbackRequestDTO;
import com.infosys.app.entityDto.CashbackResponseDTO;
import com.infosys.app.service.CashbackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cashback")
@RequiredArgsConstructor
public class CashbackController {

    private final CashbackService cashbackService;

    @PostMapping("/process")
    public ResponseEntity<CashbackResponseDTO> processCashback(@RequestBody CashbackRequestDTO request) {
        CashbackResponseDTO response = cashbackService.processCashback(request);
        return ResponseEntity.ok(response);
    }
}
