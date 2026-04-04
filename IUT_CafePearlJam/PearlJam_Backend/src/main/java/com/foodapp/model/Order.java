package com.foodapp.model;

import java.util.List;

/**
 * Order domain entity.
 */
public class Order {
    private String id;
    private String customerId;
    private String restaurantId;
    private String deliveryAddress;
    private String deliveryArea;
    private double subtotal;
    private double deliveryFee;
    private double discountAmount;
    private double total;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private String paymentMethod;
    private String specialInstructions;
    private String placedAt;
    private String confirmedAt;
    private String deliveredAt;

    // View-only fields (not in table directly)
    private String customerName;
    private String restaurantName;
    
    private List<OrderItem> items;

    public Order() {}

    public Order(String id, String customerId, String restaurantId, String deliveryAddress, 
                 String deliveryArea, double subtotal, double deliveryFee, double discountAmount, double total, 
                 OrderStatus status, PaymentStatus paymentStatus, String paymentMethod, String specialInstructions, 
                 String placedAt, String confirmedAt, String deliveredAt) {
        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryArea = deliveryArea;
        this.subtotal = subtotal;
        this.deliveryFee = deliveryFee;
        this.discountAmount = discountAmount;
        this.total = total;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.specialInstructions = specialInstructions;
        this.placedAt = placedAt;
        this.confirmedAt = confirmedAt;
        this.deliveredAt = deliveredAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getRestaurantId() { return restaurantId; }
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }


    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getDeliveryArea() { return deliveryArea; }
    public void setDeliveryArea(String deliveryArea) { this.deliveryArea = deliveryArea; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

    public String getPlacedAt() { return placedAt; }
    public void setPlacedAt(String placedAt) { this.placedAt = placedAt; }

    public String getConfirmedAt() { return confirmedAt; }
    public void setConfirmedAt(String confirmedAt) { this.confirmedAt = confirmedAt; }

    public String getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(String deliveredAt) { this.deliveredAt = deliveredAt; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }


    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", total=" + total +
                '}';
    }
}
