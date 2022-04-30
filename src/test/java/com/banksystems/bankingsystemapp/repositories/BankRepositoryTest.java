package com.banksystems.bankingsystemapp.repositories;

import com.banksystems.bankingsystemapp.models.BankAccount;
import com.banksystems.bankingsystemapp.models.Transactions;
import org.h2.mvstore.tx.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepositoryTest;

    private BankAccount bankAccountTest;

    @BeforeEach
    public void setup(){
        List<Transactions> transactions = new ArrayList<>();
       bankAccountTest = BankAccount.builder()
               .id(1L)
               .clientId(1L)
               .accountNumber(1111111)
               .saldo(BigDecimal.valueOf(5000))
               .userId(1L)
               .transactionsHistoryList(transactions)
               .build();
    }

    @DisplayName("JUnit test for get bank account by account number operation")
    @Test
    void getBankAccountByAccountNumber() {
        bankRepositoryTest.save(bankAccountTest);
        int accountNumber = 1111111;

        BankAccount bankAccount = bankRepositoryTest.getBankAccountByAccountNumber(accountNumber);

        assertThat(bankAccount.getAccountNumber()).isEqualTo(bankAccountTest.getAccountNumber());
    }

    @DisplayName("JUnit test for get bank account by id operation")
    @Test
    void getBankAccountById() {
        bankRepositoryTest.save(bankAccountTest);

        BankAccount bankAccountDB = bankRepositoryTest.getBankAccountById(bankAccountTest.getId());

        assertThat(bankAccountDB).isNotNull();
    }
}