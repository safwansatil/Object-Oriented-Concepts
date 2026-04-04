package com.foodapp.dao;

import com.foodapp.exception.DatabaseException;
import com.foodapp.model.User;
import com.foodapp.model.UserRole;
import com.foodapp.util.DatabaseConnectionPool;
import java.sql.*;
import java.sql.*;
import java.util.Optional;

/**
 * Data Access Object for User entity.
 */
public class UserDAO {
    private static final String INSERT_SQL = "INSERT INTO users (id, name, email, password_hash, phone, role, created_at, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password_hash = ? WHERE id = ?";
    private static final String SET_ACTIVE_STATUS_SQL = "UPDATE users SET is_active = ? WHERE id = ?";

    private final DatabaseConnectionPool pool;

    public UserDAO(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void save(User user) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
                stmt.setString(1, user.getId());
                stmt.setString(2, user.getName());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getPasswordHash());
                stmt.setString(5, user.getPhone());
                stmt.setString(6, user.getRole().name());
                stmt.setString(7, user.getCreatedAt());
                stmt.setInt(8, user.isActive() ? 1 : 0);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving user", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public Optional<User> findById(String id) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSetToUser(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding user by id", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_EMAIL_SQL)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSetToUser(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding user by email", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    public void updatePassword(String userId, String newPasswordHash) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_PASSWORD_SQL)) {
                stmt.setString(1, newPasswordHash);
                stmt.setString(2, userId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating password", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void setActiveStatus(String userId, boolean active) throws DatabaseException {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(SET_ACTIVE_STATUS_SQL)) {
                stmt.setInt(1, active ? 1 : 0);
                stmt.setString(2, userId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error setting active status", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("phone"),
                UserRole.valueOf(rs.getString("role")),
                rs.getString("created_at"),
                rs.getInt("is_active") == 1
        );
    }
}
