

-- Crea la base de datos principal
CREATE DATABASE IF NOT EXISTS user_management
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;


USE user_management;


-- Tabla referencial para tipos de género
CREATE TABLE gender (
    gender_id INT AUTO_INCREMENT PRIMARY KEY,
    gender_name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla principal de usuarios
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT CHECK (age >= 0),
    gender_id INT,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    insert_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (gender_id) REFERENCES gender(gender_id)
);



-- Paso 5: Insertar datos iniciales (géneros)


INSERT INTO gender (gender_name)
VALUES ('Male'), ('Female'), ('Non-binary'), ('Other');

-- Paso 6: Insertar un usuario de prueba

INSERT INTO users (name, age, gender_id, user_name)
VALUES ('Jorge Jacobo', 26, 1, 'jjacobo');

-- Selección de usuarios

SELECT * FROM users;





SELECT u.id, u.name, u.age, u.gender_id, u.user_name, u.insert_date, u.update_date, g.gender_name FROM users u LEFT JOIN gender g ON u.gender_id = g.gender_id

