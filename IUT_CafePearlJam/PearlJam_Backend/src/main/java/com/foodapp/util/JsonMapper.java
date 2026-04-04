package com.foodapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton wrapper around Jackson ObjectMapper.
 */
public final class JsonMapper {
    private static final Logger LOGGER = Logger.getLogger(JsonMapper.class.getName());
    private static JsonMapper instance;
    private final ObjectMapper mapper;

    private JsonMapper() {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    public static synchronized JsonMapper getInstance() {
        if (instance == null) {
            instance = new JsonMapper();
        }
        return instance;
    }

    public String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error converting to JSON", e);
            return "{}";
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error converting from JSON", e);
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}
