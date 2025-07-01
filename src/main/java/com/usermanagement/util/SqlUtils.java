package com.usermanagement.util;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase de utilidades para operaciones SQL comunes
 * Centraliza métodos reutilizables para evitar duplicación de código
 */
public final class SqlUtils {
    
    // Constructor privado para evitar instanciación
    private SqlUtils() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada");
    }
    
    /**
     * Cierra los recursos de base de datos de forma segura
     * @param resultSet ResultSet a cerrar
     * @param statement PreparedStatement a cerrar
     */
    public static void closeResources(ResultSet resultSet, PreparedStatement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar ResultSet: " + e.getMessage());
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }
    
    /**
     * Cierra los recursos de base de datos de forma segura (solo statement)
     * @param statement PreparedStatement a cerrar
     */
    public static void closeResources(PreparedStatement statement) {
        closeResources(null, statement);
    }
    
    /**
     * Cierra los recursos de base de datos de forma segura (solo resultSet)
     * @param resultSet ResultSet a cerrar
     */
    public static void closeResources(ResultSet resultSet) {
        closeResources(resultSet, null);
    }
    
    /**
     * Cierra todos los recursos de base de datos de forma segura
     * @param resultSet ResultSet a cerrar
     * @param statement PreparedStatement a cerrar
     * @param connection Connection a cerrar
     */
    public static void closeAllResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        closeResources(resultSet, statement);
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar Connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Verifica si una cadena es nula o vacía
     * @param value cadena a verificar
     * @return true si es nula o vacía, false en caso contrario
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Verifica si un entero es válido (mayor que 0)
     * @param value entero a verificar
     * @return true si es válido, false en caso contrario
     */
    public static boolean isValidId(Integer value) {
        return value != null && value > 0;
    }
    
    /**
     * Obtiene un valor String del ResultSet de forma segura
     * @param resultSet ResultSet
     * @param columnName nombre de la columna
     * @return valor String o null si no existe
     */
    public static String getStringSafely(ResultSet resultSet, String columnName) {
        try {
            return resultSet.getString(columnName);
        } catch (SQLException e) {
            System.err.println("Error al obtener String de columna " + columnName + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Obtiene un valor Integer del ResultSet de forma segura
     * @param resultSet ResultSet
     * @param columnName nombre de la columna
     * @return valor Integer o null si no existe
     */
    public static Integer getIntSafely(ResultSet resultSet, String columnName) {
        try {
            int value = resultSet.getInt(columnName);
            return resultSet.wasNull() ? null : value;
        } catch (SQLException e) {
            System.err.println("Error al obtener Integer de columna " + columnName + ": " + e.getMessage());
            return null;
        }
    }
} 