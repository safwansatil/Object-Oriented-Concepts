package org.example.Order;

import org.example.Core.OrderStatus;
import org.example.Core.QueueManager;
import org.example.Customer.Customer;

import java.util.List;

public class OrderService {
    private QueueManager queueManager;
    public OrderService(QueueManager queueManager){
        this.queueManager = queueManager;
    }
    public Order createOrder(Customer customer, List<OrderItem> items){
        int orderId = queueManager.generateOrderNum();
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomer(customer);
        order.setItems(items);
        order.setOrderStatus(OrderStatus.PLACED);

        double total = 0;
        for(OrderItem item: items){
            total += item.getItemTotal();
        }
        order.setTotalAmount(total);
        System.out.println("Order Created Successfully!!");
        order.printOrderDetails();
        return order;
    }
    double calculateTotal(List<OrderItem> items){
        return 0;
    }
    void applyDiscount(Order order){

    }
}
