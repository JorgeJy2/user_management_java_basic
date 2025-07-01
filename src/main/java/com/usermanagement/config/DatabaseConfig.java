package com.usermanagement.config;

import com.usermanagement.util.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de configuración para la conexión a la base de datos
 * Implementa el patrón Singleton para gestionar la conexión
 */
public class DatabaseConfig {
    
    private static DatabaseConfig instance;
    private Connection connection;
    
    // Constructor privado para Singleton
    private DatabaseConfig() {}
    
    /**
     * Obtiene la instancia única de DatabaseConfig (Singleton)
     * @return instancia de DatabaseConfig
     */
    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }
    
    /**
     * Obtiene la conexión a la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error en la conexión
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName(Constants.DB_DRIVER);
                connection = DriverManager.getConnection(
                    Constants.DB_URL, 
                    Constants.DB_USER, 
                    Constants.DB_PASSWORD
                );
            } catch (ClassNotFoundException e) {
                throw new SQLException(Constants.MSG_DB_CONNECTION_ERROR + "Driver no encontrado", e);
            } catch (SQLException e) {
                throw new SQLException(Constants.MSG_DB_CONNECTION_ERROR + e.getMessage(), e);
            }
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Verifica si la conexión está activa
     * @return true si la conexión está activa, false en caso contrario
     */
    public boolean isConnectionActive() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
} 