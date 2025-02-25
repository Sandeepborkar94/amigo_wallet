package com.infosys.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillPaymentRequestDTO {
    private Long userId;
    private Long merchantId;
}
