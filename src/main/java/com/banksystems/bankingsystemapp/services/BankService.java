package com.banksystems.bankingsystemapp.services;

import com.banksystems.bankingsystemapp.models.BankAccount;

import java.util.Optional;

public interface BankService {
    Optional<BankAccount> getBankAccountById(Long id);
    BankAccount addNewBankAccount(BankAccount bankAccount);
    void deleteBankAccountById(Long bankAccountId);
}
