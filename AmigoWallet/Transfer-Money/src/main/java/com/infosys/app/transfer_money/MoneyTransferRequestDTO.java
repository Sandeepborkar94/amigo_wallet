package com.infosys.app.transfer_money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferRequestDTO {
    
    private Long senderId;
    private String recipientEmail;
    private String bankCode;
    private String accountNumber;
    private String accountHolderName;
    private Double amount;
    private String transferType;  // "BANK" or "WALLET"
}
