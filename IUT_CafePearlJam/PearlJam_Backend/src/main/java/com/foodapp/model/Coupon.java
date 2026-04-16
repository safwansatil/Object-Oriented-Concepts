package com.foodapp.model;

public class Coupon {
    private String id;
    private String code;
    private String discountType;
    private double discountValue;
    private double minimumOrderValue;
    private Integer maxUses;
    private Integer currentUses;
    private String expiresAt;
    private boolean active;

    public Coupon(String id, String code, String discountType, double discountValue, double minimumOrderValue,
                  Integer maxUses, Integer currentUses, String expiresAt, boolean active) {
        this.id = id;
        this.code = code;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minimumOrderValue = minimumOrderValue;
        this.maxUses = maxUses;
        this.currentUses = currentUses;
        this.expiresAt = expiresAt;
        this.active = active;
    }

    public String getId() { return id; }
    public String getCode() { return code; }
    public String getDiscountType() { return discountType; }
    public double getDiscountValue() { return discountValue; }
    public double getMinimumOrderValue() { return minimumOrderValue; }
    public Integer getMaxUses() { return maxUses; }
    public Integer getCurrentUses() { return currentUses; }
    public String getExpiresAt() { return expiresAt; }
    public boolean isActive() { return active; }
}
