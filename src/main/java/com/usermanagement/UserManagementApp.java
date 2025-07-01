package com.usermanagement;

import com.usermanagement.config.DatabaseConfig;
import com.usermanagement.dao.UserDAO;
import com.usermanagement.dao.GenderDAO;
import com.usermanagement.service.UserService;
import com.usermanagement.service.GenderService;
import com.usermanagement.service.UserServiceInterface;
import com.usermanagement.service.GenderServiceInterface;
import com.usermanagement.controller.UserController;

/**
 * Clase principal de la aplicación de gestión de usuarios
 * Implementa inyección de dependencias manual usando interfaces
 */
public class UserManagementApp {
    
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gestión de Usuarios...");
        
        try {
            // Configuración de la base de datos (Singleton)
            DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
            
            // Crear DAOs con inyección de dependencias
            UserDAO userDAO = new UserDAO(databaseConfig);
            GenderDAO genderDAO = new GenderDAO(databaseConfig);
            
            // Crear servicios con inyección de dependencias (usando interfaces)
            UserServiceInterface userService = new UserService(userDAO, genderDAO);
            GenderServiceInterface genderService = new GenderService(genderDAO);
            
            // Crear controlador con inyección de dependencias (usando interfaces)
            UserController userController = new UserController(userService, genderService);
            
            // Ejecutar operaciones de ejemplo
            userController.runSampleOperations();
            
            // Cerrar conexión a la base de datos
            databaseConfig.closeConnection();
            
            System.out.println("\n=== APLICACIÓN FINALIZADA ===");
            
        } catch (Exception e) {
            System.err.println("Error al ejecutar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 