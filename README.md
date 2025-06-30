Inventory Management Backend:
This is the Spring Boot–based backend for the Inventory Management System. It handles authentication (JWT-based), authorization (role-based), and inventory CRUD operations.

 Architecture Overview:
Framework: Spring Boot (REST API)
Authentication: JWT (JSON Web Token)
Authorization: Role-based (USER, ADMIN)
Database: JPA with H2 or MySQL
Security: Spring Security
Cross-Origin Resource Sharing: Configured to allow frontend (http://localhost:3000)
The server runs at http://localhost:8080

Workflow Overview:
A user registers via /api/auth/register. Their password is encrypted using BCrypt.
The user logs in via /api/auth/login. If credentials are valid, a JWT token is returned.
The frontend sends the JWT token in Authorization: Bearer <token> header.
Spring Security validates the token and grants access to protected endpoints.
Only users with the ADMIN role can add or delete inventory items.

Authentication:
POST /api/auth/register – Register a new user.
POST /api/auth/login – Login and receive JWT token.

Inventory (Protected):
GET /api/inventory – List all inventory items.
POST /api/inventory – Add a new item (ADMIN only).
DELETE /api/inventory/{id} – Delete item by ID (ADMIN only).

Technologies Used:
Spring Boot 3+
Spring Security
JWT (jjwt)
Spring Data JPA
BCrypt Password Encoder
H2/MySQL
Maven
