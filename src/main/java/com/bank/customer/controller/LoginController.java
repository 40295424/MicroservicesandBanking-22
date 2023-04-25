package com.bank.customer.controller;

import com.bank.customer.exception.NotFoundException;
import com.bank.customer.model.Customer;
import com.bank.customer.model.Login;
import com.bank.customer.model.Transaction;
import com.bank.customer.service.CustomerService;
import com.bank.customer.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @Value("${admin.pwd}")
    private String adminPwd;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Login login) throws NotFoundException, LoginException {
        if(login.getLoginId().equalsIgnoreCase("admin") &&
            login.getPassword().equalsIgnoreCase(adminPwd)) {
            return new ResponseEntity<>(Login.builder().loginId("admin").build(), HttpStatus.OK);
        }
        Customer customer = customerService.fetchCustomerByLogin(login.getLoginId());
        if(null != customer && customer.getPassword().equals(login.getPassword())) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            throw new LoginException("Login Failed");
        }
    }
}
