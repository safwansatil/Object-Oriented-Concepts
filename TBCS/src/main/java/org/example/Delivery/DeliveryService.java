package org.example.Delivery;

import org.example.Core.OrderStatus;
import org.example.Order.Order;

public class DeliveryService {
    public boolean validateLicense(Driver driver,String licenseNum){
        return driver.getLicenseNum().equals(licenseNum);
    }
    public boolean checkoutDelivery(Order order, Driver driver, String licenseNum){
        System.out.println(
                "Driver attempting checkout for Order " + order.getOrderId()
        );
        if (validateLicense(driver, licenseNum)) {
            order.setOrderStatus(OrderStatus.EN_ROUTE);

            System.out.println("Driver checked out successfully");

        } else {
            System.out.println("Checkout failed, Invalid license. Delivery denied.");
            return false;
        }
        return true;
    }
    public void markDelivered(Order order, Driver driver){

        order.setOrderStatus(OrderStatus.DELIVERED);
        driver.setIsAvailable(true);
        System.out.println("Order " + order.getOrderId() + " DELIVERED");
    }
}
