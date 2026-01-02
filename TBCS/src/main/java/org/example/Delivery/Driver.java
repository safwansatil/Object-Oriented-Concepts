package org.example.Delivery;

public class Driver {
    String name;
    private boolean isAvailable;
    Vehicle vehicle;
    private String licenseNum;
    public String getLicenseNum(){
        return  this.licenseNum;
    }
    public boolean getIsAvailable(){
        return  this.isAvailable;
    }
    public void setIsAvailable(boolean status){
        this.isAvailable = status;
    }

}
