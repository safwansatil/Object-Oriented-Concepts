package com.foodapp.util;

import com.foodapp.config.DatabaseConfig;
import com.foodapp.exception.DatabaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A manual, thread-safe blocking connection pool for SQLite.
 */
public class DatabaseConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionPool.class.getName());
    private final String url;
    private final int maxPoolSize;
    private final int timeout;
    private final List<Connection> availableConnections = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();

    public DatabaseConnectionPool(DatabaseConfig config) {
        this.url = "jdbc:sqlite:" + config.getDbPath();
        this.maxPoolSize = config.getMaxPoolSize();
        this.timeout = config.getPoolTimeout();
        initializePool(config.getMinPoolSize());
    }

    private void initializePool(int minSize) {
        try {
            Class.forName("org.sqlite.JDBC");
            for (int i = 0; i < minSize; i++) {
                availableConnections.add(createConnection());
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not initialize connection pool", e);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public synchronized Connection getConnection() {
        long startTime = System.currentTimeMillis();
        while (availableConnections.isEmpty()) {
            if (usedConnections.size() < maxPoolSize) {
                try {
                    Connection conn = createConnection();
                    usedConnections.add(conn);
                    return conn;
                } catch (SQLException e) {
                    throw new DatabaseException("Could not create new connection", e);
                }
            } else {
                long elapsed = System.currentTimeMillis() - startTime;
                long remaining = timeout - elapsed;
                if (remaining <= 0) {
                    throw new DatabaseException("Connection pool timeout: no available connections");
                }
                try {
                    wait(remaining);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new DatabaseException("Thread interrupted while waiting for connection", e);
                }
            }
        }
        Connection conn = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(conn);
        return conn;
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            availableConnections.add(connection);
            notifyAll();
        }
    }

    public synchronized void shutdown() {
        for (Connection conn : availableConnections) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing connection during shutdown", e);
            }
        }
        for (Connection conn : usedConnections) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing used connection during shutdown", e);
            }
        }
        availableConnections.clear();
        usedConnections.clear();
    }
}
