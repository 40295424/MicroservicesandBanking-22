package com.bank.customer.repository;

import com.bank.customer.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Integer> {

    List<Transaction> findAllByPayee(String payee);
}
