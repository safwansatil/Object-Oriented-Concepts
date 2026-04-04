package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Menu category grouping with its items for SOAP.
 */
@XmlRootElement(name = "CategoryWithItems")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryWithItems {
    public String categoryId;
    public String categoryName;
    @XmlElement(name = "items")
    public List<MenuItemInfo> items;

    public CategoryWithItems() {}
    
    public CategoryWithItems(String categoryId, String categoryName, List<MenuItemInfo> items) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.items = items;
    }
}
