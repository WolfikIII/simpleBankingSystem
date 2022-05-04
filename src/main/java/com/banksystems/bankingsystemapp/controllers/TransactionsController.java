package com.banksystems.bankingsystemapp.controllers;
import com.banksystems.bankingsystemapp.exceptions.BankAccountNotFoundException;
import com.banksystems.bankingsystemapp.exceptions.NoFundsAvailableException;
import com.banksystems.bankingsystemapp.models.Transactions;
import com.banksystems.bankingsystemapp.services.impl.TransactionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {

    private final TransactionsServiceImpl transactionsHistoryService;

    @Autowired
    public TransactionsController(TransactionsServiceImpl transactionsHistoryService){
        this.transactionsHistoryService =transactionsHistoryService;
    }

    @PutMapping("/")
    public ResponseEntity<List<Transactions>> newTransaction(
            @RequestParam Integer fromAccountNumber,
            @RequestParam Integer toAccountNumber,
            @RequestParam BigDecimal amount
    ) throws NoFundsAvailableException, BankAccountNotFoundException {
        return new ResponseEntity<>(transactionsHistoryService.newTransaction(fromAccountNumber, toAccountNumber, amount), HttpStatus.ACCEPTED);
    }

    @GetMapping("/bankAccountId")
    public List<Transactions> getTrasactionsByBankAccountId(@RequestParam Long bankAccountId) throws BankAccountNotFoundException {
        return transactionsHistoryService.getTransactionsByBankAccountId(bankAccountId);
    }

    @GetMapping("/byBankAccountIdAndMonth")
    public List<Transactions> getTransactionsByBankAccountIdAndMonth(
            @RequestParam Long bankAccountId,
            @RequestParam Integer month
    ) throws BankAccountNotFoundException {
        return transactionsHistoryService.getTransactionsByBankAccountIdAndMonth(bankAccountId, month);
    }

    @PatchMapping("/moneyDeposit/{bankAccountId}")
    public ResponseEntity<Transactions> moneyDeposit(
            @PathVariable Long bankAccountId,
            @RequestParam BigDecimal amount){
        return new ResponseEntity<>(
                transactionsHistoryService.moneyDeposit(bankAccountId, amount),
                HttpStatus.ACCEPTED);
    }

    @PatchMapping("/moneyPayOff/{bankAccountId}")
    public ResponseEntity<Transactions> monePayOff(
            @PathVariable Long bankAccountId,
            @RequestParam BigDecimal amount)
            throws NoFundsAvailableException {
        return new ResponseEntity<>(
                transactionsHistoryService.moneyPayOff(bankAccountId, amount),
                HttpStatus.ACCEPTED);
    }
}
