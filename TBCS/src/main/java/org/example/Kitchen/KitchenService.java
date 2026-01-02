package org.example.Kitchen;

import org.example.Core.OrderStatus;
import org.example.Order.Order;

public class KitchenService {
    private HeadChef headChef = new HeadChef();
    public void sendToKitchen(Order order){
        order.setOrderStatus(OrderStatus.SENT_TO_KITCHEN);
        System.out.println("Order "+order.getOrderId()+" sent to kitchen.");
        startPrep(order);
    }
    void startPrep(Order order){
        order.setOrderStatus(OrderStatus.PREPARING);
        headChef.assignChefs(order);
        double time = headChef.estimatePrepTime(order);
        System.out.println("Estimated preparation time: " + time + " minutes");
    }
    public void makeOrderReady(Order order){
        order.setOrderStatus(OrderStatus.READY);
        System.out.println("Order " + order.getOrderId() + " is READY");
    }
}
