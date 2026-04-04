package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Coupon data for SOAP responses.
 */
@XmlRootElement(name = "CouponInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CouponInfo {
    public String id;
    public String code;
    public String description;
    public String discountType;
    public double discountValue;
    public double minOrderValue;
    public String expiresAt;

    public CouponInfo() {}
    
    public CouponInfo(String id, String code, String description, String discountType, double discountValue, double minOrderValue, String expiresAt) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderValue = minOrderValue;
        this.expiresAt = expiresAt;
    }
}
