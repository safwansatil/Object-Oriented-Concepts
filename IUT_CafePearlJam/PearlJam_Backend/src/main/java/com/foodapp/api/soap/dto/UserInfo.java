package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * User data for SOAP responses.
 */
@XmlRootElement(name = "UserInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserInfo {
    public String id;
    public String name;
    public String email;
    public String phone;
    public String role;
    public boolean isActive;
    public String createdAt;

    public UserInfo() {}
    
    public UserInfo(String id, String name, String email, String phone, String role, boolean isActive, String createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
}
