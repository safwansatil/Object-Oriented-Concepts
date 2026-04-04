package com.foodapp.service;

import com.foodapp.dao.RestaurantDAO;
import com.foodapp.dao.UserDAO;
import com.foodapp.exception.DatabaseException;
import com.foodapp.exception.ResourceNotFoundException;
import com.foodapp.exception.ValidationException;
import com.foodapp.model.Restaurant;
import com.foodapp.model.User;
import com.foodapp.model.UserRole;
import com.foodapp.util.TimeUtil;
import com.foodapp.util.UUIDGenerator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for restaurant management and lookup.
 */
public class RestaurantService {
    private final RestaurantDAO restaurantDAO;
    private final UserDAO userDAO;

    public RestaurantService(RestaurantDAO restaurantDAO, UserDAO userDAO) {
        this.restaurantDAO = restaurantDAO;
        this.userDAO = userDAO;
    }

    /**
     * Registers a new restaurant.
     */
    public Restaurant register(String ownerId, String name, String address, String area, double lat, double lng, 
                                String phone, String cuisineType, String opensAt, String closesAt, String description) {
        try {
            Optional<User> ownerOpt = userDAO.findById(ownerId);
            if (ownerOpt.isEmpty() || ownerOpt.get().getRole() != UserRole.RESTAURANT_OWNER) {
                throw new ValidationException("Invalid owner ID.");
            }

            String id = UUIDGenerator.generate();
            String createdAt = TimeUtil.nowISO();
            
            Restaurant restaurant = new Restaurant(id, ownerId, name, description, address, area, lat, lng, 
                                                   phone, cuisineType, true, opensAt, closesAt, createdAt);
            restaurantDAO.save(restaurant);
            return restaurant;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error during restaurant registration", e);
        }
    }

    /**
     * Gets active restaurants in area.
     */
    public List<Restaurant> getRestaurantsInArea(String area) {
        try {
            return restaurantDAO.findByArea(area);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error finding restaurants", e);
        }
    }

    /**
     * Checks if restaurant is currently open.
     */
    public boolean isOpen(Restaurant restaurant) {
        return TimeUtil.isCurrentlyOpen(restaurant.getOpensAt(), restaurant.getClosesAt());
    }

    /**
     * Case-insensitive search on name, cuisine_type filtered by area.
     */
    public List<Restaurant> search(String query, String area) {
        try {
            List<Restaurant> restaurants = (area == null || area.isEmpty()) ? 
                                           restaurantDAO.findAll() : restaurantDAO.findByArea(area);
            
            String lowerQuery = query.toLowerCase();
            return restaurants.stream()
                    .filter(r -> r.getName().toLowerCase().contains(lowerQuery) || 
                                 r.getCuisineType().toLowerCase().contains(lowerQuery))
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error searching restaurants", e);
        }
    }

    /**
     * Updates restaurant schedule.
     */
    public void updateSchedule(String restaurantId, String opensAt, String closesAt) {
        try {
            restaurantDAO.updateSchedule(restaurantId, opensAt, closesAt);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error updating schedule", e);
        }
    }

    /**
     * Gets restaurant details.
     */
    public Optional<Restaurant> getRestaurantDetails(String restaurantId) {
        try {
            return restaurantDAO.findById(restaurantId);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error getting restaurant details", e);
        }
    }
}
