package com.infosys.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.app.dto.BillPaymentRequestDTO;
import com.infosys.app.service.BillPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillPaymentController {
    
    private final BillPaymentService billPaymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> payBill(@RequestBody BillPaymentRequestDTO request) {
        String response = billPaymentService.payBill(request);
        return ResponseEntity.ok(response);
    }
}
