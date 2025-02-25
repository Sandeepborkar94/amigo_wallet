package com.infosys.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequestDTO {
    private Long userId;
    private double amount;
    private String bankAccountNumber;
}
