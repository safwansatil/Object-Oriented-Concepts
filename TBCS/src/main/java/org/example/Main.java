package org.example;

import org.example.Core.QueueManager;
import org.example.Customer.Customer;
import org.example.Customer.CustomerService;
import org.example.Customer.GuestCustomer;
import org.example.Customer.RegisteredCustomer;
import org.example.Delivery.*;
import org.example.Kitchen.KitchenService;
import org.example.Order.Order;
import org.example.Order.OrderItem;
import org.example.Order.OrderService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to the TasteBuds Catering System!");

        // core services
        QueueManager queueManager = new QueueManager();
        OrderService orderService = new OrderService(queueManager);
        KitchenService kitchenService = new KitchenService();
        CustomerService customerService = new CustomerService();

        // Delivery setup
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("LIC123"));

        DeliveryManager deliveryManager = new DeliveryManager(drivers);
        DeliveryService deliveryService = new DeliveryService();
        deliveryManager.setPolicy(new PriorityDeliveryPolicy());
        // Customer
        Customer customer = new RegisteredCustomer("safwansatil", "0123");

        // Items
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem("Burger", 2, 250));

        System.out.println("---- PLACING ORDER ----");
        Order order = orderService.createOrder(customer, items);

        System.out.println("---- KITCHEN PREPARATION ----");
        kitchenService.sendToKitchen(order);
        kitchenService.makeOrderReady(order);

        System.out.println("---- DELIVERY ASSIGNMENT ----");
        Driver driver = deliveryManager.assignDelivery(order);
        System.out.println("---- DRIVER CHECKOUT ----");
        if (deliveryService.checkoutDelivery(order, driver, "LIC123")) {
            deliveryService.markDelivered(order, driver);
        }

        System.out.println("---- CUSTOMER FEEDBACK ----");
        customerService.submitFeedback(order, 5, "Great food!");

    }
}