package com.foodapp.api.soap;

import com.foodapp.api.soap.dto.AddonInfo;
import com.foodapp.api.soap.dto.MenuItemInfo;
import com.foodapp.api.soap.dto.MenuResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

/**
 * SOAP Menu Query Service interface.
 */
@WebService(name = "MenuQueryService", targetNamespace = "http://foodapp.com/ws")
public interface MenuQueryService {
    
    @WebMethod
    MenuResponse getMenu(@WebParam(name = "restaurantId") String restaurantId);
    
    @WebMethod
    List<MenuItemInfo> searchMenuItems(@WebParam(name = "restaurantId") String restaurantId, @WebParam(name = "query") String query);
    
    @WebMethod
    List<AddonInfo> getItemAddons(@WebParam(name = "menuItemId") String menuItemId);
}
