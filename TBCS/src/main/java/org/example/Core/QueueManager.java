package org.example.Core;

public class QueueManager {
    private int currentOrderNum = 0;
    public int generateOrderNum(){
        this.currentOrderNum++;
        return this.currentOrderNum;
    }
    public int getCurrentOrderNum(){
        return  this.currentOrderNum;
    }
}
