package com.infosys.app.entityDTO;

import java.time.LocalDateTime;

import com.infosys.app.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private LocalDateTime transactionDateTime;
    private String transactionInfo;
    private Double amount;
    private String status;
    private TransactionType transactionType;
}
