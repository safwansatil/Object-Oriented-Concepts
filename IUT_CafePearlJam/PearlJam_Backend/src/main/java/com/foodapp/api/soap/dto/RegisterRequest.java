package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Registration request for SOAP.
 */
@XmlRootElement(name = "RegisterRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisterRequest {
    public String name;
    public String email;
    public String password;
    public String phone;
    public String role;

    public RegisterRequest() {}
}
