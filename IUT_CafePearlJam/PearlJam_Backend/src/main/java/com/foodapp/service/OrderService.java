package com.foodapp.service;

import com.foodapp.dao.*;
import com.foodapp.exception.BusinessRuleException;
import com.foodapp.exception.DatabaseException;
import com.foodapp.exception.ResourceNotFoundException;
import com.foodapp.model.*;
import com.foodapp.util.TimeUtil;
import com.foodapp.util.UUIDGenerator;
import java.math.BigDecimal;
import java.util.*;

/**
 * Service for order placement and management.
 */
public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final MenuItemDAO menuItemDAO;
    private final RestaurantDAO restaurantDAO;
    private final DeliveryZoneDAO deliveryZoneDAO;
    private final MenuItemAddonDAO addonDAO;
    private final CouponService couponService;

    public OrderService(OrderDAO orderDAO, OrderItemDAO orderItemDAO, MenuItemDAO menuItemDAO, 
                         RestaurantDAO restaurantDAO, DeliveryZoneDAO deliveryZoneDAO,
                         MenuItemAddonDAO addonDAO, CouponService couponService) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.menuItemDAO = menuItemDAO;
        this.restaurantDAO = restaurantDAO;
        this.deliveryZoneDAO = deliveryZoneDAO;
        this.addonDAO = addonDAO;
        this.couponService = couponService;
    }

    /**
     * DTO for order item request.
     */
    public static class OrderItemRequest {
        public String menuItemId;
        public int quantity;
        public List<String> addonIds;
    }

    /**
     * Places a new order.
     */
    public Order placeOrder(String customerId, String restaurantId, String deliveryAddress, String deliveryArea, 
                             List<OrderItemRequest> itemRequests, String couponCode, String paymentMethod, 
                             String specialInstructions) {
        try {
            // 1. Validate restaurant is active and open
            Restaurant restaurant = restaurantDAO.findById(restaurantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found."));
            if (!restaurant.isActive()) {
                throw new BusinessRuleException("Restaurant is currently inactive.");
            }
            if (!TimeUtil.isCurrentlyOpen(restaurant.getOpensAt(), restaurant.getClosesAt())) {
                throw new BusinessRuleException("Restaurant is currently closed.");
            }

            double subtotal = 0.0;
            List<OrderItem> orderItems = new ArrayList<>();
            String orderId = UUIDGenerator.generate();

            // 2. Validate menu items and 3. Check stock
            for (OrderItemRequest req : itemRequests) {
                MenuItem item = menuItemDAO.findById(req.menuItemId)
                        .orElseThrow(() -> new ResourceNotFoundException("Menu item " + req.menuItemId + " not found."));
                
                if (!item.getRestaurantId().equals(restaurantId)) {
                    throw new BusinessRuleException("Item " + item.getName() + " does not belong to this restaurant.");
                }
                if (!item.isAvailable()) {
                    throw new BusinessRuleException("Item " + item.getName() + " is currently unavailable.");
                }
                
                if (item.isTrackQuantity()) {
                    if (item.getQuantityInStock() < req.quantity) {
                        throw new BusinessRuleException("Insufficient stock for item " + item.getName());
                    }
                    // Decrement stock
                    menuItemDAO.updateStock(item.getId(), item.getQuantityInStock() - req.quantity);
                }

                double itemPriceTotal = item.getBasePrice();
                StringBuilder addonsList = new StringBuilder();
                
                // Handle Addons
                if (req.addonIds != null) {
                    List<MenuItemAddon> availableAddons = addonDAO.findByMenuItemId(item.getId());
                    for (String addonId : req.addonIds) {
                        MenuItemAddon addon = availableAddons.stream()
                                .filter(a -> a.getId().equals(addonId))
                                .findFirst()
                                .orElseThrow(() -> new ResourceNotFoundException("Addon " + addonId + " not found."));
                        
                        if (!addon.isAvailable()) {
                            throw new BusinessRuleException("Addon " + addon.getName() + " is currently unavailable.");
                        }
                        itemPriceTotal += addon.getExtraPrice();
                        if (addonsList.length() > 0) addonsList.append(", ");
                        addonsList.append(addon.getName());
                    }
                }

                double rowTotal = itemPriceTotal * req.quantity;
                subtotal += rowTotal;

                orderItems.add(new OrderItem(UUIDGenerator.generate(), orderId, item.getId(), item.getName(), 
                                           itemPriceTotal, req.quantity, addonsList.toString(), rowTotal));
            }

            // 5. Lookup delivery fee
            double deliveryFee = deliveryZoneDAO.findByRestaurantAndArea(restaurantId, deliveryArea)
                    .map(DeliveryZone::getDeliveryFee)
                    .orElse(0.0);

            double discountAmount = couponService.applyDiscount(couponCode, subtotal);
            double total = subtotal + deliveryFee - discountAmount;
            String now = TimeUtil.nowISO();
            PaymentStatus paymentStatus = "CASH_ON_DELIVERY".equalsIgnoreCase(paymentMethod) ? PaymentStatus.UNPAID : PaymentStatus.PAID;

            // 7. Save Order
            Order order = new Order(orderId, customerId, restaurantId, deliveryAddress, deliveryArea, 
                                    subtotal, deliveryFee, discountAmount, total, OrderStatus.PENDING,
                                    paymentStatus, paymentMethod, specialInstructions, now, null, null);
            orderDAO.save(order);

            // 8. Save Order Items
            orderItemDAO.saveAll(orderItems);

            return order;

        } catch (DatabaseException e) {
            throw new RuntimeException("Service error during order placement", e);
        }
    }

    public Order confirmOrder(String restaurantId, String orderId) {
        try {
            Order order = orderDAO.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
            if (!order.getRestaurantId().equals(restaurantId)) {
                throw new BusinessRuleException("Unauthorized access to this order.");
            }
            orderDAO.updateStatus(orderId, OrderStatus.CONFIRMED, TimeUtil.nowISO());
            order.setStatus(OrderStatus.CONFIRMED);
            return order;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error during order confirmation", e);
        }
    }

    public Order updateStatus(String actorId, String orderId, OrderStatus newStatus) {
        try {
            Order order = orderDAO.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
            
            // Logic for status transition permissions (simplified)
            orderDAO.updateStatus(orderId, newStatus, TimeUtil.nowISO());
            order.setStatus(newStatus);
            return order;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error updating status", e);
        }
    }


    public List<Order> getOrdersByCustomer(String customerId) {
        try {
            return orderDAO.findByCustomerId(customerId);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error getting customer orders", e);
        }
    }

    public List<Order> getOrdersByRestaurant(String restaurantId) {
        try {
            return orderDAO.findByRestaurantId(restaurantId);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error getting restaurant orders", e);
        }
    }

    public Order getOrderDetails(String orderId) {
        try {
            Order order = orderDAO.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
            order.setItems(orderItemDAO.findByOrderId(orderId));
            return order;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error getting order details", e);
        }
    }

    public void cancelOrder(String actorId, String orderId) {
        try {
            Order order = orderDAO.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
            if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
                throw new BusinessRuleException("Only PENDING or CONFIRMED orders can be cancelled.");
            }
            orderDAO.updateStatus(orderId, OrderStatus.CANCELLED, null);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error cancelling order", e);
        }
    }
}
