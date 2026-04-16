# Cafe PearlJam Frontend Guide

## Project Overview
This React frontend is the client app for Cafe PearlJam. It serves:
- **Users**: browse restaurants, view menu, manage cart, place order with coupon, track order status.
- **Restaurant owners**: register restaurant, create menu categories/items, manage item availability/stock, lookup orders, update order status, assign riders.

It connects to the backend REST API at:
- `http://localhost:8080/foodapp`

It does **not** call SOAP endpoints.

## Tech Stack
- **React 18**: UI framework
- **React Router**: route-level navigation
- **TanStack Query**: API query/mutation state
- **Axios**: HTTP client/interceptors
- **Zustand**: cart state persistence
- **Tailwind CSS** (+ Radix-based UI primitives): styling and reusable UI components
- **Vite**: development/build tooling

## Directory Structure and File Responsibilities

### Root
- `index.html`: SPA mount and page metadata
- `package.json`: frontend dependencies/scripts
- `vite.config.js`: Vite + `@` alias configuration
- `tailwind.config.js`: design tokens and utility extensions
- `postcss.config.js`: Tailwind + autoprefixer pipeline
- `.gitignore`: frontend ignore rules

### `src/`
- `main.jsx`: React entrypoint
- `App.jsx`: route map + protected route wrapper
- `styles/globals.css`: global styles and typography tokens
- `lib/utils.js`: `cn()` class merge helper

### `src/api`
- `client.js`: axios instance, auth header injection, `{status,data,message}` envelope unwrapping, centralized error normalization
- `users.js`: login/register
- `restaurants.js`: list/details/register restaurant
- `menu.js`: restaurant menu fetch and owner menu management actions
- `orders.js`: place order, get details/status, customer orders, status update, rider assignment, payment simulation

### `src/hooks`
- `useAuth.js`: login/logout/session persistence (`localStorage`)
- `useRestaurants.js`: restaurant list/detail queries
- `useMenu.js`: menu query
- `useOrder.js`: order-status polling query (15s interval)

### `src/store`
- `cartStore.js`: persisted cart state (items, quantities, subtotal)

### `src/components/layout`
- `Layout.jsx`: public layout shell
- `Navbar.jsx`: primary nav + auth/cart controls
- `Footer.jsx`: site footer (includes required GitHub link)
- `DashboardLayout.jsx`: owner dashboard shell
- `DashboardSidebar.jsx`: dashboard navigation

### `src/components/restaurant`
- `RestaurantCard.jsx`: listing card

### `src/components/menu`
- `MenuItemCard.jsx`: menu item display/add
- `AddonSelector.jsx`: item customization dialog

### `src/components/order`
- `OrderSummaryPanel.jsx`: cart/order totals
- `OrderStatusTracker.jsx`: status timeline visualization

### `src/components/common`
- `ErrorBoundary.jsx`: global runtime fallback
- `LoadingSpinner.jsx`: loading state UI
- `EmptyState.jsx`: empty/error placeholder
- `StatusBadge.jsx`: status pill mapping

### `src/components/ui`
- `Button.jsx`, `Input.jsx`, `Card.jsx`, `Badge.jsx`, `Dialog.jsx`, `Tabs.jsx`, `Separator.jsx`, `Checkbox.jsx`, `Label.jsx`: shared UI primitives

### `src/pages`
- `LandingPage.jsx`: hero/search entry
- `RestaurantListPage.jsx`: filter/search/sort restaurants
- `RestaurantDetailPage.jsx`: menu + add-to-cart flow
- `CheckoutPage.jsx`: address/area/coupon/payment/order placement
- `OrderTrackingPage.jsx`: live status tracking
- `Login.jsx`: user authentication
- `Register.jsx`: user registration

### `src/pages/dashboard`
- `OverviewPage.jsx`: restaurant registration/setup
- `MenuManagementPage.jsx`: category/item creation + availability/stock updates
- `OrdersPage.jsx`: order lookup + status update + rider assignment

## Routing Map
- `/` → `LandingPage` (public)
- `/restaurants` → `RestaurantListPage` (public)
- `/restaurant/:id` → `RestaurantDetailPage` (public)
- `/checkout` → `CheckoutPage` (authenticated user)
- `/order/:id` → `OrderTrackingPage` (authenticated user)
- `/login` → `Login` (public)
- `/register` → `Register` (public)
- `/dashboard` → `OverviewPage` (restaurant owner)
- `/dashboard/orders` → `OrdersPage` (restaurant owner)
- `/dashboard/menu` → `MenuManagementPage` (restaurant owner)

## API Service Layer Mapping
- `login(email,password)` → `POST /api/users/login`
- `register(userData)` → `POST /api/users/register`
- `getRestaurants({area,query,sort})` → `GET /api/restaurants`
- `getRestaurantById(id)` → `GET /api/restaurants/{id}`
- `registerRestaurant(payload)` → `POST /api/restaurants/register`
- `getMenuByRestaurant(id)` → `GET /api/restaurants/{id}/menu`
- `addMenuCategory(payload)` → `POST /api/menu/categories`
- `addMenuItem(payload)` → `POST /api/menu/items`
- `setMenuItemAvailability(id,available)` → `PATCH /api/menu/items/{id}/availability`
- `updateMenuItemStock(id,quantity)` → `PATCH /api/menu/items/{id}/stock`
- `placeOrder(payload)` → `POST /api/orders`
- `getOrderById(id)` → `GET /api/orders/{id}`
- `getOrderStatus(id)` → `GET /api/orders/{id}/status`
- `getOrdersByCustomer(customerId)` → `GET /api/orders/customer/{customerId}`
- `updateOrderStatus(id,status,actorId)` → `PATCH /api/orders/{id}/status`
- `assignRider(id,riderId)` → `PATCH /api/orders/{id}/assign-rider`
- `processPayment(id)` → `PATCH /api/orders/{id}/payment`

## State Management
- **Auth/session**: `useAuth`, persisted in `localStorage` key `pearljam-user`
- **Cart**: Zustand persisted store `pearljam-cart-storage`
- **Server state**: TanStack Query per domain hook/query key

## Auth Flow
1. User logs in/registers from `Login`/`Register`.
2. `useAuth` stores returned user object (including token) in localStorage.
3. Axios request interceptor reads token from `pearljam-user` and sets `Authorization`.
4. Protected routes gate checkout, tracking, and owner dashboard pages.

## How to Run
1. Open frontend directory:
   - `cd PearlJam_Frontend`
2. Install dependencies:
   - `npm install`
3. Start development server:
   - `npm run dev`
4. Open shown Vite URL (usually `http://localhost:5173`).

## Environment Variables
- `VITE_API_BASE_URL` (optional): base backend URL (default `http://localhost:8080/foodapp`)

## Known Limitations
- Backend currently has no direct “list orders by restaurant” endpoint in REST docs; dashboard order view is implemented as order-id lookup + action workflow.
- Coupon validation is backend-applied during order placement (no pre-validation endpoint is documented).
- Owner menu management supports create + availability/stock updates via backend-supported endpoints (no documented REST delete/edit endpoint for full item mutation).
