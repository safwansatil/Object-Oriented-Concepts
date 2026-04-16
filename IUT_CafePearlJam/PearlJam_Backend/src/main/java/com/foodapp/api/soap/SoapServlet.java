package com.foodapp.api.soap;

import com.foodapp.config.AppConstants;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;

public class SoapServlet extends CXFServlet {
    @Override
    public void init(ServletConfig sc) throws ServletException {
        super.init(sc);

        var ctx = sc.getServletContext();
        var userService = (com.foodapp.service.UserService) ctx.getAttribute(AppConstants.ATTR_USER_SERVICE);
        var restaurantService = (com.foodapp.service.RestaurantService) ctx.getAttribute(AppConstants.ATTR_RESTAURANT_SERVICE);
        var menuService = (com.foodapp.service.MenuService) ctx.getAttribute(AppConstants.ATTR_MENU_SERVICE);
        var orderService = (com.foodapp.service.OrderService) ctx.getAttribute(AppConstants.ATTR_ORDER_SERVICE);
        var zoneService = (com.foodapp.service.DeliveryZoneService) ctx.getAttribute(AppConstants.ATTR_DELIVERY_ZONE_SERVICE);
        var addonDAO = (com.foodapp.dao.MenuItemAddonDAO) ctx.getAttribute(AppConstants.ATTR_MENU_ITEM_ADDON_DAO);

        Bus bus = getBus();
        new EndpointImpl(bus, new UserServiceImpl(userService)).publish("/users");
        new EndpointImpl(bus, new RestaurantQueryServiceImpl(restaurantService, zoneService)).publish("/restaurants");
        new EndpointImpl(bus, new MenuQueryServiceImpl(menuService, addonDAO)).publish("/menu");
        new EndpointImpl(bus, new OrderServiceImpl(orderService)).publish("/orders");
    }
}
