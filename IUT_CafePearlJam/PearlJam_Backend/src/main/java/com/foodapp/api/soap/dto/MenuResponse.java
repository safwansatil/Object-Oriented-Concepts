package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Menu response for SOAP.
 */
@XmlRootElement(name = "MenuResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuResponse {
    @XmlElement(name = "categories")
    public List<CategoryWithItems> categories;

    public MenuResponse() {}
    
    public MenuResponse(List<CategoryWithItems> categories) {
        this.categories = categories;
    }
}
