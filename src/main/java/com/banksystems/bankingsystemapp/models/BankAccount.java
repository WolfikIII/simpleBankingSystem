package com.banksystems.bankingsystemapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankAccount {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "clientId")
    private Long clientId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "accountNumber")
    private Integer accountNumber;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @OneToMany()
    @JoinColumn(name = "bankAccountId")
    List<Transactions>transactionsHistoryList;
}
