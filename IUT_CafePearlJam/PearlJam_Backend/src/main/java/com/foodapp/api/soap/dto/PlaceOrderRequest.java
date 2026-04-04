package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Order placement request for SOAP.
 */
@XmlRootElement(name = "PlaceOrderRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaceOrderRequest {
    public String customerId;
    public String restaurantId;
    public String deliveryAddress;
    public String deliveryArea;
    @XmlElement(name = "items")
    public List<OrderItemSOAPRequest> items;
    public String paymentMethod;
    public String specialInstructions;

    public PlaceOrderRequest() {}
}
