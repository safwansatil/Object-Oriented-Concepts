package com.foodapp.service;

import com.foodapp.dao.CouponDAO;
import com.foodapp.exception.BusinessRuleException;
import com.foodapp.exception.DatabaseException;
import com.foodapp.model.Coupon;

import java.time.ZonedDateTime;

public class CouponService {
    private final CouponDAO couponDAO;

    public CouponService(CouponDAO couponDAO) {
        this.couponDAO = couponDAO;
    }

    public double applyDiscount(String code, double subtotal) {
        if (code == null || code.isBlank()) {
            return 0.0;
        }
        try {
            Coupon coupon = couponDAO.findActiveByCode(code.trim())
                    .orElseThrow(() -> new BusinessRuleException("Invalid coupon code."));

            if (coupon.getExpiresAt() != null && ZonedDateTime.parse(coupon.getExpiresAt()).isBefore(ZonedDateTime.now())) {
                throw new BusinessRuleException("Coupon has expired.");
            }
            if (subtotal < coupon.getMinimumOrderValue()) {
                throw new BusinessRuleException("Order does not meet coupon minimum value.");
            }
            if (coupon.getMaxUses() != null && coupon.getCurrentUses() != null && coupon.getCurrentUses() >= coupon.getMaxUses()) {
                throw new BusinessRuleException("Coupon usage limit reached.");
            }

            double discount = "PERCENTAGE".equalsIgnoreCase(coupon.getDiscountType())
                    ? subtotal * (coupon.getDiscountValue() / 100.0)
                    : coupon.getDiscountValue();
            discount = Math.max(0.0, Math.min(discount, subtotal));
            couponDAO.incrementUse(coupon.getId());
            return discount;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error applying coupon", e);
        }
    }
}
