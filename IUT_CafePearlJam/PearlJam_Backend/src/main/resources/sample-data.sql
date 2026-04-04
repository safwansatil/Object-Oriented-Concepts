-- Sample Users (Passwords are pre-hashed: SALT:HASH for 'password123')
-- Salt '8f2d', SHA-256(8f2dpassword123) = 6f8e74720e0609ad8a3818e38d4f40d62c2f6d8995a9478f77361730d8a56b78 (example)
-- For simplicity in this demo, let's use: SALT:FIXED_HASH
INSERT INTO users (id, name, email, password_hash, phone, role, created_at) VALUES 
('u-001', 'Jane Doe', 'jane@example.com', 'S123:6f8e74720e0609ad8a3818e38d4f40d62c2f6d8995a9478f77361730d8a56b78', '01711223344', 'CUSTOMER', '2026-03-31T10:00:00Z'),
('u-002', 'Chef Satil', 'chef@pearljam.com', 'S456:6f8e74720e0609ad8a3818e38d4f40d62c2f6d8995a9478f77361730d8a56b78', '01855667788', 'RESTAURANT_OWNER', '2026-03-31T10:00:00Z');

-- Sample Restaurant
INSERT INTO restaurants (id, owner_id, name, description, address, area, latitude, longitude, phone, cuisine_type, opens_at, closes_at, created_at) VALUES
('r-001', 'u-002', 'Pearl Jam Cafe', 'The finest burgers in the city.', 'Plot 12, Road 5, Gulshan 2', 'Gulshan', 23.7946, 90.4043, '01911222333', 'American', '09:00', '22:00', '2026-03-31T11:00:00Z');

-- Sample Delivery Zone
INSERT INTO delivery_zones (id, restaurant_id, area_name, delivery_fee, estimated_minutes) VALUES
('dz-001', 'r-001', 'Gulshan', 50.0, 30);

-- Sample Menu Categories
INSERT INTO menu_categories (id, restaurant_id, name, display_order) VALUES
('c-001', 'r-001', 'Burgers', 1),
('c-002', 'r-001', 'Drinks', 2);

-- Sample Menu Items
INSERT INTO menu_items (id, category_id, restaurant_id, name, description, base_price, image_url, is_available, track_quantity, quantity_in_stock, preparation_time_minutes, created_at) VALUES
('mi-001', 'c-001', 'r-001', 'Classic Beef Burger', 'Juicy beef patty with lettuce and tomato.', 250.0, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd', 1, 1, 50, 15, '2026-03-31T12:00:00Z'),
('mi-002', 'c-001', 'r-001', 'Double Cheese Burger', 'Two patties with extra cheddar.', 350.0, 'https://images.unsplash.com/photo-1594212699903-ec8a3eca50f5', 1, 1, 30, 20, '2026-03-31T12:00:00Z'),
('mi-003', 'c-002', 'r-001', 'Coca Cola', 'Chilled 250ml can.', 40.0, 'https://images.unsplash.com/photo-1622483767028-3f66f32a557e', 1, 1, 100, 2, '2026-03-31T12:00:00Z'),
('mi-004', 'c-002', 'r-001', 'Mineral Water', '500ml fresh water.', 20.0, 'https://images.unsplash.com/photo-1523362628745-0c100150b504', 1, 0, 0, 1, '2026-03-31T12:00:00Z');

-- Sample Addons
INSERT INTO menu_item_addons (id, menu_item_id, name, extra_price, is_available) VALUES
('ad-001', 'mi-001', 'Extra Cheese', 30.0, 1),
('ad-002', 'mi-001', 'Grilled Bacon', 70.0, 1);

-- Sample Coupon
INSERT INTO coupons (id, code, description, discount_type, discount_value, minimum_order_value, max_uses, expires_at, is_active) VALUES
('cp-001', 'FIRST50', '50% off for first order', 'PERCENTAGE', 50.0, 200.0, 100, '2026-12-31T23:59:59Z', 1);

-- Sample Order (Pending)
INSERT INTO orders (id, customer_id, restaurant_id, delivery_address, delivery_area, subtotal, delivery_fee, discount_amount, total, status, payment_status, payment_method, placed_at) VALUES
('o-001', 'u-001', 'r-001', 'House 4, Road 1, Gulshan 1', 'Gulshan', 250.0, 50.0, 0.0, 300.0, 'PENDING', 'UNPAID', 'CASH_ON_DELIVERY', '2026-04-01T11:00:00Z');

-- Sample Order Item
INSERT INTO order_items (id, order_id, menu_item_id, item_name, item_price, quantity, selected_addons, item_total) VALUES
('oi-001', 'o-001', 'mi-001', 'Classic Beef Burger', 250.0, 1, 'None', 250.0);
