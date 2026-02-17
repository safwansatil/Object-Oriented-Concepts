package org.example;


public interface Payment {
    PaymentResult process(double amount);
    boolean supports(PaymentType type);
}
