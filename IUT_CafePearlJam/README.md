# Cafe PearlJam

Cafe PearlJam is a food delivery system with a React frontend and a Java backend. Users can browse restaurants, place and track orders, and restaurant owners can manage menus and order status. The backend exposes both REST APIs for the frontend and SOAP/WSDL services per project requirements.

## System Architecture

```mermaid
flowchart LR
  FE[React Frontend<br/>http://localhost:5173] --> REST[REST API<br/>http://localhost:8080/foodapp/api/*]
  REST --> BE[Java Servlet Backend<br/>Tomcat on :8080]
  BE --> DB[(SQLite DB<br/>foodapp.db)]
  BE --> SOAP[SOAP/WSDL Layer<br/>http://localhost:8080/foodapp/ws]
```

## Tech Stack

| Layer | Technology | Purpose |
|---|---|---|
| Frontend | React + Vite + Tailwind | UI and client routing |
| Frontend State | TanStack Query + Zustand | Server state + cart state |
| Frontend HTTP | Axios | REST calls + envelope handling |
| Backend | Java Servlet + CXF + Maven Cargo | REST + SOAP runtime |
| Database | SQLite | Persistent relational storage |
| Protocols | REST/JSON + SOAP/WSDL | Client/backend integrations |

## Repository Structure

```text
IUT_CafePearlJam/
├─ PearlJam_Backend/         # Java backend (REST + SOAP, DB, schema)
├─ PearlJam_Frontend/        # React frontend app
├─ project_requirements.txt  # Original project requirements
└─ README.md                 # Master system guide
```

## How This Matches Requirements
- Supports user-side flow: browse/search/sort restaurants, menu selection, order placement, coupon in payload, order tracking, payment simulation.
- Supports restaurant-side flow: registration, menu category/item create, availability + stock management, order status updates, rider assignment.
- Exposes SOAP/WSDL endpoints as required at `/foodapp/ws/*`.
- Follows modular package/component design and documented structure.

## Run Full System

### Prerequisites
- Java 23+
- Maven 3.9+
- Node.js 18+ (or newer LTS)
- npm 9+

### 1) Clone
```bash
git clone <your-repo-url>
cd IUT_CafePearlJam
```

### 2) Start Backend
```bash
cd PearlJam_Backend
mvn package cargo:run
```

### 3) Start Frontend
```bash
cd ../PearlJam_Frontend
npm install
npm run dev
```

### 4) Verify Running URLs
- Frontend: `http://localhost:5173`
- Backend base: `http://localhost:8080/foodapp`
- REST sample: `http://localhost:8080/foodapp/api/restaurants`
- SOAP service index: `http://localhost:8080/foodapp/ws`
- WSDLs:
  - `http://localhost:8080/foodapp/ws/users?wsdl`
  - `http://localhost:8080/foodapp/ws/restaurants?wsdl`
  - `http://localhost:8080/foodapp/ws/menu?wsdl`
  - `http://localhost:8080/foodapp/ws/orders?wsdl`

## REST API Quick Reference

| Method | Path | Purpose |
|---|---|---|
| GET | `/api/restaurants` | List/search/sort restaurants |
| GET | `/api/restaurants/{id}/menu` | Get restaurant menu |
| POST | `/api/orders` | Place order (supports coupon code) |
| GET | `/api/orders/{id}/status` | Track order status |
| PATCH | `/api/orders/{id}/status` | Update order status |
| PATCH | `/api/orders/{id}/payment` | Simulated payment processing |
| PATCH | `/api/orders/{id}/assign-rider` | Assign rider (simulated) |

## SOAP/WSDL Quick Reference

| WSDL Endpoint | Service | Operations |
|---|---|---|
| `/ws/users?wsdl` | UserService | registerUser, login |
| `/ws/restaurants?wsdl` | RestaurantQueryService | getRestaurantsByArea, searchRestaurants, getRestaurantDetails, getDeliveryZones |
| `/ws/menu?wsdl` | MenuQueryService | getMenu, searchMenuItems, getItemAddons |
| `/ws/orders?wsdl` | OrderService | placeOrder, getOrderStatus, getOrdersByCustomer, cancelOrder |

## User Flow

```mermaid
flowchart LR
  A[Browse Restaurants] --> B[Select Restaurant]
  B --> C[Choose Menu Items]
  C --> D[Apply Coupon + Place Order]
  D --> E[Payment Simulation]
  E --> F[Track Status]
```

## Restaurant Owner Flow

```mermaid
flowchart LR
  A[Register Restaurant] --> B[Create Categories/Items]
  B --> C[Toggle Availability/Stock]
  C --> D[Receive Order ID]
  D --> E[Update Status]
  E --> F[Assign Rider]
```

## Database Schema

```mermaid
erDiagram
  USERS ||--o{ RESTAURANTS : owns
  RESTAURANTS ||--o{ MENU_CATEGORIES : has
  RESTAURANTS ||--o{ MENU_ITEMS : has
  MENU_CATEGORIES ||--o{ MENU_ITEMS : groups
  MENU_ITEMS ||--o{ MENU_ITEM_ADDONS : has
  USERS ||--o{ ORDERS : places
  RESTAURANTS ||--o{ ORDERS : receives
  ORDERS ||--o{ ORDER_ITEMS : contains
  MENU_ITEMS ||--o{ ORDER_ITEMS : references
  RESTAURANTS ||--o{ DELIVERY_ZONES : serves
```

## GitHub
- [github.com/safwansatil](https://github.com/safwansatil)