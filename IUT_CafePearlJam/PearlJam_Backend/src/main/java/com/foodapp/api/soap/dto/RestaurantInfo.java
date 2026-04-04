package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Restaurant metadata for SOAP responses.
 */
@XmlRootElement(name = "RestaurantInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestaurantInfo {
    public String id;
    public String name;
    public String description;
    public String address;
    public String area;
    public String cuisineType;
    public boolean isActive;
    public String opensAt;
    public String closesAt;

    public RestaurantInfo() {}
    
    public RestaurantInfo(String id, String name, String description, String address, String area, 
                          String cuisineType, boolean isActive, String opensAt, String closesAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.area = area;
        this.cuisineType = cuisineType;
        this.isActive = isActive;
        this.opensAt = opensAt;
        this.closesAt = closesAt;
    }
}
