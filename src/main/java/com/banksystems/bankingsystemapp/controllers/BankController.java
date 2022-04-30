package com.banksystems.bankingsystemapp.controllers;

import com.banksystems.bankingsystemapp.models.BankAccount;
import com.banksystems.bankingsystemapp.services.impl.BankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankAccount")
public class BankController {

    private final BankServiceImpl bankService;

    @Autowired
    public BankController(BankServiceImpl bankService){
        this.bankService = bankService;
    }

    @GetMapping("/{id}")
    public BankAccount getBankAccountById(@RequestParam Long bankAccountId){
        return bankService.getBankAccountById(bankAccountId);
    }

    @PostMapping("/")
    public ResponseEntity<BankAccount> addNewBankAccount(@RequestBody BankAccount bankAccount){
        return new ResponseEntity<>(bankService.addNewBankAccount(bankAccount), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteBankAccountById(@RequestParam Long bankAccountId){
        bankService.deleteBankAccountById(bankAccountId);
    }
}
