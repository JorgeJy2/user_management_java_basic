package com.usermanagement.util;

/**
 * Clase que contiene todas las constantes utilizadas en la aplicación
 */
public final class Constants {
    
    // Constructor privado para evitar instanciación
    private Constants() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada");
    }
    
    // Constantes de base de datos
    public static final String DB_URL = "jdbc:mysql://localhost:3306/user_management";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "P_s@3s3s";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Constantes de consultas SQL
    public static final String SQL_SELECT_ALL_USERS = 
        "SELECT u.id, u.name, u.age, u.gender_id, u.user_name, u.insert_date, u.update_date, g.gender_name " +
        "FROM users u LEFT JOIN gender g ON u.gender_id = g.gender_id";
    
    public static final String SQL_SELECT_USER_BY_ID = 
        "SELECT u.id, u.name, u.age, u.gender_id, u.user_name, u.insert_date, u.update_date, g.gender_name " +
        "FROM users u LEFT JOIN gender g ON u.gender_id = g.gender_id WHERE u.id = ?";
    
    public static final String SQL_INSERT_USER = 
        "INSERT INTO users (name, age, gender_id, user_name) VALUES (?, ?, ?, ?)";
    
    public static final String SQL_UPDATE_USER = 
        "UPDATE users SET name = ?, age = ?, gender_id = ?, user_name = ? WHERE id = ?";
    
    public static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";
    
    public static final String SQL_SELECT_ALL_GENDERS = "SELECT gender_id, gender_name FROM gender";
    
    public static final String SQL_SELECT_GENDER_BY_ID = "SELECT gender_id, gender_name FROM gender WHERE gender_id = ?";
    
    // Constantes de mensajes
    public static final String MSG_USER_CREATED = "Usuario creado exitosamente con ID: ";
    public static final String MSG_USER_UPDATED = "Usuario actualizado exitosamente";
    public static final String MSG_USER_DELETED = "Usuario eliminado exitosamente";
    public static final String MSG_USER_NOT_FOUND = "Usuario no encontrado con ID: ";
    public static final String MSG_DB_CONNECTION_ERROR = "Error al conectar con la base de datos: ";
    public static final String MSG_SQL_ERROR = "Error en la consulta SQL: ";
    public static final String MSG_VALIDATION_ERROR = "Error de validación: ";
    
    // Constantes de validación
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 150;
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;
} 