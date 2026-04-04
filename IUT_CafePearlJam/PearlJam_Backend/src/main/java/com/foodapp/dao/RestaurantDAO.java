package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.Restaurant;
import com.foodapp.util.DatabaseConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for Restaurant entity.
 */
public class RestaurantDAO {
    private static final String INSERT_SQL = "INSERT INTO restaurants (id, owner_id, name, description, address, area, latitude, longitude, phone, cuisine_type, is_active, opens_at, closes_at, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM restaurants WHERE id = ?";
    private static final String SELECT_BY_AREA_SQL = "SELECT * FROM restaurants WHERE area = ? AND is_active = 1";
    private static final String SELECT_ALL_SQL = "SELECT * FROM restaurants";
    private static final String UPDATE_SQL = "UPDATE restaurants SET name = ?, description = ?, address = ?, area = ?, latitude = ?, longitude = ?, phone = ?, cuisine_type = ? WHERE id = ?";
    private static final String SELECT_BY_OWNER_ID_SQL = "SELECT * FROM restaurants WHERE owner_id = ?";
    private static final String UPDATE_SCHEDULE_SQL = "UPDATE restaurants SET opens_at = ?, closes_at = ? WHERE id = ?";
    private static final String SET_ACTIVE_STATUS_SQL = "UPDATE restaurants SET is_active = ? WHERE id = ?";

    private final DatabaseConnectionPool pool;

    public RestaurantDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void save(Restaurant restaurant) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                stmt.setString(1, restaurant.getId());
                stmt.setString(2, restaurant.getOwnerId());
                stmt.setString(3, restaurant.getName());
                stmt.setString(4, restaurant.getDescription());
                stmt.setString(5, restaurant.getAddress());
                stmt.setString(6, restaurant.getArea());
                stmt.setDouble(7, restaurant.getLatitude());
                stmt.setDouble(8, restaurant.getLongitude());
                stmt.setString(9, restaurant.getPhone());
                stmt.setString(10, restaurant.getCuisineType());
                stmt.setInt(11, restaurant.isActive() ? 1 : 0);
                stmt.setString(12, restaurant.getOpensAt());
                stmt.setString(13, restaurant.getClosesAt());
                stmt.setString(14, restaurant.getCreatedAt());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving restaurant", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public Optional<Restaurant> findById(String id) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSetToRestaurant(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding restaurant by id", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    public List<Restaurant> findByArea(String area) throws DatabaseException {
        List<Restaurant> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_AREA_SQL)) {
                stmt.setString(1, area);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToRestaurant(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding restaurants by area", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public List<Restaurant> findAll() throws DatabaseException {
        List<Restaurant> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToRestaurant(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding all restaurants", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public void update(Restaurant restaurant) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, restaurant.getName());
                stmt.setString(2, restaurant.getDescription());
                stmt.setString(3, restaurant.getAddress());
                stmt.setString(4, restaurant.getArea());
                stmt.setDouble(5, restaurant.getLatitude());
                stmt.setDouble(6, restaurant.getLongitude());
                stmt.setString(7, restaurant.getPhone());
                stmt.setString(8, restaurant.getCuisineType());
                stmt.setString(9, restaurant.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating restaurant", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public List<Restaurant> findByOwnerId(String ownerId) throws DatabaseException {
        List<Restaurant> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_OWNER_ID_SQL)) {
                stmt.setString(1, ownerId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToRestaurant(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding restaurants by owner id", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public void updateSchedule(String restaurantId, String opensAt, String closesAt) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SCHEDULE_SQL)) {
                stmt.setString(1, opensAt);
                stmt.setString(2, closesAt);
                stmt.setString(3, restaurantId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating restaurant schedule", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void setActiveStatus(String restaurantId, boolean active) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SET_ACTIVE_STATUS_SQL)) {
                stmt.setInt(1, active ? 1 : 0);
                stmt.setString(2, restaurantId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error setting restaurant active status", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    private Restaurant mapResultSetToRestaurant(ResultSet rs) throws SQLException {
        return new Restaurant(
                rs.getString("id"),
                rs.getString("owner_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("address"),
                rs.getString("area"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                rs.getString("phone"),
                rs.getString("cuisine_type"),
                rs.getInt("is_active") == 1,
                rs.getString("opens_at"),
                rs.getString("closes_at"),
                rs.getString("created_at")
        );
    }
}
