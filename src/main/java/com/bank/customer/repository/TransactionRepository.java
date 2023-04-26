package com.bank.customer.repository;

import com.bank.customer.model.Customer;
import com.bank.customer.model.Transaction;
import com.bank.customer.model.TransactionAnalysis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Integer> {

    List<Transaction> findAllByPayeeOrPayerOrderByTransactionTimestampDesc(Customer payee, Customer payer);
    @Query(value =
            "SELECT new com.bank.customer.model.TransactionAnalysis (t.paymentType, SUM(t.amount ))"+
                    "FROM Transaction t " +
                    "WHERE  t.payer.customerId =  :customerId " +
                    "GROUP BY t.paymentType "
    )
    List<TransactionAnalysis> fetchTransactionAnalysis(Integer customerId);
}
