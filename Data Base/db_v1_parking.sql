-- ===============================
-- CREATE DATABASE
-- ===============================
DROP DATABASE IF EXISTS parking_db;
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
	update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

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

-- ===============================
-- INITIAL DATA (OPTIONAL)
-- ===============================

-- --------------------------------------------------
-- Default rates:
-- NORMAL → standard rate
-- STUDENT → discounted rate
-- --------------------------------------------------
-- INSERT INTO rate (type, price_per_minute) VALUES
-- ('NORMAL', 100),
-- ('STUDENT', 70);


-- --------------------------------------------------
-- Initial configuration:
-- total_capacity → max number of vehicles
-- available_spaces → starts equal to capacity
-- --------------------------------------------------
-- INSERT INTO configuration (total_capacity, available_spaces)
-- VALUES (50, 50);


-- --------------------------------------------------
-- Initial admin user:
-- Allows first system access
-- ⚠️ In production, password must be encrypted
-- --------------------------------------------------
-- INSERT INTO user (name, username, password, role)
-- VALUES ('Administrator', 'admin', 'admin123', 'ADMIN');userpassword