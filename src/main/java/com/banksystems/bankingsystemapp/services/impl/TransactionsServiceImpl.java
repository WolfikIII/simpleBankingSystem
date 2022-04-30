package com.banksystems.bankingsystemapp.services.impl;

import com.banksystems.bankingsystemapp.exceptions.BankAccountNotFoundException;
import com.banksystems.bankingsystemapp.exceptions.NoFundsAvailableException;
import com.banksystems.bankingsystemapp.models.BankAccount;
import com.banksystems.bankingsystemapp.models.Transactions;
import com.banksystems.bankingsystemapp.repositories.BankRepository;
import com.banksystems.bankingsystemapp.repositories.TransactionsRepository;
import com.banksystems.bankingsystemapp.services.TransactionsService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private TransactionsRepository transactionsRepository;
    private BankRepository bankRepository;

    @Autowired
    public TransactionsServiceImpl(
            TransactionsRepository transactionsHistoryRepository,
            BankRepository bankRepository
    ){
        this.transactionsRepository = transactionsHistoryRepository;
        this.bankRepository = bankRepository;
    }

    @Transactional
    @Override
    public Transactions moneyDeposit(Long bankAccountId, BigDecimal amount){
        BankAccount bankAccount = bankRepository.getBankAccountById(bankAccountId);
        if(bankAccount.equals(null)){
            throw new NullPointerException("Bank account not found");
        }
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new ValueException("The operation cannot be performed, an incorrect amount has been entered");
        }
        BigDecimal saldoBeforeDepositMoney = bankAccount.getSaldo();
        BigDecimal saldoAfterDepositMoney = saldoBeforeDepositMoney.add(amount);
        bankAccount.setSaldo(saldoAfterDepositMoney);
        bankRepository.save(bankAccount);
        Transactions transactions = new Transactions();
        transactions.setSaldoBefore(saldoBeforeDepositMoney);
        transactions.setSaldoAfter(saldoAfterDepositMoney);
        transactions.setTransactionDate(Instant.now());
        transactions.setBankAccountId(bankAccountId);
        return transactionsRepository.save(transactions);
    }

    @Transactional
    @Override
    public Transactions moneyPayOff(Long bankAccountId, BigDecimal amount) throws NoFundsAvailableException {
        BankAccount bankAccount = bankRepository.getBankAccountById(bankAccountId);
        if(bankAccount.equals(null))
        {
            throw new NullPointerException("Bank account not found");
        }
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new ValueException("The operation cannot be performed, an incorrect amount has been entered");
        }
        BigDecimal saldoBeforePayOffMoney = bankAccount.getSaldo();
        if(saldoBeforePayOffMoney.subtract(amount).compareTo(BigDecimal.ZERO)<0){
            throw new NoFundsAvailableException("No available funds");
        }
        BigDecimal saldoAfterPayOffMoney = saldoBeforePayOffMoney.add(amount);
        bankAccount.setSaldo(saldoAfterPayOffMoney);
        Long clientId = bankAccount.getClientId();
        bankRepository.save(bankAccount);
        Transactions transaction = Transactions.builder()
                .bankAccountId(bankAccountId)
                .clientId(clientId)
                .transactionDate(Instant.now())
                .saldoBefore(saldoBeforePayOffMoney)
                .saldoAfter(saldoAfterPayOffMoney)
                .build();
        return transactionsRepository.save(transaction);
    }

    @Transactional
    @Override
    public List<Transactions> newTransaction
            (
            Integer fromAccountNumber,
            Integer toAccountNumber,
            BigDecimal amount
            )
            throws NoFundsAvailableException, BankAccountNotFoundException
    {
        BankAccount recipientBankAccount = bankRepository.getBankAccountByAccountNumber(toAccountNumber);
        BankAccount senderBankAccount = bankRepository.getBankAccountByAccountNumber(fromAccountNumber);
        if(recipientBankAccount == null || senderBankAccount == null)
        {
            throw new BankAccountNotFoundException("Bank account not found");
        }
        BigDecimal recipientSaldoBefore = recipientBankAccount.getSaldo();
        Long recipientBankAccountId = recipientBankAccount.getId();
        Long recipientClientId = recipientBankAccount.getClientId();
        Long senderBankAccountId = senderBankAccount.getId();
        Long senderClientId = senderBankAccount.getClientId();
        BigDecimal recipientSaldoAfter = recipientSaldoBefore.add(amount);
        Transactions recipientTransaction = Transactions.builder()
                .bankAccountId(recipientBankAccountId)
                .transactionDate(Instant.now())
                .saldoBefore(recipientSaldoBefore)
                .saldoAfter(recipientSaldoAfter)
                .clientId(recipientClientId)
                .fromBankAccountId(senderBankAccountId)
                .build();
        BigDecimal senderSaldoBefore = senderBankAccount.getSaldo();
        BigDecimal senderSaldoAfter = senderSaldoBefore.subtract(amount);
        if(senderSaldoBefore.subtract(amount).compareTo(BigDecimal.ZERO)<0){
            throw new NoFundsAvailableException("No available funds");
        }

        Transactions senderTransaction = Transactions.builder()
                .bankAccountId(senderBankAccountId)
                .transactionDate(Instant.now())
                .saldoBefore(senderSaldoBefore)
                .saldoAfter(senderSaldoAfter)
                .clientId(senderClientId)
                .fromBankAccountId(recipientBankAccountId)
                .build();
        List<Transactions> transactionsList = new ArrayList<>();
        transactionsList.add(recipientTransaction);
        transactionsList.add(senderTransaction);
        recipientBankAccount.setSaldo(recipientSaldoAfter);
        senderBankAccount.setSaldo(senderSaldoAfter);
        bankRepository.save(recipientBankAccount);
        bankRepository.save(senderBankAccount);
        return transactionsRepository.saveAll(transactionsList);
    }

    //ZASTOSOWAC BUILDERA MOZE BEDZIE OPTYMALNIEJ
    //WYJATKI DO USERA (NP NIE POWTARZAJACY SIE LOGIN

    @Override
    public List<Transactions> getTransactionsByBankAccountId(Long bankAccountId){
        return transactionsRepository.getTransactionsByBankAccountId(bankAccountId);
    }

    @Override
    public List<Transactions> getTransactionsByBankAccountIdAndMonth(Long bankAccountId, Integer month){
        return transactionsRepository.getTransactionsByBankAccountIdAndMonth(bankAccountId, month);
    }

}
