package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Coupon validation result for SOAP.
 */
@XmlRootElement(name = "CouponValidationResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class CouponValidationResult {
    public boolean valid;
    public String message;
    public double discountAmount;
    public CouponInfo coupon;

    public CouponValidationResult() {}
    
    public CouponValidationResult(boolean valid, String message, double discountAmount, CouponInfo coupon) {
        this.valid = valid;
        this.message = message;
        this.discountAmount = discountAmount;
        this.coupon = coupon;
    }
}
