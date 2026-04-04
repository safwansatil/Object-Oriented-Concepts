package com.foodapp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.foodapp.config.DatabaseConfig;

/**
 * Initializes the database schema and optionally loads sample data.
 */
public class SchemaInitializer {
    private static final Logger LOGGER = Logger.getLogger(SchemaInitializer.class.getName());
    private final DatabaseConnectionPool pool;
    private final DatabaseConfig config;

    public SchemaInitializer(DatabaseConnectionPool pool, DatabaseConfig config) {
        this.pool = pool;
        this.config = config;
    }

    public void initialize() {
        if (!tablesExist()) {
            LOGGER.info("Schema not found. Creating tables...");
            executeSqlScript("/schema.sql");
            
            if (config.shouldLoadSampleData()) {
                LOGGER.info("Loading sample data...");
                executeSqlScript("/sample-data.sql");
            }
        } else {
            LOGGER.info("Schema already exists. Skipping initialization.");
        }
    }

    private boolean tablesExist() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            stmt = conn.createStatement();
            // SQLite specific check for table existence
            rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='users'");
            boolean exists = rs.next();
            if (exists) {
                LOGGER.info("Table 'users' found, assuming schema exists.");
            } else {
                LOGGER.info("Table 'users' NOT found, schema needs initialization.");
            }
            return exists;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking if schema exists", e);
            return false;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException ignored) {}
            if (conn != null) pool.releaseConnection(conn);
        }
    }

    private void executeSqlScript(String resourcePath) {
        Connection conn = null;
        Statement stmt = null;
        
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOGGER.warning("SQL script not found: " + resourcePath);
                return;
            }
            
            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));
            
            // Split by semicolon, but be careful of empty strings
            String[] statements = sql.split(";");
            
            conn = pool.getConnection();
            stmt = conn.createStatement();
            
            // Turn off auto-commit for speed and safety during bulk initialization
            conn.setAutoCommit(false);
            
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
            
            conn.commit();
            LOGGER.info("Successfully executed script: " + resourcePath);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing SQL script: " + resourcePath, e);
            // Rollback if there was an error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    LOGGER.log(Level.WARNING, "Error rolling back transaction", rollbackEx);
                }
            }
        } finally {
            if (stmt != null) {
        try { stmt.close(); } catch (SQLException ignored) {}
    }
    if (conn != null) {
        try {
            // CRITICAL: Reset auto-commit before returning to pool
            if (!conn.getAutoCommit()) {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error resetting auto-commit", e);
        }
        pool.releaseConnection(conn); 
    }
        }
    }
}