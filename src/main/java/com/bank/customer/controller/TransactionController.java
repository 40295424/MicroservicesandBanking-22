package com.bank.customer.controller;

import com.bank.customer.exception.BadRequestException;
import com.bank.customer.exception.NotFoundException;
import com.bank.customer.model.Customer;
import com.bank.customer.model.Transaction;
import com.bank.customer.model.TransactionType;
import com.bank.customer.service.CustomerService;
import com.bank.customer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Transaction> retrieveTransaction(@NotNull @PathVariable Integer transactionId) throws NotFoundException {
        Transaction transaction = transactionService.view(transactionId);
        if(transaction == null ) {
            throw new NotFoundException(String.format("Transaction %s not found",transactionId));
        }
        return new ResponseEntity<Transaction>(transaction , HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/transaction/{transactionId}")
    public ResponseEntity<String>  deleteTransaction(@NotNull @PathVariable Integer transactionId) throws NotFoundException, BadRequestException {
        Transaction transaction = transactionService.view(transactionId);
        if(transaction == null) {
            throw new NotFoundException(String.format("Transaction %s not found",transactionId));
        }
        Boolean deleted = transactionService.delete(transactionId);
        if(!deleted ) {
            throw new BadRequestException((String.format("Transaction %s not delete",transactionId)));
        }
        return new ResponseEntity<String>("Deleted" , HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> listTransaction() throws NotFoundException {
        List<Transaction> transaction =  transactionService.listTransaction();
        return new ResponseEntity<List<Transaction>>(transaction, HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/transaction")
    public ResponseEntity<Transaction> saveTransation(@Valid @RequestBody Transaction transaction) throws  BadRequestException {
        Customer payer = null ;
        try {
            payer = customerService.view(transaction.getPayer().getCustomerId());
        } catch (NullPointerException exception) {

        }
        Customer payee = null ;
        try {
            payee = customerService.view(transaction.getPayee().getCustomerId());
        } catch (NullPointerException exception) {

        }
        if(payee == null
                && TransactionType.CASH_DEPOSIT.name().equalsIgnoreCase(transaction.getTransactionType()) ) {
            throw new BadRequestException((String.format("Transaction  not executed invalid payer %s /payee %s",
                    transaction.getPayer().getCustomerId(),
                    transaction.getPayee().getCustomerId())));
        }

        if(payer == null
                && TransactionType.CASH_WITHDRAWAL.name().equalsIgnoreCase(transaction.getTransactionType()) ) {
            throw new BadRequestException((String.format("Transaction  not executed invalid payer %s /payee %s",
                    transaction.getPayer().getCustomerId(),
                    transaction.getPayee().getCustomerId())));
        }

        if( ( payee == null ||  payer == null )
                && TransactionType.ACCOUNT_TO_ACCOUNT.name().equalsIgnoreCase(transaction.getTransactionType()) ) {
            throw new BadRequestException((String.format("Transaction  not executed invalid payer %s /payee %s",
                    transaction.getPayer().getCustomerId(),
                    transaction.getPayee().getCustomerId())));
        }
        return new ResponseEntity<Transaction>(transactionService.save(transaction),HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/customer/{customerId}/transactions")
    public ResponseEntity<List<Transaction>> listTransactionByPayee(@NotNull @PathVariable Integer customerId) throws NotFoundException {
        Customer customer = customerService.view(customerId);
        if(customer == null) {
            throw new NotFoundException(String.format("Customer %s not found  ", customerId));
        }
        List<Transaction> transaction =  transactionService.listByCustomer(customer);
        return new ResponseEntity<List<Transaction>>(transaction, HttpStatus.OK);

    }


}
