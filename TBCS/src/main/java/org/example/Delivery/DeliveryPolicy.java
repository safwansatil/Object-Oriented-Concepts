package org.example.Delivery;

import org.example.Order.Order;

import java.util.List;

public interface DeliveryPolicy {
    void assignDelivery(Order order);
    Driver assignDriver(List<Driver> drivers);
}
