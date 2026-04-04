```
You are building a professional, production-quality Java backend for a Food Delivery 
application. This is a solo academic/portfolio project. The goal is clean, 
well-structured, idiomatic Java — not "AI slop". Every class must have a clear 
single responsibility. No God classes. No copy-pasted boilerplate. No unnecessary 
abstraction. Write code like a senior Java engineer who takes pride in their work.

════════════════════════════════════════
ENVIRONMENT
════════════════════════════════════════
- JDK: Oracle OpenJDK 23
- Build: Maven (pom.xml)
- Server: Apache Tomcat 10.x (via tomcat10-maven-plugin, embedded — mvn tomcat10:run)
- No Spring. No Spring Boot. No Spring anything.
- Frameworks allowed: Jakarta EE 10 (Servlets, CDI annotations), Apache CXF 4.x 
  (SOAP/JAX-WS), Jackson 2.x (JSON), SQLite via xerial JDBC driver, JUnit 5
- Archetype: maven-archetype-webapp

════════════════════════════════════════
PACKAGE STRUCTURE (strict — do not deviate)
════════════════════════════════════════
com.foodapp
├── api
│   ├── servlet          ← HttpServlet subclasses (one per resource group)
│   └── soap             ← JAX-WS @WebService interfaces + implementations
├── service              ← Pure business logic. No HTTP, no SQL here.
├── dao                  ← All JDBC. One DAO class per domain entity.
├── model                ← Plain Java POJOs. No logic, only fields + getters/setters.
├── exception            ← Custom checked/unchecked exception types
├── util                 ← Stateless helpers: DBConnectionPool, JsonMapper, 
│                           PasswordHasher, Validator
└── config               ← AppConstants, DatabaseConfig (reads from properties file)

════════════════════════════════════════
DATABASE: SQLite via JDBC
════════════════════════════════════════
- Single SQLite file: data/foodapp.db (relative to project root, created on startup)
- Class DatabaseConfig reads db path from src/main/resources/app.properties
- Class DatabaseConnectionPool: manual connection pool (no external pool lib), 
  min 2 / max 10 connections, thread-safe using synchronized blocks
- Schema is created via schema.sql in src/main/resources/, executed on first startup 
  by a SchemaInitializer class that checks if tables exist before running DDL

DATABASE SCHEMA — implement exactly these tables:

users (id TEXT PK, name TEXT, email TEXT UNIQUE, password_hash TEXT, 
       phone TEXT, role TEXT CHECK(role IN ('CUSTOMER','RESTAURANT_OWNER','RIDER')),
       created_at TEXT, is_active INTEGER DEFAULT 1)

restaurants (id TEXT PK, owner_id TEXT FK→users, name TEXT, description TEXT,
             address TEXT, area TEXT, latitude REAL, longitude REAL,
             phone TEXT, cuisine_type TEXT, is_active INTEGER DEFAULT 1,
             opens_at TEXT, closes_at TEXT, created_at TEXT)

menu_categories (id TEXT PK, restaurant_id TEXT FK→restaurants, 
                 name TEXT, display_order INTEGER)

menu_items (id TEXT PK, category_id TEXT FK→menu_categories, restaurant_id TEXT FK,
            name TEXT, description TEXT, base_price REAL, image_url TEXT,
            is_available INTEGER DEFAULT 1, track_quantity INTEGER DEFAULT 0,
            quantity_in_stock INTEGER, preparation_time_minutes INTEGER,
            created_at TEXT)

menu_item_addons (id TEXT PK, menu_item_id TEXT FK→menu_items,
                  name TEXT, extra_price REAL, is_available INTEGER DEFAULT 1)

orders (id TEXT PK, customer_id TEXT FK→users, restaurant_id TEXT FK→restaurants,
        rider_id TEXT FK→users nullable, delivery_address TEXT,
        delivery_area TEXT, subtotal REAL, delivery_fee REAL, discount_amount REAL,
        total REAL, status TEXT CHECK(status IN 
        ('PENDING','CONFIRMED','PREPARING','READY_FOR_PICKUP',
         'OUT_FOR_DELIVERY','DELIVERED','CANCELLED')),
        payment_status TEXT CHECK(payment_status IN ('UNPAID','PAID','REFUNDED')),
        payment_method TEXT, special_instructions TEXT,
        placed_at TEXT, confirmed_at TEXT, delivered_at TEXT)

order_items (id TEXT PK, order_id TEXT FK→orders, menu_item_id TEXT FK,
             item_name TEXT, item_price REAL, quantity INTEGER,
             selected_addons TEXT, item_total REAL)

coupons (id TEXT PK, code TEXT UNIQUE, description TEXT,
         discount_type TEXT CHECK(discount_type IN ('PERCENTAGE','FLAT')),
         discount_value REAL, minimum_order_value REAL, max_uses INTEGER,
         used_count INTEGER DEFAULT 0, expires_at TEXT, is_active INTEGER DEFAULT 1)

delivery_zones (id TEXT PK, restaurant_id TEXT FK→restaurants,
                area_name TEXT, delivery_fee REAL, estimated_minutes INTEGER)

Use TEXT for all IDs (UUID strings). Use TEXT for all timestamps (ISO-8601). 
Use INTEGER (0/1) for all booleans. Add proper indexes on FK columns and 
frequently-queried columns (area, status, restaurant_id, customer_id).

════════════════════════════════════════
MODEL CLASSES
════════════════════════════════════════
Create one POJO per table. Each model class must have:
- Private fields matching the schema exactly
- A no-arg constructor
- A full-arg constructor
- Proper getters and setters (no Lombok — write them out)
- Override toString() meaningfully
- For enums: OrderStatus, PaymentStatus, UserRole, DiscountType — 
  create proper Java enums in the model package

════════════════════════════════════════
DAO LAYER
════════════════════════════════════════
Each DAO handles ONE entity. Pattern to follow:
- Constructor takes a DatabaseConnectionPool instance (injected, not static)
- All methods throw a custom DatabaseException (checked) that wraps SQLException
- Use try-with-resources for ALL connections and statements
- Use PreparedStatements everywhere — zero string concatenation in SQL
- Return Optional<T> for single-item lookups
- Return List<T> (never null, empty list if none) for collections

DAO classes to create:
- UserDAO: save, findById, findByEmail, updatePassword, setActiveStatus
- RestaurantDAO: save, findById, findByArea, findAll, update, 
                 findByOwnerId, updateSchedule, setActiveStatus
- MenuCategoryDAO: save, findByRestaurantId, update, delete
- MenuItemDAO: save, findById, findByCategoryId, findByRestaurantId,
               updateAvailability, updateStock, update
- MenuItemAddonDAO: save, findByMenuItemId, updateAvailability, delete
- OrderDAO: save, findById, findByCustomerId, findByRestaurantId, 
            findByRiderId, updateStatus, updatePaymentStatus, assignRider,
            findByStatusAndRestaurant
- OrderItemDAO: saveAll, findByOrderId
- CouponDAO: save, findByCode, incrementUsedCount, findAll, setActiveStatus
- DeliveryZoneDAO: save, findByRestaurantId, findByRestaurantAndArea, delete

════════════════════════════════════════
SERVICE LAYER
════════════════════════════════════════
Services contain ALL business logic. They call DAOs, never Servlets or each other 
unless necessary. Each service takes its required DAOs via constructor.

Services to create:

UserService
  - register(name, email, plainPassword, phone, role) → User
    Validates email format, checks duplicate email, hashes password (SHA-256 + salt),
    generates UUID, saves via UserDAO
  - login(email, plainPassword) → Optional<User>
    Finds by email, verifies password hash
  - changePassword(userId, oldPassword, newPassword) → void

RestaurantService
  - register(ownerId, name, address, area, lat, lng, phone, cuisineType, 
             opensAt, closesAt, description) → Restaurant
    Validates owner exists and has RESTAURANT_OWNER role, generates UUID, saves
  - getRestaurantsInArea(area) → List<Restaurant>
    Filters to only active restaurants
  - isOpen(Restaurant) → boolean
    Compares current time against opens_at/closes_at
  - search(query, area) → List<Restaurant>
    Case-insensitive search on name, cuisine_type filtered by area
  - updateSchedule(restaurantId, opensAt, closesAt) → void
  - getRestaurantDetails(restaurantId) → Optional<Restaurant>

MenuService
  - addCategory(restaurantId, name, displayOrder) → MenuCategory
  - addItem(restaurantId, categoryId, name, description, basePrice, 
            imageUrl, prepTime, trackQuantity, initialStock) → MenuItem
  - addAddon(menuItemId, name, extraPrice) → MenuItemAddon
  - getFullMenu(restaurantId) → Map<MenuCategory, List<MenuItem>>
    Returns menu as an ordered map: category → items under it
  - setItemAvailability(itemId, available) → void
  - updateItemStock(itemId, newQuantity) → void
  - searchMenuItems(restaurantId, query) → List<MenuItem>

OrderService
  - placeOrder(customerId, restaurantId, deliveryAddress, deliveryArea, 
               List<OrderItemRequest>, couponCode, paymentMethod, 
               specialInstructions) → Order
    Steps:
    1. Validate restaurant is active and open
    2. Validate all menu items exist, are available, and restaurant owns them
    3. Check stock for items with tracking enabled, decrement if placing order
    4. Calculate subtotal (sum of item prices × qty + selected addons)
    5. Lookup delivery fee from DeliveryZoneDAO for the area
    6. Apply coupon if provided (validate via CouponService)
    7. Calculate total, generate order UUID, set status PENDING, save
    8. Save all OrderItems
    Return the saved Order object
  - confirmOrder(restaurantId, orderId) → Order  [validates restaurant owns order]
  - updateStatus(actorId, orderId, newStatus) → Order
    Validates actor has permission for that status transition
  - assignRider(restaurantId, orderId, riderId) → void
  - getOrdersByCustomer(customerId) → List<Order>
  - getOrdersByRestaurant(restaurantId) → List<Order>
  - getOrderDetails(orderId) → Order  [includes order items]
  - cancelOrder(actorId, orderId) → void
    Only allowed in PENDING or CONFIRMED status

CouponService
  - validate(couponCode, orderSubtotal) → Coupon  throws InvalidCouponException
    Checks exists, is_active, not expired, not over max_uses, 
    meets minimum_order_value
  - apply(coupon, subtotal) → BigDecimal  [returns discount amount]
  - createCoupon(code, description, discountType, value, minOrder, 
                 maxUses, expiresAt) → Coupon
  - listAll() → List<Coupon>

DeliveryZoneService
  - addZone(restaurantId, areaName, deliveryFee, estimatedMinutes) → DeliveryZone
  - getZonesForRestaurant(restaurantId) → List<DeliveryZone>
  - getZoneForArea(restaurantId, area) → Optional<DeliveryZone>

════════════════════════════════════════
EXCEPTION CLASSES
════════════════════════════════════════
Create these in com.foodapp.exception:
- AppException (base RuntimeException with message + HTTP status code field)
- DatabaseException extends AppException (wraps SQLExceptions)
- ResourceNotFoundException extends AppException (404)
- ValidationException extends AppException (400) — carries a List<String> of errors
- AuthException extends AppException (401/403)
- InvalidCouponException extends AppException (400)
- BusinessRuleException extends AppException (422) — e.g. restaurant closed, 
  item out of stock, invalid status transition

════════════════════════════════════════
SOAP API LAYER (JAX-WS via Apache CXF)
════════════════════════════════════════
Use Apache CXF 4.x. Configure CXFServlet in web.xml mapped to /ws/*
Create a cxf-servlet.xml (or programmatic config) that registers the endpoints.

Expose these 5 WSDL services:

1. RestaurantQueryService  @WebService
   WSDL at: /ws/restaurants?wsdl
   Methods:
   - getRestaurantsByArea(String area) → List<RestaurantInfo>
   - searchRestaurants(String query, String area) → List<RestaurantInfo>
   - getRestaurantDetails(String restaurantId) → RestaurantInfo
   - getDeliveryZones(String restaurantId) → List<DeliveryZoneInfo>

2. MenuQueryService  @WebService
   WSDL at: /ws/menu?wsdl
   Methods:
   - getMenu(String restaurantId) → MenuResponse
     (MenuResponse contains List<CategoryWithItems>)
   - searchMenuItems(String restaurantId, String query) → List<MenuItemInfo>
   - getItemAddons(String menuItemId) → List<AddonInfo>

3. OrderService (SOAP)  @WebService
   WSDL at: /ws/orders?wsdl
   Methods:
   - placeOrder(PlaceOrderRequest) → OrderConfirmation
   - getOrderStatus(String orderId) → OrderStatusResponse
   - getOrdersByCustomer(String customerId) → List<OrderSummary>
   - cancelOrder(String orderId, String customerId) → OperationResult

4. CouponService (SOAP)  @WebService
   WSDL at: /ws/coupons?wsdl
   Methods:
   - validateCoupon(String code, double orderSubtotal) → CouponValidationResult
   - listActiveCoupons() → List<CouponInfo>

5. UserService (SOAP)  @WebService
   WSDL at: /ws/users?wsdl
   Methods:
   - registerUser(RegisterRequest) → UserInfo
   - login(String email, String password) → LoginResult

For each service:
- Create a separate interface annotated with @WebService(name=..., 
  targetNamespace="http://foodapp.com/ws")
- Create an implementation class suffixed with Impl
- All request/response objects are separate JAXB-annotated classes 
  in com.foodapp.api.soap.dto
- SOAP services call the corresponding business service classes — 
  they do NOT contain logic
- Wrap all service exceptions into proper SOAP faults using 
  @WebFault-annotated exception classes

════════════════════════════════════════
UTILITY CLASSES
════════════════════════════════════════

PasswordHasher:
  - hashPassword(String plain) → String
    Uses SHA-256 with a random salt: "SALT:HASH" format stored in DB
  - verifyPassword(String plain, String stored) → boolean

UUIDGenerator:
  - generate() → String  (UUID.randomUUID().toString())

Validator:
  - isValidEmail(String) → boolean
  - isNotBlank(String) → boolean
  - isPositive(double) → boolean
  - validateRequired(Map<String, String> fieldNameToValue) → List<String> errors

JsonMapper (wraps ObjectMapper):
  - toJson(Object) → String
  - fromJson(String, Class<T>) → T
  Singleton pattern. Configured with ISO date format.

TimeUtil:
  - nowISO() → String  (current UTC time as ISO-8601)
  - parseTime(String HH:mm) → LocalTime
  - isCurrentlyOpen(String opensAt, String closesAt) → boolean

════════════════════════════════════════
SERVLET LAYER (REST-like JSON endpoints)
════════════════════════════════════════
Simple HttpServlet subclasses. Each reads the request, calls a service, 
writes JSON response. Use a BaseServlet abstract class that provides:
  - writeJson(HttpServletResponse, int status, Object body)
  - writeError(HttpServletResponse, int status, String message)
  - readBody(HttpServletRequest) → String
  - getPathParam(HttpServletRequest, int index) → String

These JSON endpoints complement the SOAP APIs for frontend consumption:

RestaurantServlet  → /api/restaurants/*
  GET /api/restaurants?area=Gulshan          → list restaurants in area
  GET /api/restaurants/{id}                  → restaurant details
  GET /api/restaurants/{id}/menu             → full menu
  POST /api/restaurants/register             → register new restaurant (JSON body)

MenuServlet  → /api/menu/*
  POST /api/menu/categories                  → add category
  POST /api/menu/items                       → add menu item
  PATCH /api/menu/items/{id}/availability    → toggle availability
  PATCH /api/menu/items/{id}/stock           → update stock

OrderServlet  → /api/orders/*
  POST /api/orders                           → place order
  GET  /api/orders/{id}                      → order details
  GET  /api/orders/customer/{customerId}     → orders by customer
  PATCH /api/orders/{id}/status             → update status

UserServlet  → /api/users/*
  POST /api/users/register                   → register user
  POST /api/users/login                      → login

CouponServlet  → /api/coupons/*
  POST /api/coupons                          → create coupon
  GET  /api/coupons                          → list all coupons
  GET  /api/coupons/validate?code=X&subtotal=Y → validate coupon

════════════════════════════════════════
WIRING / STARTUP
════════════════════════════════════════
Create a AppInitializer class implementing ServletContextListener 
(annotated @WebListener). On contextInitialized:
  1. Load AppConfig from app.properties
  2. Initialize DatabaseConnectionPool (singleton stored in ServletContext)
  3. Run SchemaInitializer to create tables if they don't exist
  4. Instantiate all DAOs, then all Services, store them in ServletContext
  
Servlets retrieve services from ServletContext attributes — no static state, 
no singletons except the connection pool.

════════════════════════════════════════
CONFIGURATION: src/main/resources/app.properties
════════════════════════════════════════
db.path=data/foodapp.db
db.pool.min=2
db.pool.max=10
app.name=FoodApp
app.version=1.0

════════════════════════════════════════
pom.xml DEPENDENCIES (provide exact versions)
════════════════════════════════════════
- jakarta.servlet-api 6.0.0 (scope: provided)
- apache-cxf-rt-frontend-jaxws 4.0.4
- apache-cxf-rt-transports-http 4.0.4
- jackson-databind 2.17.1
- xerial sqlite-jdbc 3.45.3.0
- junit-jupiter 5.10.2 (scope: test)
- tomcat10-maven-plugin (for mvn tomcat10:run)
- maven-compiler-plugin with source/target 23

════════════════════════════════════════
CODE QUALITY RULES (enforce throughout)
════════════════════════════════════════
1. Every public method has a Javadoc comment (purpose, params, returns, throws)
2. No magic strings — all string constants in AppConstants or enums
3. No System.out.println — use java.util.logging.Logger (one per class, static final)
4. Every resource (Connection, Statement, ResultSet) closed with try-with-resources
5. No raw types — fully generic collections everywhere
6. No null returns — use Optional<T> for nullable single values, 
   empty collections for empty lists
7. Meaningful variable names — never i, j, temp, data, obj
8. Methods longer than 30 lines should be decomposed
9. No checked exceptions leaking past the service layer — 
   wrap them in AppException subclasses before they reach servlets
10. All JDBC SQL strings declared as private static final constants 
    at the top of each DAO class

════════════════════════════════════════
WHAT NOT TO DO
════════════════════════════════════════
- Do NOT use Spring, Spring Boot, or any Spring dependency
- Do NOT use Lombok
- Do NOT use Hibernate or any ORM
- Do NOT use connection pool libraries (HikariCP etc.) — implement it manually
- Do NOT return null from any public method
- Do NOT swallow exceptions silently
- Do NOT put SQL in service classes
- Do NOT put business logic in DAO classes
- Do NOT put business logic in Servlets
- Do NOT use static mutable state
- Do NOT write a single class that handles more than one domain concept

════════════════════════════════════════
DELIVERABLES
════════════════════════════════════════
Produce all files with full content — no placeholders, no "// TODO implement". 
Every class must be complete and compilable. 

Start in this order:
1. pom.xml
2. app.properties + schema.sql
3. config/ classes (AppConstants, DatabaseConfig)
4. util/ classes (DatabaseConnectionPool, SchemaInitializer, PasswordHasher, 
   UUIDGenerator, Validator, JsonMapper, TimeUtil)
5. exception/ classes (all 7 exception types)
6. model/ classes (all POJOs + all enums)
7. dao/ classes (all 9 DAOs)
8. service/ classes (all 5 services)
9. api/soap/ (interfaces, implementations, DTOs, web.xml CXF config)
10. api/servlet/ (BaseServlet + all 5 servlets)
11. AppInitializer (ServletContextListener)
12. web.xml

The result should be a project that compiles cleanly with `mvn clean package` 
and runs with `mvn tomcat10:run` with zero errors.
```

