package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.OrderItem;
import com.foodapp.util.DatabaseConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for OrderItem entity.
 */
public class OrderItemDAO {
    private static final String INSERT_SQL = "INSERT INTO order_items (id, order_id, menu_item_id, item_name, item_price, quantity, selected_addons, item_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ORDER_ID_SQL = "SELECT * FROM order_items WHERE order_id = ?";

    private final DatabaseConnectionPool pool;

    public OrderItemDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void saveAll(List<OrderItem> items) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                for (OrderItem item : items) {
                    stmt.setString(1, item.getId());
                    stmt.setString(2, item.getOrderId());
                    stmt.setString(3, item.getMenuItemId());
                    stmt.setString(4, item.getItemName());
                    stmt.setDouble(5, item.getItemPrice());
                    stmt.setInt(6, item.getQuantity());
                    stmt.setString(7, item.getSelectedAddons());
                    stmt.setDouble(8, item.getItemTotal());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            throw new DatabaseException("Error saving order items", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public List<OrderItem> findByOrderId(String orderId) throws DatabaseException {
        List<OrderItem> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ORDER_ID_SQL)) {
                stmt.setString(1, orderId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToOrderItem(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding items for order", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        return new OrderItem(
                rs.getString("id"),
                rs.getString("order_id"),
                rs.getString("menu_item_id"),
                rs.getString("item_name"),
                rs.getDouble("item_price"),
                rs.getInt("quantity"),
                rs.getString("selected_addons"),
                rs.getDouble("item_total")
        );
    }
}
