package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.MenuCategory;
import com.foodapp.util.DatabaseConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for MenuCategory entity.
 */
public class MenuCategoryDAO {
    private static final String INSERT_SQL = "INSERT INTO menu_categories (id, restaurant_id, name, display_order) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_RESTAURANT_ID_SQL = "SELECT * FROM menu_categories WHERE restaurant_id = ? ORDER BY display_order";
    private static final String UPDATE_SQL = "UPDATE menu_categories SET name = ?, display_order = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM menu_categories WHERE id = ?";

    private final DatabaseConnectionPool pool;

    public MenuCategoryDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void save(MenuCategory category) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                stmt.setString(1, category.getId());
                stmt.setString(2, category.getRestaurantId());
                stmt.setString(3, category.getName());
                stmt.setInt(4, category.getDisplayOrder());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving menu category", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public List<MenuCategory> findByRestaurantId(String restaurantId) throws DatabaseException {
        List<MenuCategory> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_RESTAURANT_ID_SQL)) {
                stmt.setString(1, restaurantId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToCategory(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding categories by restaurant id", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public void update(MenuCategory category) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, category.getName());
                stmt.setInt(2, category.getDisplayOrder());
                stmt.setString(3, category.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating menu category", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void delete(String id) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
                stmt.setString(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting menu category", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    private MenuCategory mapResultSetToCategory(ResultSet rs) throws SQLException {
        return new MenuCategory(
                rs.getString("id"),
                rs.getString("restaurant_id"),
                rs.getString("name"),
                rs.getInt("display_order")
        );
    }
}
