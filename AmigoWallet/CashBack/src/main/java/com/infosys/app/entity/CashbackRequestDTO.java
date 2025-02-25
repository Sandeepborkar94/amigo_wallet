package com.infosys.app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashbackRequestDTO {
    private Long userId;
    private Double billAmount;
    private String utilityType; // e.g., "Electricity", "Mobile", etc.
}
