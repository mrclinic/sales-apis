# Sales System

A Spring Boot 3.x application to manage sales operations, integrated with **MySQL (Docker)**, **Swagger/OpenAPI**, and **AOP-based logging**.

---

## Table of Contents

- [Features](#features)  
- [Technologies](#technologies)  
- [Setup](#setup)  
- [Running the Application](#running-the-application)  
- [API Documentation](#api-documentation)  
- [Logging](#logging)  
- [Docker MySQL](#docker-mysql)  
- [Custom Logging with @LogUpdate](#custom-logging-with-logupdate)  

---

## Features

- CRUD operations for sales entities  
- Swagger UI for API documentation  
- Automatic logging of all service operations  
- Dedicated logging for `@LogUpdate` annotated methods  
- Integration with MySQL via Docker  
- Separate log files for normal operations and sales updates  

---

## Technologies

- **Spring Boot 3.x**  
- **Spring Data JPA**  
- **MySQL 8.x** (Docker)  
- **Springdoc OpenAPI 2.8.9** (Swagger UI)  
- **Aspect-Oriented Programming (AOP) for logging**  
- **Logback** for logging configuration  

---

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/mrclinic/sales-apis.git
cd sales-apis
```

### 2. Start MySQL using Docker

```bash
docker-compose up -d
```

- Database: `sales_db`  
- User: `app_user`  
- Password: `app_pass`  

### 3. Configure application

Check `src/main/resources/application.properties`:

---

## Running the Application

1. Build the project:

```bash
./mvnw clean install
```

2. Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

3. Access the API:

- Swagger UI: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)  
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)  

---

## API Documentation

- `GET /api/sales` → List all sales  
- `POST /api/sales` → Create a new sale  
- `PUT /api/sales/{id}` → Update a sale (logged with `@LogUpdate`)  
- `DELETE /api/sales/{id}` → Delete a sale  

Swagger UI allows you to test all endpoints interactively.

---

## Logging

The project uses **Logback** for logging.

- **Normal application logs:** `logs/application.log`  
- **Sales update logs (`@LogUpdate`):** `logs/sales-operations.log`  

Logs rotate daily and keep up to 7 days of history.  

Example log entries:

**application.log**
```
2025-10-24 11:20:02 INFO  LoggingAspect - Method updateSale executed successfully, returned: Sale(id=1, productName=iPhone 16, quantity=5)
```

**sales-operations.log**
```
2025-10-24 11:20:01 INFO  salesLogger - Updating entity. Old values: [Sale(id=1, productName=iPhone, quantity=3)]
2025-10-24 11:20:02 INFO  salesLogger - Update completed. New values: Sale(id=1, productName=iPhone 16, quantity=5)
```

---

## Docker MySQL

The project uses **Docker Compose** for MySQL:

```yaml
services:
  mysql-db:
    image: mysql:8.0
    container_name: mysql-db
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: sales_db
      MYSQL_USER: app_user
      MYSQL_PASSWORD: app_pass
    ports:
      - "3306:3306"
```

---

## Custom Logging with @LogUpdate

- Any service method annotated with `@LogUpdate` will automatically log:

  1. **Before execution:** old values (from DB or passed entity)  
  2. **After execution:** new values returned  

```java
@LogUpdate("Updating sale information")
public Sale updateSale(Sale sale) {
    // update logic
}



## Folder Structure

src/
 ├─ main/
 │   ├─ java/com/example/salessystem/
 │   │   ├─ aop/LogUpdate.java
 │   │   ├─ aop/LoggingAspect.java
 │   │   ├─ controller/SaleController.java
 │   │   ├─ service/SaleService.java
 │   │   └─ repository/SaleRepository.java
 │   └─ resources/
 │       ├─ application.yml
 │       └─ logback-spring.xml
docker-compose.yml


## DataBase Diagram
check file: sales_db.png
