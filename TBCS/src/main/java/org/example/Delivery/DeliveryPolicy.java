package org.example.Delivery;

import org.example.Order.Order;

public interface DeliveryPolicy {
    void assignDelivery(Order order);
}
