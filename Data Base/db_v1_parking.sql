-- ===============================
--  CREAR BASE DE DATOS
-- ===============================
CREATE DATABASE IF NOT EXISTS parqueadero_db;
USE parqueadero_db;

-- ===============================
-- Crear tabla USUARIOS
-- ===============================
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'OPERADOR') NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===============================
-- Crear tabla TARIFAS
-- ===============================
CREATE TABLE tarifa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('ESTUDIANTE', 'NORMAL') NOT NULL UNIQUE,
    precio_por_minuto DECIMAL(10,2) NOT NULL
);

-- ===============================
-- crear tabla CONFIGURACIÓN
-- ===============================
CREATE TABLE configuracion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    capacidad_total INT NOT NULL,
    espacios_disponibles INT NOT NULL
);

-- ===============================
-- 🚗 REGISTROS (CORE DEL SISTEMA)
-- ===============================
CREATE TABLE registro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    ticket_codigo VARCHAR(50) NOT NULL UNIQUE, 

    placa VARCHAR(10) NOT NULL,
    estudiante BOOLEAN NOT NULL,

    hora_entrada DATETIME NOT NULL,
    hora_salida DATETIME,

    tiempo_minutos BIGINT,
    valor_pagado DECIMAL(10,2),

    estado ENUM('ACTIVO', 'FINALIZADO') NOT NULL,

    usuario_id BIGINT NOT NULL,
    tarifa_id BIGINT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_registro_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id),

    CONSTRAINT fk_registro_tarifa
        FOREIGN KEY (tarifa_id) REFERENCES tarifa(id)
);


CREATE INDEX idx_registro_ticket ON registro(ticket_codigo);
CREATE INDEX idx_registro_estado ON registro(estado);
CREATE INDEX idx_registro_placa ON registro(placa);

-- ===============================
-- DATOS INICIALES (OPCIONAL)
-- ===============================

-- --------------------------------------------------
-- Inserta tarifas por defecto:
-- NORMAL → tarifa estándar
-- ESTUDIANTE → tarifa reducida
-- --------------------------------------------------
-- INSERT INTO tarifa (tipo, precio_por_minuto) VALUES
-- ('NORMAL', 100),
-- ('ESTUDIANTE', 70);


-- --------------------------------------------------
-- Configuración inicial del parqueadero:
-- capacidad_total → número máximo de vehículos
-- espacios_disponibles → inicia igual a la capacidad
-- --------------------------------------------------
-- INSERT INTO configuracion (capacidad_total, espacios_disponibles)
-- VALUES (50, 50);


-- --------------------------------------------------
-- Usuario administrador inicial:
-- Permite acceder al sistema por primera vez
-- ⚠️ En producción se recomienda encriptar contraseña
-- --------------------------------------------------
-- INSERT INTO usuario (nombre, username, password, rol)
-- VALUES ('Administrador', 'admin', 'admin123', 'ADMIN');