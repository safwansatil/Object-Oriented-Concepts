package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Addon info for SOAP responses.
 */
@XmlRootElement(name = "AddonInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddonInfo {
    public String id;
    public String name;
    public double extraPrice;
    public boolean isAvailable;

    public AddonInfo() {}
    
    public AddonInfo(String id, String name, double extraPrice, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.extraPrice = extraPrice;
        this.isAvailable = isAvailable;
    }
}
