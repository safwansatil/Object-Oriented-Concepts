package org.example.Order;

public class OrderItem {
    private String itemName;
    private double itemPrice;
    private int quantity;
    public OrderItem(String itemName, double itemPrice, int quantity){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }
    public double getItemTotal(){
        return (this.itemPrice*this.quantity);
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
