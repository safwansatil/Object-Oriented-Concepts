package org.example.Delivery;

import org.example.Order.Order;

import java.util.List;

public class NormalDeliveryPolicy implements DeliveryPolicy{
    @Override
    public void assignDelivery(Order order) {

    }
    @Override
    public Driver assignDriver(List<Driver> drivers) {
        for (Driver d : drivers) {
            if (d.getIsAvailable()) {
                System.out.println("Lucky! This is a Normal Order");
                System.out.println("However, due to availability of drivers we are assigning you driver");
                return d;
            }
        }
        return null;
    }
}
