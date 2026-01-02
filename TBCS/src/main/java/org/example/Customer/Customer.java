package org.example.Customer;

import org.example.Order.Order;

public abstract class Customer {
    private String name;
    private String address;
    private String contact;
    abstract double calculateDisc(int monthlyOrders);
    public Customer(String name, String contact){
        this.name = name;
        this.contact = contact;
    }
    public String getName(){
        return this.name;
    }
    public void getCustomerDetails(){
        System.out.println("Customer Name: " + this.name);
        System.out.println("Customer Address: " + this.address);
        System.out.println("Contact them through: "+ this.contact);
    }

}
