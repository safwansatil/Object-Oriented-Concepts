package org.example.Delivery;

import org.example.Core.OrderStatus;
import org.example.Order.Order;

public class DeliveryService {
    public boolean validateLicense(String licenseNum){
        if(!licenseNum.isEmpty()){return true;}
        return false;
    }
    public void checkoutDelivery(Order order, String licenseNum){

    }
    public void markDelivered(Order order){
        order.setOrderStatus(OrderStatus.DELIVERED);
    }
}
