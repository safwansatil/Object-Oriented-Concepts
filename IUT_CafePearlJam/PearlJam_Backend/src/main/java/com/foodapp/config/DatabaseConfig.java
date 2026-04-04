package com.foodapp.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads application configuration from app.properties.
 */
public final class DatabaseConfig {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConfig.class.getName());
    private static final String CONFIG_FILE = "/app.properties";
    private final Properties properties = new Properties();

    public DatabaseConfig() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                LOGGER.severe("Unable to find " + CONFIG_FILE);
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error loading configuration", ex);
        }
    }

    public String getDbPath() {
        return properties.getProperty("db.path", AppConstants.DEFAULT_DB_PATH);
    }

    public int getMinPoolSize() {
        return Integer.parseInt(properties.getProperty("db.pool.min", "2"));
    }

    public int getMaxPoolSize() {
        return Integer.parseInt(properties.getProperty("db.pool.max", "10"));
    }

    public int getPoolTimeout() {
        return Integer.parseInt(properties.getProperty("db.pool.timeout", "5000"));
    }

    public boolean shouldLoadSampleData() {
        return Boolean.parseBoolean(properties.getProperty("app.load.sample.data", "false"));
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
