package com.bank.customer.repository;

import com.bank.customer.model.Customer;
import com.bank.customer.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Integer> {

    List<Transaction> findAllByPayeeOrPayerOrderByTransactionTimestampDesc(Customer payee, Customer payer);
}
