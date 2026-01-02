package org.example.Core;

public class QueueManager {
    private int currentOrderNum = 0;
    public int generateOrderNum(){
        this.currentOrderNum++;
        return this.currentOrderNum;
    }
    int getCurrentServingOrderNum(){
        return 0;
    }
    void updateCurrentServingOrder(int orderNum){

    }
    public int getCurrentOrderNum(){
        return  this.currentOrderNum;
    }
}
