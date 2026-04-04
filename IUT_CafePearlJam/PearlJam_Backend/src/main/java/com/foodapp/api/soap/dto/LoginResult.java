package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Login result for SOAP.
 */
@XmlRootElement(name = "LoginResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginResult {
    public boolean success;
    public String message;
    public UserInfo userInfo;

    public LoginResult() {}
    
    public LoginResult(boolean success, String message, UserInfo userInfo) {
        this.success = success;
        this.message = message;
        this.userInfo = userInfo;
    }
}
