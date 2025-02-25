package com.infosys.app.entityDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashbackResponseDTO {
    private String message;
    private Double cashbackAmount;
    private Double newWalletBalance;
}
