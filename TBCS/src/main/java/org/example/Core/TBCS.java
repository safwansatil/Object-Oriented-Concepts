package org.example.Core;

import org.example.Customer.Customer;
import org.example.Customer.CustomerService;
import org.example.Delivery.DeliveryManager;
import org.example.Kitchen.KitchenService;
import org.example.Order.Order;
import org.example.Order.OrderItem;
import org.example.Order.OrderService;

import java.util.List;

public class TBCS {
    QueueManager queueManager;
    OrderService orderService;
    KitchenService kitchenService;
    DeliveryManager deliveryManager;
    CustomerService customerService;
    Order placeOrder(Order order, List<OrderItem> items){
        return null;
    }
    void sendOrderToKitchen(Order order){

    }
    void handleReadyOrder(Order order){

    }
    void assignDelivery(Order order){

    }
    void completeDelivery(Order order){

    }
    void recieveFeedback(Order order, double rating, String feedback){

    }
}
