package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Order confirmation for SOAP.
 */
@XmlRootElement(name = "OrderConfirmation")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderConfirmation {
    public String orderId;
    public String status;
    public double total;
    public String message;

    public OrderConfirmation() {}
    
    public OrderConfirmation(String orderId, String status, double total, String message) {
        this.orderId = orderId;
        this.status = status;
        this.total = total;
        this.message = message;
    }
}
