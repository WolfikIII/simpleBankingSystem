package com.banksystems.bankingsystemapp.repositories;

import com.banksystems.bankingsystemapp.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> getTransactionsByBankAccountId(Long bankAccountId);

    @Query(nativeQuery = true, value = "SELECT * FROM transactions WHERE MONTH(transaction_date) =:month and bankAccountId =:bankAccountId")
    List<Transactions> getTransactionsByBankAccountIdAndMonth(Long bankAccountId, Integer month);

}
