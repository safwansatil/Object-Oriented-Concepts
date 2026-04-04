package com.foodapp.api.soap;

import com.foodapp.api.soap.dto.DeliveryZoneInfo;
import com.foodapp.api.soap.dto.RestaurantInfo;
import com.foodapp.model.DeliveryZone;
import com.foodapp.model.Restaurant;
import com.foodapp.service.DeliveryZoneService;
import com.foodapp.service.RestaurantService;
import jakarta.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of Restaurant Query SOAP Service.
 */
@WebService(endpointInterface = "com.foodapp.api.soap.RestaurantQueryService", targetNamespace = "http://foodapp.com/ws", serviceName = "RestaurantQueryService")
public class RestaurantQueryServiceImpl implements RestaurantQueryService {
    
    private final RestaurantService restaurantService;
    private final DeliveryZoneService zoneService;

    public RestaurantQueryServiceImpl(RestaurantService restaurantService, DeliveryZoneService zoneService) {
        this.restaurantService = restaurantService;
        this.zoneService = zoneService;
    }

    @Override
    public List<RestaurantInfo> getRestaurantsByArea(String area) {
        return restaurantService.getRestaurantsInArea(area).stream()
                .map(this::mapToInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantInfo> searchRestaurants(String query, String area) {
        return restaurantService.search(query, area).stream()
                .map(this::mapToInfo)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantInfo getRestaurantDetails(String restaurantId) {
        return restaurantService.getRestaurantDetails(restaurantId)
                .map(this::mapToInfo)
                .orElse(null);
    }

    @Override
    public List<DeliveryZoneInfo> getDeliveryZones(String restaurantId) {
        return zoneService.getZonesForRestaurant(restaurantId).stream()
                .map(z -> new DeliveryZoneInfo(z.getId(), z.getRestaurantId(), z.getAreaName(), z.getDeliveryFee(), z.getEstimatedMinutes()))
                .collect(Collectors.toList());
    }

    private RestaurantInfo mapToInfo(Restaurant r) {
        return new RestaurantInfo(r.getId(), r.getName(), r.getDescription(), r.getAddress(), r.getArea(), r.getCuisineType(), r.isActive(), r.getOpensAt(), r.getClosesAt());
    }
}
