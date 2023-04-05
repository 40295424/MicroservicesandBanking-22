package com.bank.customer.service;

import com.bank.customer.model.Transaction;
import com.bank.customer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {


    private final TransactionRepository transactionRepository ;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction view(@NotNull  Integer transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

    public boolean delete(@NotNull Integer customerId) {
        Optional<Transaction> transaction = transactionRepository.findById(customerId);
        if(transaction.isPresent()) {
            transactionRepository.delete(transaction.get());
            return true;
        } else {
            return false;
        }
    }

    public Transaction save(@Valid Transaction transaction) {
        Transaction newTransaction = transactionRepository.save(transaction);
        return view(newTransaction.getTransactionId());
    }

    public List<Transaction> listTransaction() {
        return (List<Transaction>)transactionRepository.findAll() ;
    }

    public List<Transaction> listTransactionByPayee(String payee) {
        return transactionRepository.findAllByPayee(payee) ;
    }

}
