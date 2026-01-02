package org.example.Customer;

import org.example.Order.Order;
import org.example.Order.OrderItem;

import java.util.List;

public class CustomerService {
    Order placeOrder(Customer customer, List<OrderItem> items){
        return null;
    }
    public void submitFeedback(Order order, int rating, String comment) {
        Feedback feedback = new Feedback(rating, comment);

        System.out.println(
                "Feedback received for Order " + order.getOrderId()
        );
        System.out.println("Rating: " + feedback.getRating());
        System.out.println("Feedback: " + feedback.getFeedback());
    }

}
