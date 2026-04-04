package com.foodapp.service;

import com.foodapp.dao.DeliveryZoneDAO;
import com.foodapp.exception.DatabaseException;
import com.foodapp.model.DeliveryZone;
import com.foodapp.util.UUIDGenerator;
import java.util.List;
import java.util.Optional;

/**
 * Service for delivery zone management.
 */
public class DeliveryZoneService {
    private final DeliveryZoneDAO zoneDAO;

    public DeliveryZoneService(DeliveryZoneDAO zoneDAO) {
        this.zoneDAO = zoneDAO;
    }

    /**
     * Adds a delivery zone to a restaurant.
     */
    public DeliveryZone addZone(String restaurantId, String areaName, double deliveryFee, int estimatedMinutes) {
        try {
            DeliveryZone zone = new DeliveryZone(UUIDGenerator.generate(), restaurantId, areaName, deliveryFee, estimatedMinutes);
            zoneDAO.save(zone);
            return zone;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error adding zone", e);
        }
    }

    /**
     * Gets all delivery zones for a restaurant.
     */
    public List<DeliveryZone> getZonesForRestaurant(String restaurantId) {
        try {
            return zoneDAO.findByRestaurantId(restaurantId);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error getting zones", e);
        }
    }

    /**
     * Gets a specific zone for a restaurant by area.
     */
    public Optional<DeliveryZone> getZoneForArea(String restaurantId, String area) {
        try {
            return zoneDAO.findByRestaurantAndArea(restaurantId, area);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error getting zone by area", e);
        }
    }
}
