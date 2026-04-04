package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Result of a SOAP operation.
 */
@XmlRootElement(name = "OperationResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationResult {
    public boolean success;
    public String message;

    public OperationResult() {}
    
    public OperationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
