-- Script de inicialización de la base de datos para el Sistema de Gestión de Usuarios
-- Ejecutar este script en MySQL para crear la base de datos y las tablas

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS user_management;
USE user_management;

-- Tabla referencial para tipos de género
CREATE TABLE IF NOT EXISTS gender (
   gender_id INT AUTO_INCREMENT PRIMARY KEY,
   gender_name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla principal de usuarios
CREATE TABLE IF NOT EXISTS users (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   age INT CHECK (age >= 0),
   gender_id INT,
   user_name VARCHAR(50) NOT NULL UNIQUE,
   insert_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (gender_id) REFERENCES gender(gender_id)
);

-- Insertar datos de ejemplo para géneros
INSERT INTO gender (gender_name) VALUES 
('Masculino'),
('Femenino'),
('No especificado')
ON DUPLICATE KEY UPDATE gender_name = gender_name;

-- Insertar algunos usuarios de ejemplo (opcional)
INSERT INTO users (name, age, gender_id, user_name) VALUES 
('Juan Pérez', 25, 1, 'juan.perez'),
('María García', 30, 2, 'maria.garcia'),
('Carlos López', 28, 1, 'carlos.lopez'),
('Ana Martínez', 35, 2, 'ana.martinez')
ON DUPLICATE KEY UPDATE name = name;

-- Verificar que las tablas se crearon correctamente
SELECT 'Tabla gender creada:' as mensaje;
DESCRIBE gender;

SELECT 'Tabla users creada:' as mensaje;
DESCRIBE users;

-- Mostrar datos insertados
SELECT 'Datos en tabla gender:' as mensaje;
SELECT * FROM gender;

SELECT 'Datos en tabla users:' as mensaje;
SELECT u.id, u.name, u.age, u.user_name, g.gender_name, u.insert_date, u.update_date
FROM users u 
LEFT JOIN gender g ON u.gender_id = g.gender_id; 