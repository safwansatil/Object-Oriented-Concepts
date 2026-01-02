package org.example.Customer;

public class GuestCustomer extends  Customer{
    public GuestCustomer(String name, String contact) {
        super(name, contact);
    }

    double calculateDisc(int monthlyOrders){
        System.out.println("To get discount, customer needs to be registered~");
        return 0;
    }
}
