package com.usermanagement.controller;

import com.usermanagement.model.User;
import com.usermanagement.service.UserServiceInterface;
import com.usermanagement.service.GenderServiceInterface;
import com.usermanagement.exception.UserManagementException;
import java.util.List;

/**
 * Clase controlador que maneja las operaciones de usuarios
 * Usa interfaces en lugar de implementaciones concretas para seguir el principio de inversión de dependencias
 */
public class UserController {
    
    private final UserServiceInterface userService;
    private final GenderServiceInterface genderService;
    
    /**
     * Constructor que recibe los servicios por inyección de dependencias
     * @param userService servicio de usuarios (interfaz)
     * @param genderService servicio de géneros (interfaz)
     */
    public UserController(UserServiceInterface userService, GenderServiceInterface genderService) {
        this.userService = userService;
        this.genderService = genderService;
    }
    
    /**
     * Obtiene y muestra todos los usuarios
     */
    public void showAllUsers() {
        System.out.println("\n=== LISTA DE USUARIOS ===");
        try {
            List<User> users = userService.getAllUsers();
            
            if (users.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
            } else {
                for (User user : users) {
                    displayUser(user);
                }
            }
        } catch (UserManagementException e) {
            System.err.println("Error: " + e.getErrorDescription() + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene y muestra un usuario por su ID
     * @param id ID del usuario a mostrar
     */
    public void showUserById(int id) {
        System.out.println("\n=== BUSCAR USUARIO POR ID ===");
        try {
            User user = userService.getUserById(id);
            displayUser(user);
        } catch (UserManagementException e) {
            System.err.println("Error: " + e.getErrorDescription() + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Crea un nuevo usuario con datos de ejemplo
     */
    public void createSampleUser() {
        System.out.println("\n=== CREAR USUARIO DE EJEMPLO ===");
        
        try {
            // Crear usuario de ejemplo
            User newUser = new User();
            newUser.setName("Juan Pérez");
            newUser.setAge(25);
            newUser.setGenderId(1); // Asumiendo que existe un género con ID 1
            newUser.setUserName("juan.perez");
            
            boolean success = userService.createUser(newUser);
            
            if (success) {
                System.out.println("Usuario creado exitosamente");
            }
        } catch (UserManagementException e) {
            System.err.println("Error: " + e.getErrorDescription() + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Crea un usuario con datos específicos
     * @param name nombre del usuario
     * @param age edad del usuario
     * @param genderId ID del género
     * @param userName nombre de usuario
     */
    public void createUser(String name, Integer age, Integer genderId, String userName) {
        System.out.println("\n=== CREAR USUARIO ===");
        
        try {
            User newUser = new User();
            newUser.setName(name);
            newUser.setAge(age);
            newUser.setGenderId(genderId);
            newUser.setUserName(userName);
            
            boolean success = userService.createUser(newUser);
            
            if (success) {
                System.out.println("Usuario creado exitosamente");
            }
        } catch (UserManagementException e) {
            System.err.println("Error: " + e.getErrorDescription() + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Actualiza un usuario existente
     * @param id ID del usuario a actualizar
     * @param name nuevo nombre
     * @param age nueva edad
     * @param genderId nuevo ID de género
     * @param userName nuevo nombre de usuario
     */
    public void updateUser(int id, String name, Integer age, Integer genderId, String userName) {
        System.out.println("\n=== ACTUALIZAR USUARIO ===");
        
        try {
            User userToUpdate = new User();
            userToUpdate.setId(id);
            userToUpdate.setName(name);
            userToUpdate.setAge(age);
            userToUpdate.setGenderId(genderId);
            userToUpdate.setUserName(userName);
            
            boolean success = userService.updateUser(userToUpdate);
            
            if (success) {
                System.out.println("Usuario actualizado exitosamente");
            }
        } catch (UserManagementException e) {
            System.err.println("Error: " + e.getErrorDescription() + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     */
    public void deleteUser(int id) {
        System.out.println("\n=== ELIMINAR USUARIO ===");
        
        try {
            boolean success = userService.deleteUser(id);
            
            if (success) {
                System.out.println("Usuario eliminado exitosamente");
            }
        } catch (UserManagementException e) {
            System.err.println("Error: " + e.getErrorDescription() + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Muestra información detallada de un usuario
     * @param user usuario a mostrar
     */
    private void displayUser(User user) {
        System.out.println("ID: " + user.getId());
        System.out.println("Nombre: " + user.getName());
        System.out.println("Edad: " + (user.getAge() != null ? user.getAge() : "No especificada"));
        System.out.println("Género: " + (user.getGenderName() != null ? user.getGenderName() : "No especificado"));
        System.out.println("Nombre de usuario: " + user.getUserName());
        System.out.println("Fecha de inserción: " + (user.getInsertDate() != null ? user.getInsertDate() : "No disponible"));
        System.out.println("Fecha de actualización: " + (user.getUpdateDate() != null ? user.getUpdateDate() : "No disponible"));
        System.out.println("---");
    }
    
    /**
     * Ejecuta operaciones de ejemplo para demostrar el CRUD
     */
    public void runSampleOperations() {
        System.out.println("=== DEMOSTRACIÓN DEL SISTEMA DE GESTIÓN DE USUARIOS ===\n");
        
        // Mostrar usuarios existentes
        showAllUsers();
        
        // Crear usuarios de ejemplo
        createUser("María García", 30, 2, "maria.garcia");
        createUser("Carlos López", 28, 1, "carlos.lopez");
        createUser("Ana Martínez", 35, 2, "ana.martinez");
        
        // Mostrar usuarios después de crear
        showAllUsers();
        
        // Buscar un usuario específico
        showUserById(1);
        
        // Actualizar un usuario
        updateUser(1, "Juan Pérez Actualizado", 26, 1, "juan.perez.actualizado");
        
        // Mostrar el usuario actualizado
        showUserById(1);
        
        // Eliminar un usuario
        deleteUser(2);
        
        // Mostrar usuarios después de eliminar
        showAllUsers();
    }
} 