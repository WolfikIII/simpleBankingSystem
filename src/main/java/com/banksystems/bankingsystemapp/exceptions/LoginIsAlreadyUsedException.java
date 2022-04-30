package com.banksystems.bankingsystemapp.exceptions;

public class LoginIsAlreadyUsedException extends Exception{

    public LoginIsAlreadyUsedException(String s){
        super(s);
    }
}
