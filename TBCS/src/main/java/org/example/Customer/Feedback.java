package org.example.Customer;

import org.example.Order.Order;

public class Feedback {
    private double rating;
    private String feedback;

    public Feedback(double rating, String feedback){
        this.rating = rating;
        this.feedback = feedback;

    }
    public double getRating(){
        return this.rating;
    }
    public String getFeedback(){
        return this.feedback;
    }
}
