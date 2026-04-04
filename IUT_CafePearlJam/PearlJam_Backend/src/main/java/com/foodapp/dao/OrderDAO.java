package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.Order;
import com.foodapp.model.OrderStatus;
import com.foodapp.model.PaymentStatus;
import com.foodapp.util.DatabaseConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for Order entity.
 */
public class OrderDAO {
    private static final String INSERT_SQL = "INSERT INTO orders (id, customer_id, restaurant_id, delivery_address, delivery_area, subtotal, delivery_fee, discount_amount, total, status, payment_status, payment_method, special_instructions, placed_at, confirmed_at, delivered_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BASE = "SELECT o.*, c.name as customer_name, r.name as restaurant_name " +
            "FROM orders o " +
            "LEFT JOIN users c ON o.customer_id = c.id " +
            "LEFT JOIN restaurants r ON o.restaurant_id = r.id ";
    
    private static final String SELECT_BY_ID_SQL = SELECT_BASE + "WHERE o.id = ?";
    private static final String SELECT_BY_CUSTOMER_ID_SQL = SELECT_BASE + "WHERE o.customer_id = ? ORDER BY o.placed_at DESC";
    private static final String SELECT_BY_RESTAURANT_ID_SQL = SELECT_BASE + "WHERE o.restaurant_id = ? ORDER BY o.placed_at DESC";
    private static final String UPDATE_STATUS_SQL = "UPDATE orders SET status = ?, confirmed_at = ?, delivered_at = ? WHERE id = ?";
    private static final String UPDATE_PAYMENT_STATUS_SQL = "UPDATE orders SET payment_status = ? WHERE id = ?";
    private static final String SELECT_BY_STATUS_AND_RESTAURANT_SQL = SELECT_BASE + "WHERE o.restaurant_id = ? AND o.status = ?";

    private final DatabaseConnectionPool pool;

    public OrderDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void save(Order order) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                stmt.setString(1, order.getId());
                stmt.setString(2, order.getCustomerId());
                stmt.setString(3, order.getRestaurantId());
                stmt.setString(4, order.getDeliveryAddress());
                stmt.setString(5, order.getDeliveryArea());
                stmt.setDouble(6, order.getSubtotal());
                stmt.setDouble(7, order.getDeliveryFee());
                stmt.setDouble(8, order.getDiscountAmount());
                stmt.setDouble(9, order.getTotal());
                stmt.setString(10, order.getStatus().name());
                stmt.setString(11, order.getPaymentStatus().name());
                stmt.setString(12, order.getPaymentMethod());
                stmt.setString(13, order.getSpecialInstructions());
                stmt.setString(14, order.getPlacedAt());
                stmt.setString(15, order.getConfirmedAt());
                stmt.setString(16, order.getDeliveredAt());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving order", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public Optional<Order> findById(String id) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSetToOrder(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding order by id", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    public List<Order> findByCustomerId(String customerId) throws DatabaseException {
        List<Order> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CUSTOMER_ID_SQL)) {
                stmt.setString(1, customerId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToOrder(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding orders by customer id", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public List<Order> findByRestaurantId(String restaurantId) throws DatabaseException {
        List<Order> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_RESTAURANT_ID_SQL)) {
                stmt.setString(1, restaurantId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToOrder(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding orders by restaurant id", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }


    public void updateStatus(String orderId, OrderStatus status, String time) throws DatabaseException {
        String column = (status == OrderStatus.CONFIRMED) ? "confirmed_at" : 
                        (status == OrderStatus.DELIVERED) ? "delivered_at" : null;
        
        String sql = UPDATE_STATUS_SQL;
        if (column == null) {
            sql = "UPDATE orders SET status = ? WHERE id = ?";
        }

        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status.name());
                if (column != null) {
                    stmt.setString(2, time);
                    stmt.setString(3, orderId);
                } else {
                    stmt.setString(2, orderId);
                }
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating order status", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void updatePaymentStatus(String orderId, PaymentStatus status) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_PAYMENT_STATUS_SQL)) {
                stmt.setString(1, status.name());
                stmt.setString(2, orderId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating payment status", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }


    public List<Order> findByStatusAndRestaurant(String restaurantId, OrderStatus status) throws DatabaseException {
        List<Order> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_STATUS_AND_RESTAURANT_SQL)) {
                stmt.setString(1, restaurantId);
                stmt.setString(2, status.name());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToOrder(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding orders by status and restaurant", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public List<Order> findAvailableOrders() throws DatabaseException {
        List<Order> list = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE o.status = 'CONFIRMED' ORDER BY o.placed_at DESC";
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding available orders", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order(
                rs.getString("id"),
                rs.getString("customer_id"),
                rs.getString("restaurant_id"),
                rs.getString("delivery_address"),
                rs.getString("delivery_area"),
                rs.getDouble("subtotal"),
                rs.getDouble("delivery_fee"),
                rs.getDouble("discount_amount"),
                rs.getDouble("total"),
                OrderStatus.valueOf(rs.getString("status")),
                PaymentStatus.valueOf(rs.getString("payment_status")),
                rs.getString("payment_method"),
                rs.getString("special_instructions"),
                rs.getString("placed_at"),
                rs.getString("confirmed_at"),
                rs.getString("delivered_at")
        );
        
        // Populate joined fields
        try {
            order.setCustomerName(rs.getString("customer_name"));
            order.setRestaurantName(rs.getString("restaurant_name"));
        } catch (SQLException e) {
            // Fields might not exist in some simpler queries if any
        }
        
        return order;
    }
}
