package com.infosys.app.transfer_money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferResponseDTO {
    
    private String status;
    private String message;
}
