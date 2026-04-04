package com.foodapp.config;

/**
 * Global application constants.
 */
public final class AppConstants {
    private AppConstants() {}

    public static final String APP_NAME = "FoodApp";
    public static final String DB_PREFIX = "jdbc:sqlite:";
    public static final String DEFAULT_DB_PATH = "data/foodapp.db";
    
    // Role constants
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String ROLE_RESTAURANT_OWNER = "RESTAURANT_OWNER";
    public static final String ROLE_RIDER = "RIDER";
    
    // Order Status constants
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_PREPARING = "PREPARING";
    public static final String STATUS_READY_FOR_PICKUP = "READY_FOR_PICKUP";
    public static final String STATUS_OUT_FOR_DELIVERY = "OUT_FOR_DELIVERY";
    public static final String STATUS_DELIVERED = "DELIVERED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    
    // Payment Status constants
    public static final String PAYMENT_UNPAID = "UNPAID";
    public static final String PAYMENT_PAID = "PAID";
    public static final String PAYMENT_REFUNDED = "REFUNDED";

    // Date/Time formats
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    
    // Servlet Context Attributes
    public static final String ATTR_DB_POOL = "db_pool";
    public static final String ATTR_USER_SERVICE = "user_service";
    public static final String ATTR_RESTAURANT_SERVICE = "restaurant_service";
    public static final String ATTR_MENU_SERVICE = "menu_service";
    public static final String ATTR_ORDER_SERVICE = "order_service";
    public static final String ATTR_COUPON_SERVICE = "coupon_service";
    public static final String ATTR_DELIVERY_ZONE_SERVICE = "delivery_zone_service";
}
