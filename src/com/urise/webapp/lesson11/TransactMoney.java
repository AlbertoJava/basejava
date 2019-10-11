package com.urise.webapp.lesson11;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class TransactMoney {
    public static void main(String[] args) {
        TransactMoney tm = new TransactMoney();
        Account a1 = new Account(1000);
        Account a2 =new Account(2000);
        new Thread(() -> {
            System.out.println("Thread " + Thread.currentThread().getName() + " starts transferring money!");
            tm.transferMoney (a1,a2,500);
            System.out.println("Thread " + Thread.currentThread().getName() + " has transferred money!");
        }).start();
        System.out.println("Thread " + Thread.currentThread().getName() + " starts transferring money!");
        tm.transferMoney(a2,a1,700);
        System.out.println("Thread " + Thread.currentThread().getName() + " has transferred money!");
    }

    public void transferMoney (Account a1, Account a2, int sum){
       //dead lock
        synchronized (a1) {
           try {
               currentThread().sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           synchronized (a2) {
               a1.withdraw(sum);
               a2.add(sum);
           }
       }
    }

}
