package org.example.Delivery;

import org.example.Order.Order;

import java.util.List;

public class PriorityDeliveryPolicy implements DeliveryPolicy{
    @Override
    public void assignDelivery(Order order) {

    }
    @Override
    public Driver assignDriver(List <Driver> drivers) {
        for (Driver d : drivers) {
            if (d.getIsAvailable()) {
                return d;
            }
        }
        return null;
    }
}
