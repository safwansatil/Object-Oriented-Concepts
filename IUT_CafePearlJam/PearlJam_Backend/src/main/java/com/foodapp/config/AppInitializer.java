package com.foodapp.config;

import java.util.logging.Logger;

import com.foodapp.dao.CouponDAO;
import com.foodapp.dao.DeliveryZoneDAO;
import com.foodapp.dao.MenuCategoryDAO;
import com.foodapp.dao.MenuItemAddonDAO;
import com.foodapp.dao.MenuItemDAO;
import com.foodapp.dao.OrderDAO;
import com.foodapp.dao.OrderItemDAO;
import com.foodapp.dao.RestaurantDAO;
import com.foodapp.dao.UserDAO;
import com.foodapp.util.DatabaseConnectionPool;
import com.foodapp.util.SchemaInitializer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class AppInitializer implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(AppInitializer.class.getName());
    private DatabaseConnectionPool pool;

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
        CouponDAO couponDAO = new CouponDAO(pool);

        // 5. Services (Business Layer)
        com.foodapp.service.UserService userService = new com.foodapp.service.UserService(userDAO);
        com.foodapp.service.RestaurantService restaurantService = new com.foodapp.service.RestaurantService(restaurantDAO, userDAO);
        com.foodapp.service.MenuService menuService = new com.foodapp.service.MenuService(categoryDAO, itemDAO, addonDAO, restaurantDAO);
        com.foodapp.service.DeliveryZoneService zoneService = new com.foodapp.service.DeliveryZoneService(zoneDAO);
        com.foodapp.service.CouponService couponService = new com.foodapp.service.CouponService(couponDAO);
        com.foodapp.service.OrderService orderService = new com.foodapp.service.OrderService(orderDAO, orderItemDAO, itemDAO, restaurantDAO, zoneDAO, addonDAO, couponService);

        // 6. Store in ServletContext
        ctx.setAttribute(AppConstants.ATTR_USER_SERVICE, userService);
        ctx.setAttribute(AppConstants.ATTR_RESTAURANT_SERVICE, restaurantService);
        ctx.setAttribute(AppConstants.ATTR_MENU_SERVICE, menuService);
        ctx.setAttribute(AppConstants.ATTR_ORDER_SERVICE, orderService);
        ctx.setAttribute(AppConstants.ATTR_COUPON_SERVICE, couponService);
        ctx.setAttribute(AppConstants.ATTR_DELIVERY_ZONE_SERVICE, zoneService);
        ctx.setAttribute(AppConstants.ATTR_MENU_ITEM_ADDON_DAO, addonDAO);

        LOGGER.info("FoodApp Backend initialization complete.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Shutting down FoodApp Backend...");
        
        if (pool != null) {
            pool.shutdown();
        }
    }
}
