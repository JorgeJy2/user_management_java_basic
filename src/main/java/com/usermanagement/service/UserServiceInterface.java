package com.usermanagement.service;

import com.usermanagement.model.User;
import com.usermanagement.exception.UserManagementException;
import java.util.List;

/**
 * Interfaz que define los contratos para el servicio de usuarios
 */
public interface UserServiceInterface {
    
    /**
     * Obtiene todos los usuarios
     * @return Lista de usuarios
     * @throws UserManagementException si hay error al obtener los usuarios
     */
    List<User> getAllUsers() throws UserManagementException;
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return User encontrado
     * @throws UserManagementException si el usuario no existe o hay error
     */
    User getUserById(int id) throws UserManagementException;
    
    /**
     * Crea un nuevo usuario con validaciones
     * @param user usuario a crear
     * @return true si se creó correctamente
     * @throws UserManagementException si hay error de validación o al crear
     */
    boolean createUser(User user) throws UserManagementException;
    
    /**
     * Actualiza un usuario existente con validaciones
     * @param user usuario a actualizar
     * @return true si se actualizó correctamente
     * @throws UserManagementException si hay error de validación o al actualizar
     */
    boolean updateUser(User user) throws UserManagementException;
    
    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     * @throws UserManagementException si el usuario no existe o hay error al eliminar
     */
    boolean deleteUser(int id) throws UserManagementException;
} 