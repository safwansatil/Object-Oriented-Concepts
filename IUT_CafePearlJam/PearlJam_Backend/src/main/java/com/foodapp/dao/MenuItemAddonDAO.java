package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.MenuItemAddon;
import com.foodapp.util.DatabaseConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for MenuItemAddon entity.
 */
public class MenuItemAddonDAO {
    private static final String INSERT_SQL = "INSERT INTO menu_item_addons (id, menu_item_id, name, extra_price, is_available) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_MENU_ITEM_ID_SQL = "SELECT * FROM menu_item_addons WHERE menu_item_id = ?";
    private static final String UPDATE_AVAILABILITY_SQL = "UPDATE menu_item_addons SET is_available = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM menu_item_addons WHERE id = ?";

    private final DatabaseConnectionPool pool;

    public MenuItemAddonDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void save(MenuItemAddon addon) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                stmt.setString(1, addon.getId());
                stmt.setString(2, addon.getMenuItemId());
                stmt.setString(3, addon.getName());
                stmt.setDouble(4, addon.getExtraPrice());
                stmt.setInt(5, addon.isAvailable() ? 1 : 0);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving menu item addon", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public List<MenuItemAddon> findByMenuItemId(String menuItemId) throws DatabaseException {
        List<MenuItemAddon> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_MENU_ITEM_ID_SQL)) {
                stmt.setString(1, menuItemId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToAddon(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding addons by menu item", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public void updateAvailability(String addonId, boolean available) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_AVAILABILITY_SQL)) {
                stmt.setInt(1, available ? 1 : 0);
                stmt.setString(2, addonId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating addon availability", e);
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
            throw new DatabaseException("Error deleting menu item addon", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    private MenuItemAddon mapResultSetToAddon(ResultSet rs) throws SQLException {
        return new MenuItemAddon(
                rs.getString("id"),
                rs.getString("menu_item_id"),
                rs.getString("name"),
                rs.getDouble("extra_price"),
                rs.getInt("is_available") == 1
        );
    }
}
