package org.example;

import java.util.List;

// model class
public class Order {
    private String id;
    private List<Item> items;
    private PaymentType paymentType;

    public String getId() { return id; }
    public List<Item> getItems() { return items; }
    public PaymentType getPaymentType() { return paymentType; }

    public double calculateTotal() {
        return 17.17;
    }
}
