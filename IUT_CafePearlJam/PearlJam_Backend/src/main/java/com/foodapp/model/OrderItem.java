package com.foodapp.model;

/**
 * OrderItem domain entity.
 */
public class OrderItem {
    private String id;
    private String orderId;
    private String menuItemId;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private String selectedAddons;
    private double itemTotal;

    public OrderItem() {}

    public OrderItem(String id, String orderId, String menuItemId, String itemName, double itemPrice, 
                     int quantity, String selectedAddons, double itemTotal) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.selectedAddons = selectedAddons;
        this.itemTotal = itemTotal;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getMenuItemId() { return menuItemId; }
    public void setMenuItemId(String menuItemId) { this.menuItemId = menuItemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public double getItemPrice() { return itemPrice; }
    public void setItemPrice(double itemPrice) { this.itemPrice = itemPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSelectedAddons() { return selectedAddons; }
    public void setSelectedAddons(String selectedAddons) { this.selectedAddons = selectedAddons; }

    public double getItemTotal() { return itemTotal; }
    public void setItemTotal(double itemTotal) { this.itemTotal = itemTotal; }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", itemTotal=" + itemTotal +
                '}';
    }
}
