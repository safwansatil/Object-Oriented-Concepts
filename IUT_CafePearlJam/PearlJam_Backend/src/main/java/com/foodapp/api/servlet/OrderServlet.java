package com.foodapp.api.servlet;

import com.foodapp.config.AppConstants;
import com.foodapp.exception.AppException;
import com.foodapp.model.Order;
import com.foodapp.model.OrderStatus;
import com.foodapp.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet for order operations.
 */
@WebServlet("/api/orders/*")
public class OrderServlet extends BaseServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = (OrderService) getServletContext().getAttribute(AppConstants.ATTR_ORDER_SERVICE);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = getPathParam(req, 0);
        String sub = getPathParam(req, 1);

        if (id == null) {
            writeError(resp, 400, "Order ID missing");
        } else if ("customer".equals(id)) {
            // /api/orders/customer/{customerId}
            String customerId = getPathParam(req, 1);
            List<Order> list = orderService.getOrdersByCustomer(customerId);
            writeJson(resp, 200, list);
        } else if ("status".equals(sub)) {
            Order order = orderService.getOrderDetails(id);
            Map<String, Object> statusPayload = new java.util.HashMap<>();
            statusPayload.put("orderId", order.getId());
            statusPayload.put("status", order.getStatus());
            statusPayload.put("updatedAt", order.getDeliveredAt() != null ? order.getDeliveredAt() : order.getConfirmedAt());
            writeJson(resp, 200, statusPayload);
        } else {
            // /api/orders/{orderId}
            Order order = orderService.getOrderDetails(id);
            writeJson(resp, 200, order);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> body = jsonMapper.fromJson(readBody(req), Map.class);
        try {
            // Extract items
            List<Map<String, Object>> itemsRaw = (List<Map<String, Object>>) body.get("items");
            List<OrderService.OrderItemRequest> items = itemsRaw.stream().map(m -> {
                OrderService.OrderItemRequest r = new OrderService.OrderItemRequest();
                r.menuItemId = (String) m.get("menuItemId");
                r.quantity = ((Number) m.get("quantity")).intValue();
                r.addonIds = (List<String>) m.get("addonIds");
                return r;
            }).toList();

            Order order = orderService.placeOrder(
                (String) body.get("customerId"),
                (String) body.get("restaurantId"),
                (String) body.get("deliveryAddress"),
                (String) body.get("deliveryArea"),
                items,
                (String) body.get("couponCode"),
                (String) body.get("paymentMethod"),
                (String) body.get("specialInstructions")
            );
            writeJson(resp, 201, order);
        } catch (AppException e) {
            writeError(resp, e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            writeError(resp, 500, e.getMessage());
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = getPathParam(req, 0);
        String sub = getPathParam(req, 1);
        
        if (id != null) {
            Map<String, String> body = jsonMapper.fromJson(readBody(req), Map.class);
            try {
                if ("status".equals(sub)) {
                    Order order = orderService.updateStatus(body.getOrDefault("actorId", "system"), id, OrderStatus.valueOf(body.get("status").toUpperCase()));
                    writeJson(resp, 200, order);
                } else if ("assign-rider".equals(sub)) {
                    writeJson(resp, 200, Map.of("orderId", id, "riderId", body.get("riderId"), "assigned", true));
                } else if ("payment".equals(sub)) {
                    writeJson(resp, 200, Map.of("orderId", id, "paymentStatus", "PAID", "message", "Payment processed (simulated)"));
                } else {
                    writeError(resp, 400, "Unknown sub-resource");
                }
            } catch (AppException e) {
                writeError(resp, e.getStatusCode(), e.getMessage());
            } catch (Exception e) {
                writeError(resp, 500, e.getMessage());
            }
        } else {
            writeError(resp, 400, "Order ID missing");
        }
    }
}
