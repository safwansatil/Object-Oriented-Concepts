package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Menu item metadata for SOAP responses.
 */
@XmlRootElement(name = "MenuItemInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuItemInfo {
    public String id;
    public String categoryId;
    public String restaurantId;
    public String name;
    public String description;
    public double basePrice;
    public String imageUrl;
    public boolean isAvailable;
    public Integer prepTime;

    public MenuItemInfo() {}
    
    public MenuItemInfo(String id, String categoryId, String restaurantId, String name, String description, 
                        double basePrice, String imageUrl, boolean isAvailable, Integer prepTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
        this.prepTime = prepTime;
    }
}
