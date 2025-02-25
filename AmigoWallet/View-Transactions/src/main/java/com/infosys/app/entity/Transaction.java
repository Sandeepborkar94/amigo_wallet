package com.infosys.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String transactionInfo;
    private Double amount;
    private String status;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    // Override to remove the precision (6)
    @Column(name = "transaction_date_time", columnDefinition = "DATETIME")
    private LocalDateTime transactionDateTime;
}
