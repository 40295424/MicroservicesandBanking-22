package com.bank.customer.exception;

public class LoginException extends Exception{
    public LoginException(String errorMessage) {
        super(errorMessage);
    }
}
