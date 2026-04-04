package com.foodapp.config;

import com.foodapp.dao.*;
import com.foodapp.util.DatabaseConnectionPool;
import com.foodapp.util.SchemaInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.EndpointImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Bootstraps the application: Config, DB, DAOs, Services.
 */
@WebListener
public class AppInitializer implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(AppInitializer.class.getName());
    private DatabaseConnectionPool pool;
    private final List<EndpointImpl> endpoints = new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Initializing FoodApp Backend...");
        ServletContext ctx = sce.getServletContext();

        // 1. Configuration
        DatabaseConfig config = new DatabaseConfig();

        // 2. Database Pool
        pool = new DatabaseConnectionPool(config);
        ctx.setAttribute(AppConstants.ATTR_DB_POOL, pool);

        // 3. Schema Initialization
        SchemaInitializer schemaInitializer = new SchemaInitializer(pool, config);
        schemaInitializer.initialize();

        // 4. DAOs
        UserDAO userDAO = new UserDAO(pool);
        RestaurantDAO restaurantDAO = new RestaurantDAO(pool);
        MenuCategoryDAO categoryDAO = new MenuCategoryDAO(pool);
        MenuItemDAO itemDAO = new MenuItemDAO(pool);
        MenuItemAddonDAO addonDAO = new MenuItemAddonDAO(pool);
        OrderDAO orderDAO = new OrderDAO(pool);
        OrderItemDAO orderItemDAO = new OrderItemDAO(pool);
        DeliveryZoneDAO zoneDAO = new DeliveryZoneDAO(pool);

        // 5. Services (Business Layer)
        com.foodapp.service.UserService userService = new com.foodapp.service.UserService(userDAO);
        com.foodapp.service.RestaurantService restaurantService = new com.foodapp.service.RestaurantService(restaurantDAO, userDAO);
        com.foodapp.service.MenuService menuService = new com.foodapp.service.MenuService(categoryDAO, itemDAO, addonDAO, restaurantDAO);
        com.foodapp.service.DeliveryZoneService zoneService = new com.foodapp.service.DeliveryZoneService(zoneDAO);
        com.foodapp.service.OrderService orderService = new com.foodapp.service.OrderService(orderDAO, orderItemDAO, itemDAO, restaurantDAO, zoneDAO, addonDAO);

        // 6. Store in ServletContext
        ctx.setAttribute(AppConstants.ATTR_USER_SERVICE, userService);
        ctx.setAttribute(AppConstants.ATTR_RESTAURANT_SERVICE, restaurantService);
        ctx.setAttribute(AppConstants.ATTR_MENU_SERVICE, menuService);
        ctx.setAttribute(AppConstants.ATTR_ORDER_SERVICE, orderService);
        ctx.setAttribute(AppConstants.ATTR_DELIVERY_ZONE_SERVICE, zoneService);

        // 7. Register SOAP Endpoints (Programmatic CXF)
        try {
            Bus bus = BusFactory.getDefaultBus();
            
            publishEndpoint(bus, "/users", new com.foodapp.api.soap.UserServiceImpl(userService));
            publishEndpoint(bus, "/restaurants", new com.foodapp.api.soap.RestaurantQueryServiceImpl(restaurantService, zoneService));
            publishEndpoint(bus, "/menu", new com.foodapp.api.soap.MenuQueryServiceImpl(menuService, addonDAO));
            publishEndpoint(bus, "/orders", new com.foodapp.api.soap.OrderServiceImpl(orderService));
            
            LOGGER.info("SOAP endpoints published successfully via CXF.");
        } catch (Exception e) {
            LOGGER.severe("Failed to publish SOAP endpoints: " + e.getMessage());
            e.printStackTrace();
        }

        LOGGER.info("FoodApp Backend initialization complete.");
    }

    private void publishEndpoint(Bus bus, String path, Object implementor) {
        EndpointImpl endpoint = new EndpointImpl(bus, implementor);
        endpoint.publish(path);
        endpoints.add(endpoint);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Shutting down FoodApp Backend...");
        
        for (EndpointImpl ep : endpoints) {
            try {
                ep.stop();
            } catch (Exception e) {
                // Ignore
            }
        }
        
        if (pool != null) {
            pool.shutdown();
        }
    }
}
