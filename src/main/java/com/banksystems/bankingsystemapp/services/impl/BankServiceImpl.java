package com.banksystems.bankingsystemapp.services.impl;

import com.banksystems.bankingsystemapp.models.BankAccount;
import com.banksystems.bankingsystemapp.models.Client;
import com.banksystems.bankingsystemapp.models.Transactions;
import com.banksystems.bankingsystemapp.repositories.BankRepository;
import com.banksystems.bankingsystemapp.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository){
        this.bankRepository = bankRepository;
    }

    @Override
    public BankAccount getBankAccountById(Long bankAccountId){
        return bankRepository.getBankAccountById(bankAccountId);
    }

    @Override
    public BankAccount addNewBankAccount(BankAccount bankAccount){
        return bankRepository.save(bankAccount);
    }

    @Override
    public void deleteBankAccountById(Long bankAccountId){
        bankRepository.deleteById(bankAccountId);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void basicDataBankAccounts(){
        List<Transactions> transactions = new ArrayList<>();
        bankRepository.save(new BankAccount(1L, 1L, 1L, 111111, BigDecimal.valueOf(5000), transactions));
        bankRepository.save(new BankAccount(2L, 2L, 2L, 222222, BigDecimal.valueOf(5000), transactions));
        bankRepository.save(new BankAccount(3L, 3L, 3L, 333333, BigDecimal.valueOf(5000), transactions));
        bankRepository.save(new BankAccount(4L, 4L, 4L, 444444, BigDecimal.valueOf(5000), transactions));
    }


}
