You are building the frontend for "PearlJam", a food delivery web application.
The backend is a Java + Jakarta EE application running on Tomcat at 
http://localhost:8080. It exposes REST-like JSON endpoints at /api/* and 
SOAP endpoints at /ws/*.

Your job is to set up the complete frontend project directory and implement 
all 6 pages faithfully from the Stitch designs provided.

════════════════════════════════════════
TECH STACK DECISION
════════════════════════════════════════
Use: React 18 + Vite 5 + Tailwind CSS 3 + shadcn/ui

Rationale: The design is component-heavy (cards, tabs, toggles, modals, 
step trackers). shadcn/ui provides unstyled-but-structured primitives 
that we fully restyle to match the design system. Do NOT use shadcn/ui 
default styles — override everything to match the design.

Additional libraries (add only these, no others without justification):
  - react-router-dom v6          (routing)
  - axios                        (HTTP client for backend calls)
  - @tanstack/react-query v5     (server state, caching, loading states)
  - react-hook-form              (form handling)
  - zod                          (schema validation, paired with react-hook-form)
  - lucide-react                 (icons only — no other icon library)
  - clsx + tailwind-merge        (conditional class utilities)

════════════════════════════════════════
PROJECT STRUCTURE (strict — do not deviate)
════════════════════════════════════════
pearljam-frontend/
├── public/
│   └── favicon.svg
├── src/
│   ├── api/                  ← All backend communication
│   │   ├── client.js         ← Axios instance with base URL + interceptors
│   │   ├── restaurants.js    ← Restaurant API calls
│   │   ├── menu.js           ← Menu API calls
│   │   ├── orders.js         ← Order API calls
│   │   ├── users.js          ← Auth API calls
│   │   └── coupons.js        ← Coupon API calls
│   ├── components/
│   │   ├── ui/               ← shadcn/ui primitives (Button, Input, Badge, 
│   │   │                        Card, Tabs, Toggle, Dialog, etc.)
│   │   ├── layout/
│   │   │   ├── Navbar.jsx
│   │   │   ├── Footer.jsx
│   │   │   └── DashboardSidebar.jsx
│   │   ├── restaurant/
│   │   │   ├── RestaurantCard.jsx
│   │   │   ├── RestaurantHeader.jsx
│   │   │   └── RestaurantFilters.jsx
│   │   ├── menu/
│   │   │   ├── MenuCategoryTabs.jsx
│   │   │   ├── MenuItemCard.jsx
│   │   │   └── AddonSelector.jsx
│   │   ├── order/
│   │   │   ├── OrderSummaryPanel.jsx
│   │   │   ├── OrderStatusTracker.jsx
│   │   │   └── OrderItemRow.jsx
│   │   └── common/
│   │       ├── StatusBadge.jsx
│   │       ├── LoadingSpinner.jsx
│   │       ├── EmptyState.jsx
│   │       └── ErrorBoundary.jsx
│   ├── pages/
│   │   ├── LandingPage.jsx
│   │   ├── RestaurantListPage.jsx
│   │   ├── RestaurantDetailPage.jsx
│   │   ├── CheckoutPage.jsx
│   │   ├── OrderTrackingPage.jsx
│   │   └── dashboard/
│   │       ├── DashboardLayout.jsx
│   │       ├── OverviewPage.jsx
│   │       ├── OrdersPage.jsx
│   │       └── MenuManagementPage.jsx
│   ├── hooks/
│   │   ├── useRestaurants.js
│   │   ├── useMenu.js
│   │   ├── useOrder.js
│   │   └── useAuth.js
│   ├── store/
│   │   └── cartStore.js      ← Cart state (Zustand — add this dependency)
│   ├── lib/
│   │   └── utils.js          ← cn() helper (clsx + twMerge)
│   ├── styles/
│   │   └── globals.css       ← Tailwind directives + CSS variables
│   ├── App.jsx
│   └── main.jsx
├── .env.example
├── .gitignore
├── index.html
├── tailwind.config.js
├── vite.config.js
└── package.json

════════════════════════════════════════
DESIGN SYSTEM — implement exactly
════════════════════════════════════════
In tailwind.config.js, extend the theme with these exact tokens:

colors:
  background: '#FAF8F4'
  surface:    '#F0EDE6'
  border:     '#E2DDD5'
  text:
    primary: '#1A1A18'
    muted:   '#6B6760'
  accent:
    DEFAULT: '#2D5016'
    hover:   '#3D6B1F'
    light:   '#EAF0E4'   ← light green tint for selected states
  danger:    '#C0392B'
  white:     '#FFFFFF'

  status:
    pending:    '#D97706'   (amber)
    confirmed:  '#2563EB'   (blue)
    preparing:  '#7C3AED'   (purple)
    ready:      '#0D9488'   (teal)
    delivering: '#EA580C'   (orange)
    delivered:  '#2D5016'   (green — same as accent)
    cancelled:  '#C0392B'   (red)

fontFamily:
  sans:  ['Inter', 'sans-serif']
  serif: ['Fraunces', 'Playfair Display', 'serif']

borderRadius:
  card:   '12px'
  pill:   '100px'
  input:  '8px'

In globals.css, declare these CSS custom properties (for shadcn/ui 
overrides) and import Inter + Fraunces from Google Fonts.

════════════════════════════════════════
API CLIENT SETUP (src/api/client.js)
════════════════════════════════════════
- Axios instance with baseURL from import.meta.env.VITE_API_BASE_URL
- Default: http://localhost:8080
- Request interceptor: attach auth token from localStorage if present
- Response interceptor: 
    on 401 → clear localStorage, redirect to /login
    on 5xx → throw a normalized AppError with message + status
- Export as default

════════════════════════════════════════
CART STATE (src/store/cartStore.js)
════════════════════════════════════════
Use Zustand. Cart state must hold:
  restaurantId, restaurantName, items: [{ menuItemId, name, basePrice, 
  quantity, selectedAddons: [{id, name, extraPrice}] }]

Actions:
  addItem(item)         — if different restaurant, prompt user then clear
  removeItem(menuItemId)
  updateQuantity(menuItemId, qty)
  clearCart()
  getSubtotal()         — computed: sum of (basePrice + addon prices) × qty
  getItemCount()        — total qty across all items

Persist cart to localStorage so it survives page refresh.

════════════════════════════════════════
CUSTOM HOOKS
════════════════════════════════════════
useRestaurants(area):
  Uses react-query. Fetches GET /api/restaurants?area={area}
  Returns { restaurants, isLoading, error }

useMenu(restaurantId):
  Fetches GET /api/restaurants/{id}/menu
  Returns { menuByCategory, isLoading, error }
  menuByCategory is Map<categoryName, MenuItem[]> — preserve order

useOrder(orderId):
  Fetches GET /api/orders/{id}, refetches every 15s while status is 
  not DELIVERED or CANCELLED (polling for order tracking)
  Returns { order, isLoading, error }

useAuth():
  Reads user from localStorage
  Returns { user, login(email,password), logout(), isAuthenticated }
  login() calls POST /api/users/login, stores token + user in localStorage

════════════════════════════════════════
PAGE IMPLEMENTATIONS
════════════════════════════════════════
Implement every page completely — no placeholder components, 
no "coming soon". Each page must:
  1. Handle loading state (show LoadingSpinner)
  2. Handle error state (show EmptyState with retry)
  3. Handle empty data (show EmptyState with helpful message)
  4. Be fully responsive (mobile-first)

LandingPage.jsx:
  - Address search bar (controlled input, stores to sessionStorage)
  - On submit: navigate to /restaurants?area={input}
  - Cuisine chip row: clicking a chip navigates to 
    /restaurants?area=current&cuisine={type}
  - "How it works" section with 3 steps

RestaurantListPage.jsx:
  - Reads area from URL query param
  - Left filter sidebar + restaurant card grid
  - Filter state in URL params (so filters are shareable/bookmarkable)
  - Search is debounced (300ms) before firing API call
  - Each RestaurantCard links to /restaurants/{id}

RestaurantDetailPage.jsx:
  - Loads restaurant + full menu
  - Category tabs are sticky below the hero banner
  - Scrollspy: as user scrolls through sections, active tab updates
  - "+ " button opens an AddonSelector modal if item has addons, 
    otherwise adds directly to cart
  - Order summary panel is sticky on desktop, 
    becomes a fixed bottom bar on mobile showing item count + subtotal

CheckoutPage.jsx:
  - Protected route — redirect to /login if not authenticated
  - Shows cart items (editable qty, removable)
  - Delivery address input (pre-fills from user profile if available)
  - Payment method selector (3 options as described)
  - Coupon field: on apply, calls GET /api/coupons/validate?code=X&subtotal=Y
    Shows success (green) or error (red) inline
  - On submit: POST /api/orders, then navigate to /orders/{id}/tracking

OrderTrackingPage.jsx:
  - Polls order status every 15 seconds (via useOrder hook)
  - Vertical step tracker — completed steps are solid green, 
    active step has a pulsing dot animation (CSS keyframes)
  - Shows timestamp for each completed step
  - Page title updates to reflect current status

Dashboard pages (DashboardLayout wraps all):
  OverviewPage: stats cards + active orders table + menu toggle list
  OrdersPage: full orders table with status filter tabs + action buttons
  MenuManagementPage: category accordion with item list, 
                      add/edit item form in a slide-over panel

════════════════════════════════════════
COMPONENT RULES
════════════════════════════════════════
StatusBadge.jsx:
  Takes status prop (OrderStatus enum string)
  Returns a styled pill with correct color from the status color map.
  Never hardcode colors inline — use Tailwind classes mapped from status.

RestaurantCard.jsx:
  Takes restaurant object as prop.
  Shows open/closed status based on opensAt/closesAt fields.
  "Closed" state: card is slightly desaturated (CSS filter), 
  overlay text "Closed", not clickable.

AddonSelector.jsx:
  Modal/dialog component.
  Lists addon groups, each addon as a checkbox.
  Shows running price total as addons are selected.
  "Add to order" button updates cart and closes modal.

════════════════════════════════════════
CODE QUALITY RULES
════════════════════════════════════════
1. Every component is a named function export, not arrow function default
2. PropTypes OR JSDoc @param annotations on every component
3. No inline styles — Tailwind classes only
4. No hardcoded strings — all UI text in a constants/strings.js file
5. API error messages never exposed raw to the user — 
   map them to friendly messages
6. Loading and error states are never null — always render something
7. No console.log in final code
8. All async operations wrapped in try/catch
9. Components under 150 lines — decompose if longer
10. File names: PascalCase for components, camelCase for hooks/utils

════════════════════════════════════════
.env.example
════════════════════════════════════════
VITE_API_BASE_URL=http://localhost:8080
VITE_APP_NAME=PearlJam

════════════════════════════════════════
DELIVERABLES
════════════════════════════════════════
1. Complete package.json with all dependencies and scripts:
   dev, build, preview, lint
2. All config files: vite.config.js, tailwind.config.js, 
   postcss.config.js, .gitignore, .env.example
3. Every file in the structure above — complete, no placeholders
4. The app must run cleanly with: npm install && npm run dev
5. No TypeScript errors (or use JSX throughout — pick one and be consistent)

Start in this order:
1. package.json + all config files
2. globals.css + tailwind.config.js (design tokens first)
3. src/lib/utils.js + src/store/cartStore.js
4. src/api/ (all API modules)
5. src/hooks/ (all custom hooks)
6. src/components/ui/ (shadcn primitives, restyled)
7. src/components/common/ + layout/
8. src/components/restaurant/ + menu/ + order/
9. src/pages/ (all pages)
10. src/App.jsx + src/main.jsx