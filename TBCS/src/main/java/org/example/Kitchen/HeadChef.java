package org.example.Kitchen;

import org.example.Core.OrderStatus;
import org.example.Core.OrderType;
import org.example.Order.Order;

public class HeadChef extends  Staff{
    double estimatePrepTime(Order order){
        if(order.getOrderType() == OrderType.PRIORITY){
            return 17.17;
        }
        return 55.55;
    }
    void assignChefs(Order order){
        System.out.println(
                "HeadChef assigned chefs to Order " + order.getOrderId()
        );
    }
    void updateOrderStatus(Order order, OrderStatus status){

    }
}
