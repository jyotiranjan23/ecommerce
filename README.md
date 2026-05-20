# E-Commerce Microservices

Spring Boot e-commerce backend built as multiple microservices.

## Services

- `serviceRegistry/serviceRegistry` - Eureka service registry
- `apiGateway/apiGateway` - API Gateway
- `user/user` - User and authentication service
- `product/product` - Product service
- `cart/cart` - Cart service

## Requirements

- Java 21
- Gradle wrapper included in each service
- MySQL

## Environment Variables

Set these before running the services outside local development:

```text
DB_URL=jdbc:mysql://localhost:3306/ecommerce
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET=replace-with-a-long-secure-secret-at-least-32-characters
ADMIN_USERNAME=admin
ADMIN_PASSWORD=change-this-password
EUREKA_URL=http://localhost:8761/eureka/
```

## Local Run Order

Start the services in this order:

1. Service Registry
2. User Service
3. Product Service
4. Cart Service
5. API Gateway

Example:

```powershell
cd serviceRegistry/serviceRegistry
./gradlew bootRun
```

Run the same command from each service folder in a separate terminal.

## GitHub Notes

Generated folders such as `build/`, `.gradle/`, `.idea/`, and local secret files are ignored by Git. Do not commit real passwords, tokens, or production database URLs.

## Render Deployment

This repository includes a `render.yaml` Blueprint and one Dockerfile per service.

Before creating the Blueprint on Render, prepare a hosted MySQL database and keep these values ready:

```text
DB_URL=jdbc:mysql://YOUR_MYSQL_HOST:3306/YOUR_DATABASE
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
ADMIN_PASSWORD=your_initial_admin_password
```

In Render, create a new Blueprint from this GitHub repository. Render will prompt for the secret values marked with `sync: false`.
