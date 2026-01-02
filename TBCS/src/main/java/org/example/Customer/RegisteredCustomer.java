package org.example.Customer;

public class RegisteredCustomer extends Customer{
    int monthlyOrders;

    public RegisteredCustomer(String name, String contact) {
        super(name, contact);
    }

    double calculateDisc(int monthlyOrders){
        return 0;
    }
}
