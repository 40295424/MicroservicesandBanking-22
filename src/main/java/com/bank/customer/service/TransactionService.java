package com.bank.customer.service;

import com.bank.customer.model.Customer;
import com.bank.customer.model.Transaction;
import com.bank.customer.model.TransactionAnalysis;
import com.bank.customer.model.TransactionType;
import com.bank.customer.repository.CustomerRepository;
import com.bank.customer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository ;
    @Autowired
    private final CustomerRepository customerRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    public Transaction view(@NotNull  Integer transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

    public boolean delete(@NotNull Integer transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if(transaction.isPresent()) {
            BigDecimal amount = transaction.get().getAmount();
            transaction.get().getPayee().setBankBalance(transaction.get().getPayee().getBankBalance().subtract(amount));
            transaction.get().getPayer().setBankBalance(transaction.get().getPayer().getBankBalance().add(amount));
            transactionRepository.delete(transaction.get());
            return true;
        } else {
            return false;
        }
    }

    public Transaction save(@Valid Transaction transaction) {
        if(TransactionType.CASH_DEPOSIT.name().equalsIgnoreCase(transaction.getTransactionType())) {

            Optional<Customer> payee = customerRepository.findById(transaction.getPayee().getCustomerId());
            payee.get().setBankBalance(payee.get().getBankBalance().add(transaction.getAmount()));
            customerRepository.save(payee.get());
            transaction.setPayee(payee.get());
            transaction.setPayeeBalance(payee.get().getBankBalance());

        }

        if(TransactionType.CASH_WITHDRAWAL.name().equalsIgnoreCase(transaction.getTransactionType())) {

            Optional<Customer> payer = customerRepository.findById(transaction.getPayer().getCustomerId());
            payer.get().setBankBalance(payer.get().getBankBalance().subtract(transaction.getAmount()));
            customerRepository.save(payer.get());
            transaction.setPayer(payer.get());
            transaction.setPayerBalance(payer.get().getBankBalance());

        }

        if(TransactionType.ACCOUNT_TO_ACCOUNT.name().equalsIgnoreCase(transaction.getTransactionType())) {

            Optional<Customer> payer = customerRepository.findById(transaction.getPayer().getCustomerId());
            Optional<Customer> payee = customerRepository.findById(transaction.getPayee().getCustomerId());
            payer.get().setBankBalance(payer.get().getBankBalance().subtract(transaction.getAmount()));
            payee.get().setBankBalance(payee.get().getBankBalance().add(transaction.getAmount()));
            customerRepository.save(payer.get());
            customerRepository.save(payee.get());
            transaction.setPayee(payee.get());
            transaction.setPayer(payer.get());
            transaction.setPayeeBalance(payee.get().getBankBalance());
            transaction.setPayerBalance(payer.get().getBankBalance());
        }


        Transaction newTransaction = transactionRepository.save(transaction);
        return view(newTransaction.getTransactionId());
    }

    public List<Transaction> listTransaction() {
        return (List<Transaction>)transactionRepository.findAll() ;
    }

    public List<Transaction> listByCustomer(Customer customer) {
        return transactionRepository.findAllByPayeeOrPayerOrderByTransactionTimestampDesc(customer,customer) ;
    }

    public List<TransactionAnalysis> listTransactionAnalysis(Customer customer) {
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) transactionRepository.findAll();
        return transactionRepository.fetchTransactionAnalysis(customer.getCustomerId()) ;
    }

}
