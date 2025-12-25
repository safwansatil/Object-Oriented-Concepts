package com.example;

import java.util.List;

public class Customers {
    List<Order> pastOrders = new java.util.ArrayList<>();
    public Customers(){
        
    }
    void addOrder(Order order){
        this.pastOrders.add(order);
    }
    List<Order> getPastOrders(){
        return this.pastOrders;
    }
}
