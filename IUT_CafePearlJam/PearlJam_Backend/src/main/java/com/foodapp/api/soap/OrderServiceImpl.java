package com.foodapp.api.soap;

import com.foodapp.api.soap.dto.*;
import com.foodapp.exception.AppException;
import com.foodapp.model.Order;
import jakarta.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Order SOAP Service.
 */
@WebService(endpointInterface = "com.foodapp.api.soap.OrderService", targetNamespace = "http://foodapp.com/ws", serviceName = "OrderService")
public class OrderServiceImpl implements OrderService {
    
    private final com.foodapp.service.OrderService orderService;

    public OrderServiceImpl(com.foodapp.service.OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderConfirmation placeOrder(PlaceOrderRequest request) {
        try {
            List<com.foodapp.service.OrderService.OrderItemRequest> items = request.items.stream()
                    .map(i -> {
                        com.foodapp.service.OrderService.OrderItemRequest req = new com.foodapp.service.OrderService.OrderItemRequest();
                        req.menuItemId = i.menuItemId;
                        req.quantity = i.quantity;
                        req.addonIds = i.addonIds;
                        return req;
                    }).collect(Collectors.toList());

            Order order = orderService.placeOrder(
                    request.customerId,
                    request.restaurantId,
                    request.deliveryAddress,
                    request.deliveryArea,
                    items,
                    null,
                    request.paymentMethod,
                    request.specialInstructions
            );

            return new OrderConfirmation(order.getId(), order.getStatus().name(), order.getTotal(), "Order placed successfully.");
        } catch (AppException e) {
            return new OrderConfirmation(null, "FAILED", 0.0, e.getMessage());
        } catch (Exception e) {
            return new OrderConfirmation(null, "ERROR", 0.0, "An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public OrderStatusResponse getOrderStatus(String orderId) {
        try {
            Order order = orderService.getOrderDetails(orderId);
            return new OrderStatusResponse(order.getId(), order.getStatus().name(), order.getPlacedAt(), order.getConfirmedAt(), order.getDeliveredAt());
        } catch (Exception e) {
            return new OrderStatusResponse(orderId, "NOT_FOUND", null, null, null);
        }
    }

    @Override
    public List<OrderSummary> getOrdersByCustomer(String customerId) {
        return orderService.getOrdersByCustomer(customerId).stream()
                .map(o -> new OrderSummary(o.getId(), o.getRestaurantId(), o.getTotal(), o.getStatus().name(), o.getPlacedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public OperationResult cancelOrder(String orderId, String customerId) {
        try {
            orderService.cancelOrder(customerId, orderId);
            return new OperationResult(true, "Order cancelled successfully.");
        } catch (AppException e) {
            return new OperationResult(false, e.getMessage());
        } catch (Exception e) {
            return new OperationResult(false, "An error occurred: " + e.getMessage());
        }
    }
}
