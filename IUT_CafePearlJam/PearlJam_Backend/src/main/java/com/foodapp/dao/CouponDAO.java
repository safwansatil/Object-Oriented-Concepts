package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.Coupon;
import com.foodapp.util.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CouponDAO {
    private static final String SELECT_BY_CODE_SQL = "SELECT * FROM coupons WHERE code = ? AND is_active = 1";
    private static final String INCREMENT_USE_SQL = "UPDATE coupons SET current_uses = current_uses + 1 WHERE id = ?";
    private final DatabaseConnectionPool pool;

    public CouponDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public Optional<Coupon> findActiveByCode(String code) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CODE_SQL)) {
                stmt.setString(1, code);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(new Coupon(
                                rs.getString("id"),
                                rs.getString("code"),
                                rs.getString("discount_type"),
                                rs.getDouble("discount_value"),
                                rs.getDouble("minimum_order_value"),
                                (Integer) rs.getObject("max_uses"),
                                (Integer) rs.getObject("current_uses"),
                                rs.getString("expires_at"),
                                rs.getInt("is_active") == 1
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding coupon by code", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    public void incrementUse(String couponId) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(INCREMENT_USE_SQL)) {
                stmt.setString(1, couponId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error incrementing coupon usage", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }
}
