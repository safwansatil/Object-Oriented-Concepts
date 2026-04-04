package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Summary of an order for list responses in SOAP.
 */
@XmlRootElement(name = "OrderSummary")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSummary {
    public String orderId;
    public String restaurantId;
    public double total;
    public String status;
    public String placedAt;

    public OrderSummary() {}
    
    public OrderSummary(String orderId, String restaurantId, double total, String status, String placedAt) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.total = total;
        this.status = status;
        this.placedAt = placedAt;
    }
}
