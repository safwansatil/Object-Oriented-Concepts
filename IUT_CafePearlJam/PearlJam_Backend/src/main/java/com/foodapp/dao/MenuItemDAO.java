package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.MenuItem;
import com.foodapp.util.DatabaseConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for MenuItem entity.
 */
public class MenuItemDAO {
    private static final String INSERT_SQL = "INSERT INTO menu_items (id, category_id, restaurant_id, name, description, base_price, image_url, is_available, track_quantity, quantity_in_stock, preparation_time_minutes, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM menu_items WHERE id = ?";
    private static final String SELECT_BY_CATEGORY_ID_SQL = "SELECT * FROM menu_items WHERE category_id = ?";
    private static final String SELECT_BY_RESTAURANT_ID_SQL = "SELECT * FROM menu_items WHERE restaurant_id = ?";
    private static final String UPDATE_AVAILABILITY_SQL = "UPDATE menu_items SET is_available = ? WHERE id = ?";
    private static final String UPDATE_STOCK_SQL = "UPDATE menu_items SET quantity_in_stock = ? WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE menu_items SET category_id = ?, name = ?, description = ?, base_price = ?, image_url = ?, track_quantity = ?, preparation_time_minutes = ? WHERE id = ?";

    private final DatabaseConnectionPool pool;

    public MenuItemDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void save(MenuItem item) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                stmt.setString(1, item.getId());
                stmt.setString(2, item.getCategoryId());
                stmt.setString(3, item.getRestaurantId());
                stmt.setString(4, item.getName());
                stmt.setString(5, item.getDescription());
                stmt.setDouble(6, item.getBasePrice());
                stmt.setString(7, item.getImageUrl());
                stmt.setInt(8, item.isAvailable() ? 1 : 0);
                stmt.setInt(9, item.isTrackQuantity() ? 1 : 0);
                stmt.setObject(10, item.getQuantityInStock());
                stmt.setObject(11, item.getPreparationTimeMinutes());
                stmt.setString(12, item.getCreatedAt());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving menu item", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public Optional<MenuItem> findById(String id) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSetToMenuItem(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding menu item by id", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    public List<MenuItem> findByCategoryId(String categoryId) throws DatabaseException {
        List<MenuItem> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CATEGORY_ID_SQL)) {
                stmt.setString(1, categoryId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToMenuItem(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding menu items by category", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public List<MenuItem> findByRestaurantId(String restaurantId) throws DatabaseException {
        List<MenuItem> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_RESTAURANT_ID_SQL)) {
                stmt.setString(1, restaurantId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToMenuItem(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding menu items by restaurant", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public void updateAvailability(String itemId, boolean available) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_AVAILABILITY_SQL)) {
                stmt.setInt(1, available ? 1 : 0);
                stmt.setString(2, itemId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating item availability", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void updateStock(String itemId, int quantity) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_STOCK_SQL)) {
                stmt.setInt(1, quantity);
                stmt.setString(2, itemId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating item stock", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void update(MenuItem item) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, item.getCategoryId());
                stmt.setString(2, item.getName());
                stmt.setString(3, item.getDescription());
                stmt.setDouble(4, item.getBasePrice());
                stmt.setString(5, item.getImageUrl());
                stmt.setInt(6, item.isTrackQuantity() ? 1 : 0);
                stmt.setObject(7, item.getPreparationTimeMinutes());
                stmt.setString(8, item.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating menu item", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    private MenuItem mapResultSetToMenuItem(ResultSet rs) throws SQLException {
        return new MenuItem(
                rs.getString("id"),
                rs.getString("category_id"),
                rs.getString("restaurant_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("base_price"),
                rs.getString("image_url"),
                rs.getInt("is_available") == 1,
                rs.getInt("track_quantity") == 1,
                (Integer) rs.getObject("quantity_in_stock"),
                (Integer) rs.getObject("preparation_time_minutes"),
                rs.getString("created_at")
        );
    }
}
