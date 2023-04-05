package com.bank.customer.controller;

import com.bank.customer.exception.BadRequestException;
import com.bank.customer.exception.NotFoundException;
import com.bank.customer.model.Customer;
import com.bank.customer.model.Transaction;
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

    /*Each of the methods here will be integrated into the buttons that are on the customer homepage/dashboard
    however, to see what the buttons are  going to be doing once they are integrated hal explorer is used
    JSON Hypertext Application Language we can use the explorer to navigate to certain endpoints for example we can go to the
    customer endpoint add a transaction, view a transaction using the transaction id. The same thing can be done
    with the customer controller.   */

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
        Boolean deleted = transactionService.delete(transactionId);
        if(!deleted ) {
            throw new BadRequestException((String.format("Transaction %s not delete",transactionId)));
        }
        return new ResponseEntity<String>("Deleted" , HttpStatus.OK);
        /**/
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> listTransaction() throws NotFoundException {
        List<Transaction> transaction =  transactionService.listTransaction();
        return new ResponseEntity<List<Transaction>>(transaction, HttpStatus.OK);
        /**/
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/transaction")
    public ResponseEntity<Transaction> saveTransation(@Valid @RequestBody Transaction transaction) throws NotFoundException {
        return new ResponseEntity<Transaction>(transactionService.save(transaction),HttpStatus.OK);
        /**/
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/customers/{customerId}/transactions")
    public ResponseEntity<List<Transaction>> listTransactionByPayee(@NotNull @PathVariable Integer customerId) throws NotFoundException {
        Customer customer = customerService.view(customerId);
        List<Transaction> transaction =  transactionService.listTransactionByPayee(customer.getAccountNumber());
        return new ResponseEntity<List<Transaction>>(transaction, HttpStatus.OK);
        /**/
    }


}
