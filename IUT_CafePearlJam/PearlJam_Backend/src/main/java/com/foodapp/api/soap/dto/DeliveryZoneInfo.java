package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Delivery zone mapping for SOAP responses.
 */
@XmlRootElement(name = "DeliveryZoneInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryZoneInfo {
    public String id;
    public String restaurantId;
    public String areaName;
    public double deliveryFee;
    public int estimatedMinutes;

    public DeliveryZoneInfo() {}
    
    public DeliveryZoneInfo(String id, String restaurantId, String areaName, double deliveryFee, int estimatedMinutes) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.areaName = areaName;
        this.deliveryFee = deliveryFee;
        this.estimatedMinutes = estimatedMinutes;
    }
}
