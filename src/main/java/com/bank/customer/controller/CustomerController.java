package com.bank.customer.controller;

import com.bank.customer.exception.BadRequestException;
import com.bank.customer.exception.NotFoundException;
import com.bank.customer.model.Customer;
import com.bank.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import jakarta.validation.constraints.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> retrieveCustomer(@NotNull @PathVariable Integer customerId) throws NotFoundException {
        Customer customer = customerService.view(customerId);
        if(customer == null ) {
            throw new NotFoundException(String.format("Customer %s not found",customerId));
        }
        return  new ResponseEntity<Customer>(customer , HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws NotFoundException, BadRequestException {
         Boolean deleted = customerService.delete(customerId);
        if(!deleted ) {
            throw new BadRequestException((String.format("Customer %s not delete",customerId)));
        }
        return new ResponseEntity<String>("Deleted" , HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> listCustomer() throws NotFoundException {
        List<Customer> customers =  customerService.listCustomers();
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/customer")
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) throws NotFoundException {
        return new ResponseEntity<Customer>(customerService.save(customer),HttpStatus.OK);
    }


}
