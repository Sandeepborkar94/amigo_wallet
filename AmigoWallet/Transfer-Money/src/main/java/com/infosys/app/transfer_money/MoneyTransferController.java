package com.infosys.app.transfer_money;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class MoneyTransferController {
    
    private final MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping
    public MoneyTransferResponseDTO transferMoney(@RequestBody MoneyTransferRequestDTO request) {
        return moneyTransferService.transferMoney(request);
    }
}
