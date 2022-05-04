package com.banksystems.bankingsystemapp.services;

import com.banksystems.bankingsystemapp.exceptions.BankAccountNotFoundException;
import com.banksystems.bankingsystemapp.exceptions.NoFundsAvailableException;
import com.banksystems.bankingsystemapp.models.Transactions;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionsService {

    List<Transactions> newTransaction
            (
            Integer fromAccountNumber,
            Integer toAccountNumber,
            BigDecimal amount
            ) throws NoFundsAvailableException, BankAccountNotFoundException;

    List<Transactions> getTransactionsByBankAccountId(Long bankAccountId) throws BankAccountNotFoundException;
    List<Transactions> getTransactionsByBankAccountIdAndMonth(Long bankAccountId, Integer month) throws BankAccountNotFoundException;
    Transactions moneyDeposit(Long bankAccountId, BigDecimal amount);
    Transactions moneyPayOff(Long bankAccountId, BigDecimal amount) throws NoFundsAvailableException;
}

