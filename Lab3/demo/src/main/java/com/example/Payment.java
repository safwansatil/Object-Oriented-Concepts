package com.example;

public class Payment {
    static boolean processPayment(String method, double amount) {
        if(amount <= 0 || !method.equals("Online")) {
            return false;
        }
        return true;
        
    }
}
