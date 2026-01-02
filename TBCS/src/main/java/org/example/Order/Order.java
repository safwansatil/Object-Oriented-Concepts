package org.example.Order;

import org.example.Core.OrderStatus;
import org.example.Core.OrderType;
import org.example.Customer.Customer;

import java.util.List;

public class Order {
    private String orderId;
    Customer customer;
    List<OrderItem> items;
    OrderType type;
    private OrderStatus status;
    private double total;
    public String getOrderId(){
        return this.orderId;
    }
    public OrderStatus getOrderStatus(){
        return  this.status;
    }
    public void setStatus(OrderStatus status){
        this.status = status;
    }
    public double getOrderTotal(){
        return  this.total;
    }
    public void setTotalAmount(double amn){
        this.total = amn;
    }

}
