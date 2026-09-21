# Smart Inventory Management System with Predictive Restocking

A web-based inventory management system that tracks stock movements as transactions rather than a static count, enabling full audit history. The system predicts potential stockouts using historical consumption trends and enforces a role-based approval workflow for stock removal, ensuring changes go through proper authorization.

## Tech Stack

- **Backend:** Java, Spring Boot, Spring Security (JWT)
- **Frontend:** React.js
- **Database:** MySQL
- **API:** REST

## Features

- Product and category management (CRUD)
- Stock-in / stock-out tracking via transaction log (not a static quantity field)
- Real-time current stock calculation from transaction history
- Low-stock alerts based on reorder level
- Consumption-rate based stockout prediction
- Role-based approval workflow (Staff requests → Admin approves/rejects)
- JWT-based authentication and role-based access control

## Setup Instructions

### Backend (Spring Boot)
```bash
cd backend
# Configure MySQL connection in src/main/resources/application.properties
mvn spring-boot:run
```

### Frontend (React)
```bash
cd frontend
npm install
npm start
```

### Database
- Create a MySQL database named `inventory_db`
- Update credentials in `application.properties`
- Tables are auto-created via JPA on first run

## Project Status
 In development.



[Shravani Chavan] —  Project, 2026
