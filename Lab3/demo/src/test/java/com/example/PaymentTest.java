package com.example;
// to test payment class 

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PaymentTest {
    @Test
    public void testProcessPayment() {

        // valid
        //assertEquals(Payment.processPayment("Online", 100.0), true);
        assertEquals(true, Payment.processPayment("Online", 10000));
        // invalid cases
        assertEquals(false, Payment.processPayment("Cash", 100.0));
        assertEquals(false,Payment.processPayment("On-line", 0.0));
        assertEquals(false, Payment.processPayment("Online", -50.0));
    }
}
