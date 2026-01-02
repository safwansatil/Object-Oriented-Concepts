package org.example.Delivery;

import org.example.Core.OrderStatus;
import org.example.Order.Order;

import java.util.List;

public class DeliveryManager {
    private DeliveryPolicy policy;
    private List<Driver> drivers;
    public DeliveryManager(List<Driver> drivers) {
        this.drivers = drivers;
    }
    public void setPolicy(DeliveryPolicy policy){
        this.policy = policy;
    }
    public Driver assignDelivery(Order order){
        Driver driver = policy.assignDriver(drivers);
        if (driver != null) {
            driver.setIsAvailable(false);
            order.setOrderStatus(OrderStatus.EN_ROUTE);
            System.out.println(
                    "Order " + order.getOrderId() +
                            " assigned to driver " + driver.getLicenseNum()
            );
        }
        return driver;
    }
}
