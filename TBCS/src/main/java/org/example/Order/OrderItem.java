package org.example.Order;

public class OrderItem {
    private String itemName;
    double itemPrice;
    int quantity;
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
