package org.example;

public class CreditCardPayment implements Payment {
    
    @Override
    public PaymentResult process(double amount) {
        // process logic
        return null;
    }

    @Override
    public boolean supports(PaymentType type) {
        return type == PaymentType.CREDIT_CARD;
    }
}
