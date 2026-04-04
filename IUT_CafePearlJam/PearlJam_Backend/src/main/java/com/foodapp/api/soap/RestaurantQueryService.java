package com.foodapp.api.soap;

import com.foodapp.api.soap.dto.DeliveryZoneInfo;
import com.foodapp.api.soap.dto.RestaurantInfo;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

/**
 * SOAP Restaurant Query Service interface.
 */
@WebService(name = "RestaurantQueryService", targetNamespace = "http://foodapp.com/ws")
public interface RestaurantQueryService {
    
    @WebMethod
    List<RestaurantInfo> getRestaurantsByArea(@WebParam(name = "area") String area);
    
    @WebMethod
    List<RestaurantInfo> searchRestaurants(@WebParam(name = "query") String query, @WebParam(name = "area") String area);
    
    @WebMethod
    RestaurantInfo getRestaurantDetails(@WebParam(name = "restaurantId") String restaurantId);
    
    @WebMethod
    List<DeliveryZoneInfo> getDeliveryZones(@WebParam(name = "restaurantId") String restaurantId);
}
