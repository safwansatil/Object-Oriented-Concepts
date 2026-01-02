package org.example.Delivery;

public class Driver {

    private boolean isAvailable;
    private String licenseNum;
    public Driver(String licenseNum){
        this.licenseNum = licenseNum;
        this.isAvailable = true;
    }
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
