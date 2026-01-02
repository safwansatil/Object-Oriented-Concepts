package org.example.Customer;

import org.example.Order.Order;

public class Feedback {
    private double rating;
    private String feedback;
    private Order order;

    public double getRating(){
        return this.rating;
    }
    public String getFeedback(){
        return this.feedback;
    }
}
