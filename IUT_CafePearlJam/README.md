<div align="center">

# 🍱 PearlJam
### Food Delivery Application

*3rd Semester Software Engineering Project — Object Oriented Concepts II*

![Java](https://img.shields.io/badge/Java-23-ED8B00?style=for-the-badge&logo=openjdk)
![Vite](https://img.shields.io/badge/Frontend-React_Vite-646CFF?style=for-the-badge&logo=vite)
![SOAP](https://img.shields.io/badge/API-SOAP%20/%20JAX--WS-43682b?style=for-the-badge)
![License](https://img.shields.io/badge/License-Academic-blue?style=for-the-badge)

</div>

---

## 🎯 About The Project
PearlJam is a high-impact, full-stack food delivery application built with a focus on clean, idiomatic Java and modern web design. Inspired by "Editorial Organicism," the UI offers a curated, magazine-like experience for users to discover and order from local kitchens.

This project was developed for the **Object Oriented Concepts II** course (3rd Semester Software Engineering). It demonstrates advanced Java OOP principles, JAX-WS SOAP service implementation, and a robust layered architecture without the use of Spring Boot.

---

## ✨ Features

### 👤 Customer Features
- **Restaurant Discovery:** Search for nearby kitchens by delivery area.
- **Refined Search:** Browse by cuisine (Italian, Japanese, Artisanal, etc.) or restaurant name.
- **Menu Curation:** View dishes with descriptions, pricing, and availability.
- **Smart Checkout:** Apply coupon codes (WELCOME10) and manage cart quantities.
- **Order Tracking:** Monitor order state from PENDING to DELIVERED.

### Restaurant Features
- **Menu Management:** Add/Edit/Delete categories and items.
- **Inventory Control:** Real-time stock tracking and availability toggling.
- **Order Dashboard:** Track incoming orders and update their status.
- **Schedule Management:** Set opening and closing hours.

---

##  Project Architecture

### Package Structure
The backend follows a strict one-class-per-functionality architectural pattern:
```
com.foodapp/
├── api/             # API Layer (SOAP & REST-like Servlets)
├── service/         # Pure Business Logic
├── dao/             # JDBC Data Access Layer (SQLite)
├── model/           # Domain Entities (POJOs)
├── util/            # Helpers (Connection Pool, Hashers, etc.)
├── config/          # App Configuration
└── exception/       # Custom Exception Hierarchy
```

### Tech Stack
| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Backend** | Java 23 | Application logic |
| **API** | Apache CXF / JAX-WS | SOAP service exposure |
| **Frontend** | React / Vite / Tailwind | User interface |
| **Data** | SQLite | Persistence |
| **Build** | Maven | Dependency management |

---

## 🚀 Getting Started

### Prerequisites
- **JDK 23**
- **Maven 3.9+**
- **Node.js 20+**

### Installation

**1. Clone the repository**
```bash
git clone <repository-url>
cd IUT_CafePearlJam
```

**2. Backend Setup**
```bash
cd PearlJam_Backend
# Build the project
mvn clean package
# Run the embedded Tomcat server
mvn tomcat10:run
```
The backend will be available at `http://localhost:8080/`.

**3. Frontend Setup**
```bash
cd ../PearlJam_Frontend
# Install dependencies
npm install
# Run the dev server
npm run dev
```
Open `http://localhost:5173` in your browser.

---

## 📡 API Reference

PearlJam exposes five primary SOAP services:

- **UserService:** Handles registration, login, and profile management.
- **OrderService:** Manages placement, status updates, and tracking of orders.
- **MenuQueryService:** Retrieves restaurant menus and item details.
- **RestaurantQueryService:** Provides kitchen discovery and metadata.
- **CouponService:** Validates and lists discount coupons.

### Example SOAP Request (Restaurant Search)
```xml
<ws:getRestaurantsByArea xmlns:ws="http://foodapp.com/ws">
   <area>Gulshan</area>
</ws:getRestaurantsByArea>
```

---

## 🎨 Design System
PearlJam's UI  adheres to a **"Digital Greenhouse"** aesthetic with a forest green palette and Newsreader serif headings.


---

*Built with ☕ Java and determination as a 3rd Semester Software Engineering student.*
