package com.banksystems.bankingsystemapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Transactions {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "bankAccountId")
    private Long bankAccountId;

    @Column(name = "transactionDate")
    private Instant transactionDate;

    @Column(name = "saldoBefore")
    private BigDecimal saldoBefore;

    @Column(name = "saldoAfter")
    private BigDecimal saldoAfter;

    @Column(name = "clientId")
    private Long clientId;

    @Column(name = "fromBankAccountId")
    private Long fromBankAccountId;

}
