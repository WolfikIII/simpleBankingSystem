package com.banksystems.bankingsystemapp.services;

import com.banksystems.bankingsystemapp.models.BankAccount;

public interface BankService {
    BankAccount getBankAccountById(Long id);
    BankAccount addNewBankAccount(BankAccount bankAccount);
    void deleteBankAccountById(Long bankAccountId);
}
