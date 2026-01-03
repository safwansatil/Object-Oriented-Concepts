package org.example.Order;

import org.example.Core.OrderStatus;
import org.example.Core.OrderType;
import org.example.Customer.Customer;

import java.util.List;

public class Order {
    private int orderId;
    private Customer customer;
    private List<OrderItem> items;
    private OrderType type;
    private OrderStatus status;
    private double total;
    public int getOrderId(){
        return this.orderId;
    }
    public void setOrderId(int id){
        this.orderId = id;
    }
    public OrderStatus getOrderStatus(){
        return  this.status;
    }
    public void setOrderStatus(OrderStatus status){
        this.status = status;
    }
    public double getOrderTotal(){
        return  this.total;
    }
    public void setTotalAmount(double amn){
        this.total = amn;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public OrderType getOrderType(){
        return this.type;
    }
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
    public void printOrderDetails(){
        System.out.println("Order Details: ");
        System.out.println("\tOrder ID: "+ this.orderId);
        System.out.println("\tCustomer: "+ this.customer.getName());
        System.out.println("\tOrder Status: "+ this.getOrderStatus());
        System.out.println("\tTotal Price: "+this.getOrderTotal());
    }
}
