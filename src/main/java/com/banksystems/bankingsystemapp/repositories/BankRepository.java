package com.banksystems.bankingsystemapp.repositories;

import com.banksystems.bankingsystemapp.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<BankAccount, Long> {

    BankAccount getBankAccountByAccountNumber(Integer accountNumber);
    BankAccount getBankAccountById(Long bankAccountId);
}
