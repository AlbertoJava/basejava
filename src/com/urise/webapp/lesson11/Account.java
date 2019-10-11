package com.urise.webapp.lesson11;

public class Account {
    int balance;

    public Account(int balance) {
        this.balance=balance;
    }

    public  void  withdraw (int sum){
        if (sum>balance){
            throw new AccountExc ("Exceeded balance amount");
        }
        balance=-sum;
    }

    public void add (int sum){
        balance+=sum;
    }

    public void transferMoney (Account recipient){

    }

}
