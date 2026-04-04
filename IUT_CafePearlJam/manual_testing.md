# Manual Testing Guide (PearlJam Lean MVP)

## Running the Project

### 1. Backend (Java)
- **Directory**: `PearlJam_Backend`
- **Command**: `mvn clean package cargo:run` (Use `mvn cargo:run` if already packaged)
- **Base URL**: `http://localhost:8080/foodapp`
- **API Base**: `http://localhost:8080/foodapp/api`
- **SOAP Base**: `http://localhost:8080/foodapp/ws`

### 2. Frontend (React/Vite)
- **Directory**: `PearlJam_Frontend`
- **Command**: `npm install && npm run dev`
- **Base URL**: `http://localhost:5173` (or `5174` if port is in use)

---

## Core MVP Workflows to Test

### A. Customer Flow
1. **Registration**: Navigate to `/register`. Register as a `CUSTOMER`.
2. **Login**: Navigate to `/login`. Use the registered credentials.
3. **Restaurant Discovery**: Browse the home/restaurant list and select a restaurant.
4. **Menu & Cart**: View categories, add items to cart, select add-ons (if available).
5. **Checkout**: Proceed to Checkout. Enter address and choose payment (CASH).
6. **Order History**: Verify the order appears in your user profile/orders page.

### B. Restaurant Owner Flow
1. **Registration**: Register as a `RESTAURANT_OWNER`.
2. **Approval (Manual DB Check)**: New restaurants are default `PENDING`. You may need to manually update the `restaurants` table `is_approved = 1` in `food_app.db` (SQLite) if the Admin UI is incomplete.
3. **Menu Management**:
   - Add/Edit categories.
   - Add/Edit/Delete menu items.
   - Toggle availability for items and addons.
4. **Order Management**:
   - View incoming orders for your restaurant.
   - Update status: `PENDING` -> `PREPARING` -> `READY`.
   - Verify changes reflect for the Customer.

---

## Backend Services Audit

### SOAP/WSDL Endpoints
| Service | WSDL URL | Status |
|---|---|---|
| User | `http://localhost:8080/foodapp/ws/users?wsdl` | Working |
| Restaurant | `http://localhost:8080/foodapp/ws/restaurants?wsdl` | Working |
| Menu | `http://localhost:8080/foodapp/ws/menu?wsdl` | Working |
| Order | `http://localhost:8080/foodapp/ws/orders?wsdl` | Working |

### Recent Critical Fixes
1. **DB Connection Leak Resolution**: All DAOs (`UserDAO`, `RestaurantDAO`, `MenuCategoryDAO`, `MenuItemDAO`, `OrderItemDAO`, `OrderDAO`, `MenuItemAddonDAO`, `DeliveryZoneDAO`) have been refactored to use robust `try-finally` blocks. Connections are now reliably released back to the `DatabaseConnectionPool` even on errors.
2. **Order Constructor Fix**: Resolved a compilation error in `OrderService.java` where the `Order` constructor was called with an extra (deleted) `riderId` argument.
3. **Registration Logic Fix**: Fixed `AppException` argument order in `UserServlet.java` to match the model signature `(String message, int statusCode)`.

---

## Scope Limitations (Removed Features)
- **No Rider App**: Delivery rider flow and tracking have been removed to prioritize core stability.
- **No Coupons**: The coupon/discount system was deleted as per MVP "Lean" requirements.
- **SQLite DB**: Project uses a local `food_app.db` file. If connectivity issues persist, delete this file to force a schema reset.
