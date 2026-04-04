package com.foodapp.api.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Order item for SOAP requests.
 */
@XmlRootElement(name = "OrderItemSOAPRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderItemSOAPRequest {
    public String menuItemId;
    public int quantity;
    @XmlElement(name = "addonIds")
    public List<String> addonIds;

    public OrderItemSOAPRequest() {}
}
