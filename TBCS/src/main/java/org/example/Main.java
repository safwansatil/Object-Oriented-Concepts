package org.example;

import org.example.Core.QueueManager;
import org.example.Customer.Customer;
import org.example.Customer.GuestCustomer;
import org.example.Kitchen.KitchenService;
import org.example.Order.Order;
import org.example.Order.OrderItem;
import org.example.Order.OrderService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome to the TasteBuds Catering System!");
        QueueManager queueManager = new QueueManager();
        OrderService orderService = new OrderService(queueManager);
        KitchenService kitchenService = new KitchenService();

        // dummy
        Customer customer = new GuestCustomer("Satil", "0123456789");


        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Burger", 2, 250));
        items.add(new OrderItem("Fries", 1, 120));


        Order order = orderService.createOrder(customer, items);
        kitchenService.sendToKitchen(order);
        kitchenService.makeOrderReady(order);

        System.out.println("Final Order Status: " + order.getOrderStatus());
    }
}