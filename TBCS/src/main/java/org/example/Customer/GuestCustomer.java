package org.example.Customer;

public class GuestCustomer extends  Customer{
    double calculateDisc(int monthlyOrders){
        System.out.println("To get discount, customer needs to be registered~");
        return 0;
    }
}
