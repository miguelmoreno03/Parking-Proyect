# 🚗 Parking System API

A backend system for managing parking operations, built with **Spring Boot**, focused on handling rates, vehicles, and parking transactions efficiently.

---

## 📌 Description

This project is a RESTful API designed to manage a parking system. It includes features such as:

- 🚘 Vehicle parking management  
- 💰 Rate creation and management  
- ⏱️ Price calculation based on time and vehicle type  
- ⚠️ Exception handling and validations  

---

## ⚙️ Tech Stack

- ☕ Java 17+  
- 🌱 Spring Boot  
- 🗄️ Spring Data JPA (Hibernate)  
- 📦 Lombok  
- 🐬 MySQL  
- 🔧 Maven  

---

## 📂 Backend Project Structure

```bash
src/
 └── main/
     └── java/com/_bit/solutions/parking_proyect/
         ├── controller/     # REST Controllers
         ├── service/        # Business logic
         ├── repository/     # Data access layer
         ├── entity/         # JPA Entities
         ├── dto/            # Data Transfer Objects
         ├── mapper/         # Entity <-> DTO mapping
         └── exception/      # Error handling
```

---

## 🛠️ Run Locally

Follow these steps to run the project on your local machine:

### 1. Clone the repository

```bash
git clone https://github.com/miguelmoreno03/Parking-Proyect.git
cd parking-system
```

### 2. In SQL do the query

```sql
CREATE DATABASE IF NOT EXISTS parking_db;
USE parking_db;

-- ===============================
-- CREATE TABLE USERS
-- ===============================
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'OPERATOR') NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===============================
-- CREATE TABLE RATES
-- ===============================
CREATE TABLE rate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type ENUM('STUDENT', 'NORMAL') NOT NULL UNIQUE,
    price_per_minute DECIMAL(10,2) NOT NULL
);

-- ===============================
-- CREATE TABLE CONFIGURATION
-- ===============================
CREATE TABLE configuration (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_capacity INT NOT NULL,
    available_spaces INT NOT NULL
);

-- ===============================
-- CREATE TABLE RECORDS 
-- ===============================
CREATE TABLE record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    ticket_code VARCHAR(50) NOT NULL UNIQUE,

    plate VARCHAR(10) NOT NULL,
    is_student BOOLEAN NOT NULL,

    entry_time DATETIME NOT NULL,
    exit_time DATETIME,

    total_minutes BIGINT,
    amount_paid DECIMAL(10,2),

    status ENUM('ACTIVE', 'FINISHED') NOT NULL,

    user_id BIGINT NOT NULL,
    rate_id BIGINT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_record_user
        FOREIGN KEY (user_id) REFERENCES user(id),

    CONSTRAINT fk_record_rate
        FOREIGN KEY (rate_id) REFERENCES rate(id)
);

-- ===============================
-- INDEXES
-- ===============================
CREATE INDEX idx_record_ticket ON record(ticket_code);
CREATE INDEX idx_record_status ON record(status);
CREATE INDEX idx_record_plate ON record(plate);
```

you have the script available in the folder data base.

### 3. You should open the application.properties and modify:

```properties

#BD Configuration.
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Here you have two options or you hardcode the information and delete the environment variables or you create your own environment variable.

### Option 1 (Hardcode the lines):

```properties

#BD Configuration.
spring.datasource.url=jdbc:mysql://localhost:3306/parking_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Option 2 (Environment variable) :

you should click in the intelliJ editor the following option once you have the ID open.
![alt text](image.png)

you should select the edit option.
In the Environment Variables section you should place something like this : 

```properties
DB_URL=jdbc:mysql://localhost:3306/parking_db?useSSL=false&serverTimezone=UTC;DB_USER=your_user;DB_PASSWORD=your_password
```

### 4 you must instal the dependencies: 

you can run the pom XML or you could execute.

```bash
mvn clean install
```

and

```bash
mvn spring-boot:run
```
