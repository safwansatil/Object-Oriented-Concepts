-- Users table
CREATE TABLE IF NOT EXISTS users (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    phone TEXT NOT NULL,
    role TEXT CHECK(role IN ('CUSTOMER', 'RESTAURANT_OWNER')) NOT NULL,
    created_at TEXT NOT NULL,
    is_active INTEGER DEFAULT 1
);

-- Restaurants table
CREATE TABLE IF NOT EXISTS restaurants (
    id TEXT PRIMARY KEY,
    owner_id TEXT NOT NULL,
    name TEXT NOT NULL,
    description TEXT,
    address TEXT NOT NULL,
    area TEXT NOT NULL,
    latitude REAL,
    longitude REAL,
    phone TEXT NOT NULL,
    cuisine_type TEXT NOT NULL,
    is_active INTEGER DEFAULT 1,
    opens_at TEXT NOT NULL,
    closes_at TEXT NOT NULL,
    created_at TEXT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Menu categories table
CREATE TABLE IF NOT EXISTS menu_categories (
    id TEXT PRIMARY KEY,
    restaurant_id TEXT NOT NULL,
    name TEXT NOT NULL,
    display_order INTEGER NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

-- Menu items table
CREATE TABLE IF NOT EXISTS menu_items (
    id TEXT PRIMARY KEY,
    category_id TEXT NOT NULL,
    restaurant_id TEXT NOT NULL,
    name TEXT NOT NULL,
    description TEXT,
    base_price REAL NOT NULL,
    image_url TEXT,
    is_available INTEGER DEFAULT 1,
    track_quantity INTEGER DEFAULT 0,
    quantity_in_stock INTEGER,
    preparation_time_minutes INTEGER,
    created_at TEXT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES menu_categories(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

-- Menu item addons table
CREATE TABLE IF NOT EXISTS menu_item_addons (
    id TEXT PRIMARY KEY,
    menu_item_id TEXT NOT NULL,
    name TEXT NOT NULL,
    extra_price REAL NOT NULL,
    is_available INTEGER DEFAULT 1,
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id TEXT PRIMARY KEY,
    customer_id TEXT NOT NULL,
    restaurant_id TEXT NOT NULL,
    delivery_address TEXT NOT NULL,
    delivery_area TEXT NOT NULL,
    subtotal REAL NOT NULL,
    delivery_fee REAL NOT NULL,
    discount_amount REAL DEFAULT 0.0,
    total REAL NOT NULL,
    status TEXT CHECK(status IN ('PENDING','CONFIRMED','PREPARING','READY_FOR_PICKUP','OUT_FOR_DELIVERY','DELIVERED','CANCELLED')) NOT NULL,
    payment_status TEXT CHECK(payment_status IN ('UNPAID','PAID','REFUNDED')) NOT NULL,
    payment_method TEXT NOT NULL,
    special_instructions TEXT,
    placed_at TEXT NOT NULL,
    confirmed_at TEXT,
    delivered_at TEXT,
    FOREIGN KEY (customer_id) REFERENCES users(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

-- Order items table
CREATE TABLE IF NOT EXISTS order_items (
    id TEXT PRIMARY KEY,
    order_id TEXT NOT NULL,
    menu_item_id TEXT NOT NULL,
    item_name TEXT NOT NULL,
    item_price REAL NOT NULL,
    quantity INTEGER NOT NULL,
    selected_addons TEXT,
    item_total REAL NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);


-- Delivery zones table
CREATE TABLE IF NOT EXISTS delivery_zones (
    id TEXT PRIMARY KEY,
    restaurant_id TEXT NOT NULL,
    area_name TEXT NOT NULL,
    delivery_fee REAL NOT NULL,
    estimated_minutes INTEGER NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

-- Indexes
CREATE INDEX IX_restaurants_area ON restaurants(area);
CREATE INDEX IX_restaurants_owner ON restaurants(owner_id);
CREATE INDEX IX_menu_categories_restaurant ON menu_categories(restaurant_id);
CREATE INDEX IX_menu_items_category ON menu_items(category_id);
CREATE INDEX IX_menu_items_restaurant ON menu_items(restaurant_id);
CREATE INDEX IX_menu_item_addons_item ON menu_item_addons(menu_item_id);
CREATE INDEX IX_orders_customer ON orders(customer_id);
CREATE INDEX IX_orders_restaurant ON orders(restaurant_id);
CREATE INDEX IX_orders_status ON orders(status);
CREATE INDEX IX_order_items_order ON order_items(order_id);
CREATE INDEX IX_delivery_zones_restaurant ON delivery_zones(restaurant_id);
CREATE INDEX IX_delivery_zones_area ON delivery_zones(area_name);
