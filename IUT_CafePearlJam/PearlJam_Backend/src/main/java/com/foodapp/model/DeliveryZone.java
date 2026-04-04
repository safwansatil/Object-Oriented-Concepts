package com.foodapp.model;

/**
 * DeliveryZone domain entity.
 */
public class DeliveryZone {
    private String id;
    private String restaurantId;
    private String areaName;
    private double deliveryFee;
    private int estimatedMinutes;

    public DeliveryZone() {}

    public DeliveryZone(String id, String restaurantId, String areaName, double deliveryFee, int estimatedMinutes) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.areaName = areaName;
        this.deliveryFee = deliveryFee;
        this.estimatedMinutes = estimatedMinutes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRestaurantId() { return restaurantId; }
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }

    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }

    public int getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(int estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    @Override
    public String toString() {
        return "DeliveryZone{" +
                "areaName='" + areaName + '\'' +
                ", deliveryFee=" + deliveryFee +
                '}';
    }
}
