package com.foodapp.api.soap;

import com.foodapp.api.soap.dto.OrderConfirmation;
import com.foodapp.api.soap.dto.OrderStatusResponse;
import com.foodapp.api.soap.dto.OrderSummary;
import com.foodapp.api.soap.dto.PlaceOrderRequest;
import com.foodapp.api.soap.dto.OperationResult;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

/**
 * SOAP Order Service interface.
 */
@WebService(name = "OrderService", targetNamespace = "http://foodapp.com/ws")
public interface OrderService {
    
    @WebMethod
    OrderConfirmation placeOrder(@WebParam(name = "request") PlaceOrderRequest request);
    
    @WebMethod
    OrderStatusResponse getOrderStatus(@WebParam(name = "orderId") String orderId);
    
    @WebMethod
    List<OrderSummary> getOrdersByCustomer(@WebParam(name = "customerId") String customerId);
    
    @WebMethod
    OperationResult cancelOrder(@WebParam(name = "orderId") String orderId, @WebParam(name = "customerId") String customerId);
}
