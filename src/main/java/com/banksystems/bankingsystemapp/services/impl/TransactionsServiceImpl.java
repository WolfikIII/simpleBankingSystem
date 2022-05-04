package com.banksystems.bankingsystemapp.services.impl;

import com.banksystems.bankingsystemapp.exceptions.BankAccountNotFoundException;
import com.banksystems.bankingsystemapp.exceptions.NoFundsAvailableException;
import com.banksystems.bankingsystemapp.models.BankAccount;
import com.banksystems.bankingsystemapp.models.Transactions;
import com.banksystems.bankingsystemapp.models.User;
import com.banksystems.bankingsystemapp.repositories.BankRepository;
import com.banksystems.bankingsystemapp.repositories.TransactionsRepository;
import com.banksystems.bankingsystemapp.services.TransactionsService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
        Long clientId = bankAccount.getClientId();
        if(!bankRepository.findById(bankAccountId).isPresent()){
            throw new NullPointerException("Bank account not found");
        }
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new ValueException("The operation cannot be performed, an incorrect amount has been entered");
        }
        BigDecimal saldoBeforeDepositMoney = bankAccount.getSaldo();
        BigDecimal saldoAfterDepositMoney = saldoBeforeDepositMoney.add(amount);
        bankAccount.setSaldo(saldoAfterDepositMoney);
        bankRepository.save(bankAccount);
        Transactions moneyDeposit = Transactions.builder()
                .saldoBefore(saldoBeforeDepositMoney)
                .saldoAfter(saldoAfterDepositMoney)
                .bankAccountId(bankAccountId)
                .transactionDate(Instant.now())
                .clientId(clientId)
                .build();
        return transactionsRepository.save(moneyDeposit);
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
        BigDecimal saldoAfterPayOffMoney = saldoBeforePayOffMoney.subtract(amount);
        bankAccount.setSaldo(saldoAfterPayOffMoney);
        Long clientId = bankAccount.getClientId();
        bankRepository.save(bankAccount);
        Transactions moneyPayOff = Transactions.builder()
                .bankAccountId(bankAccountId)
                .clientId(clientId)
                .transactionDate(Instant.now())
                .saldoBefore(saldoBeforePayOffMoney)
                .saldoAfter(saldoAfterPayOffMoney)
                .build();
        return transactionsRepository.save(moneyPayOff);
    }

    @Transactional
    @Override
    public List<Transactions> newTransaction(
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

    @Override
    public List<Transactions> getTransactionsByBankAccountId(Long bankAccountId) throws BankAccountNotFoundException {
        if(transactionsRepository.getTransactionsByBankAccountId(bankAccountId).size()==0 && bankRepository.existsById(bankAccountId))
            throw new BankAccountNotFoundException("Transactions for bank account not found");
        if(!bankRepository.findById(bankAccountId).isPresent())
            throw new NullPointerException("Bank account not found");
        return transactionsRepository.getTransactionsByBankAccountId(bankAccountId);
    }

    @Override
    public List<Transactions> getTransactionsByBankAccountIdAndMonth(Long bankAccountId, Integer month) throws BankAccountNotFoundException {
        if(!bankRepository.findById(bankAccountId).isPresent())
            throw new BankAccountNotFoundException("Bank account not found");
        if(transactionsRepository.getTransactionsByBankAccountIdAndMonth(bankAccountId, month).size()==0 && bankRepository.findById(bankAccountId).isPresent())
            throw new NullPointerException("Transactions not found");
        return transactionsRepository.getTransactionsByBankAccountIdAndMonth(bankAccountId, month);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void basicDataTransactions(){
        transactionsRepository.save(new Transactions(1L, 1L, Instant.now(), BigDecimal.valueOf(3000), BigDecimal.valueOf(5000), 1L, 2L));

    }
}
