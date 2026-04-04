package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.DeliveryZone;
import com.foodapp.util.DatabaseConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for DeliveryZone entity.
 */
public class DeliveryZoneDAO {
    private static final String INSERT_SQL = "INSERT INTO delivery_zones (id, restaurant_id, area_name, delivery_fee, estimated_minutes) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_RESTAURANT_ID_SQL = "SELECT * FROM delivery_zones WHERE restaurant_id = ?";
    private static final String SELECT_BY_RESTAURANT_AND_AREA_SQL = "SELECT * FROM delivery_zones WHERE restaurant_id = ? AND area_name = ?";
    private static final String DELETE_SQL = "DELETE FROM delivery_zones WHERE id = ?";

    private final DatabaseConnectionPool pool;

    public DeliveryZoneDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void save(DeliveryZone zone) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                stmt.setString(1, zone.getId());
                stmt.setString(2, zone.getRestaurantId());
                stmt.setString(3, zone.getAreaName());
                stmt.setDouble(4, zone.getDeliveryFee());
                stmt.setInt(5, zone.getEstimatedMinutes());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving delivery zone", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public List<DeliveryZone> findByRestaurantId(String restaurantId) throws DatabaseException {
        List<DeliveryZone> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_RESTAURANT_ID_SQL)) {
                stmt.setString(1, restaurantId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapResultSetToZone(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding delivery zones by restaurant", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    public Optional<DeliveryZone> findByRestaurantAndArea(String restaurantId, String area) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_RESTAURANT_AND_AREA_SQL)) {
                stmt.setString(1, restaurantId);
                stmt.setString(2, area);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSetToZone(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding delivery zone by restaurant and area", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
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
            throw new DatabaseException("Error deleting delivery zone", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    private DeliveryZone mapResultSetToZone(ResultSet rs) throws SQLException {
        return new DeliveryZone(
                rs.getString("id"),
                rs.getString("restaurant_id"),
                rs.getString("area_name"),
                rs.getDouble("delivery_fee"),
                rs.getInt("estimated_minutes")
        );
    }
}
