package com.example;

import java.util.List;

public class Items {
    private List<String> itemList = new java.util.ArrayList<>();

    public Items(String item){
        this.itemList.add(item);
    }
    public Items(List<String> itemList) {
        this.itemList = itemList;
    }
    public Items() {
    }
     

    public double calculateTotal() {
        return itemList.size() * 10.0;
    }
    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }
    public void addItem(String item){
        this.itemList.add(item);
    }
    

}
