package org.example.Kitchen;

import org.example.Core.OrderStatus;
import org.example.Order.Order;

public class KitchenService {
    HeadChef headChef;
    public void sendToKitchen(Order order){
        order.setOrderStatus(OrderStatus.SENT_TO_KITCHEN);
        System.out.println("Order "+order.getOrderId()+" sent to kitchen.");
    }
    void startPrep(Order order){

    }
    public void makeOrderReady(Order order){
        order.setOrderStatus(OrderStatus.READY);
        System.out.println("Order " + order.getOrderId() + " is READY");
    }
}
