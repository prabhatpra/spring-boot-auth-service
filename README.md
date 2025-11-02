# 🔐 Spring Boot Authentication Service

A Spring Boot based authentication & authorization microservice providing secure user registration, login (JWT), and role-based access control.  
This repo is actively maintained — new features (password recovery, social login) are planned and will be added here.

---

## 🚀 Current Features (Implemented)
- User registration (signup) with validation
- User login with JWT token generation
- Token validation and expiry handling
- Password hashing with BCrypt
- Global exception handling and standardized API responses
- Role-based endpoints (admin/user)
- Clean package structure and unit tests (basic)

---

## 🔭 Planned / Upcoming Features
These are in the roadmap and will be implemented soon:
- **Forgot Password** — send reset link/email, change password flow
- **Forgot Username** — send username reminder via email
- **Social Login / OAuth2** — login using Google, LinkedIn, Facebook
- **Refresh tokens** — safer token refresh mechanism
- **Email verification** on signup
- **Rate-limiting & brute-force protection**

---

## 🧩 Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Security
- JWT (io.jsonwebtoken / jjwt or spring-security-oauth)
- Maven
- MySQL (or H2 for local/dev)

---

## ⚙️ Setup & Run (local)
1. Clone repository  
   ```bash
   git clone https://github.com/prabhatpra/spring-boot-auth-service.git
   cd spring-boot-auth-service
