package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Order status data for SOAP.
 */
@XmlRootElement(name = "OrderStatusResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderStatusResponse {
    public String orderId;
    public String status;
    public String placedAt;
    public String confirmedAt;
    public String deliveredAt;

    public OrderStatusResponse() {}
    
    public OrderStatusResponse(String orderId, String status, String placedAt, String confirmedAt, String deliveredAt) {
        this.orderId = orderId;
        this.status = status;
        this.placedAt = placedAt;
        this.confirmedAt = confirmedAt;
        this.deliveredAt = deliveredAt;
    }
}
