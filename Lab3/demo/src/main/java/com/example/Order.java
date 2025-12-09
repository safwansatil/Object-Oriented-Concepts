package com.example;

import java.util.List;

public class Order {
	Customers customer;
    List<Items> items;
    String deliveryMethod;
    String oId;

    double calculateTotal() {
        return items.size() * 1000.0;
    }

    public Order(Customers customer, List<Items> items, String deliveryMethod) {
        this.customer = customer;
        this.items = items;
        this.deliveryMethod = deliveryMethod;
    }
    public Order(Customers customers, List<Items> items) {
        this.customer = customers;
        this.items = items;
        this.deliveryMethod = "dineIn";
    }
    public Order(){

    }
    public Order (String oId){
        this.oId = oId;
    }

    boolean processOrder() {
        Delivery delivery = new Delivery(this);
        return delivery.processDelivery();
    }

    public String getoId() {
        return oId;
    }

}
