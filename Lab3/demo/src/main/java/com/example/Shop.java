package com.example;

import java.util.List;

public class Shop {
    String name;
    List<Items> items = new java.util.ArrayList<>();
    List<Customers> customers = new java.util.ArrayList<>();


    void setName(String name) {
        this.name = name;
    }
    String getName() {
        return this.name;
    }
    List<Items> getItems() {
        return this.items;  
    }
    void addItem(Items item){
        this.items.add(item);
    }
    void addCustomer(Customers customer){
        this.customers.add(customer);
    }
    void displayItems(){
        for(Items item: items){
            System.out.println("Item List: " + item.getItemList());
        }
    }
}
