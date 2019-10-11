package com.urise.webapp.lesson11;

public class AccountExc extends RuntimeException {
    public AccountExc(String message) {
        System.out.println(message);
    }
}
