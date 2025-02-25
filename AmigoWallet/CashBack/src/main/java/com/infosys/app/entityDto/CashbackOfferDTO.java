package com.infosys.app.entityDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashbackOfferDTO {
    // For example, if the offer provides 5% cashback, then cashbackPercentage=5.0
    private Double cashbackPercentage;
}
