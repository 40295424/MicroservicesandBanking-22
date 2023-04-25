package com.bank.customer.service;

import com.bank.customer.model.Customer;
import com.bank.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository ;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer view(@NotNull  Integer customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public boolean delete(@NotNull Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()) {
            customerRepository.delete(customer.get());
            return true;
        } else {
            return false;
        }
    }

    public Customer save(@Valid Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        return view(newCustomer.getCustomerId());
    }

    public List<Customer> listCustomers() {
        return (List<Customer>)customerRepository.findAll() ;
    }

    public Customer fetchCustomerByLogin(String login) {
        return customerRepository.findByLoginId(login);
    }

}
