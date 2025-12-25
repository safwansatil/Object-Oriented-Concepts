package com.example;

public class Delivery {
    String method;
    Order order;
    double cost;
    public Delivery(String method, Order order, double cost) {
        this.method = method;
        this.order = order;
        this.cost = cost;
    }
    public Delivery(Order order) {
        this.method = "Dine-In";
        this.order = order;
        this.cost = order.calculateTotal();
    }
    boolean processDelivery(){
        // if(cost!=order.calculateTotal()){
        //     return false;
        // }
        return Payment.processPayment(method, cost);
    }
}
