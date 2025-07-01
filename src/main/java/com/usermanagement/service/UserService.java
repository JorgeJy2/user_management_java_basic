package com.usermanagement.service;

import com.usermanagement.dao.UserDAO;
import com.usermanagement.dao.GenderDAO;
import com.usermanagement.model.User;
import com.usermanagement.exception.UserManagementException;
import com.usermanagement.util.Constants;
import java.util.List;

/**
 * Clase de servicio que contiene la lógica de negocio para usuarios
 * Implementa UserServiceInterface para seguir el principio de inversión de dependencias
 */
public class UserService implements UserServiceInterface {
    
    private final UserDAO userDAO;
    private final GenderDAO genderDAO;
    
    /**
     * Constructor que recibe los DAOs por inyección de dependencias
     * @param userDAO DAO de usuarios
     * @param genderDAO DAO de géneros
     */
    public UserService(UserDAO userDAO, GenderDAO genderDAO) {
        this.userDAO = userDAO;
        this.genderDAO = genderDAO;
    }
    
    /**
     * Obtiene todos los usuarios
     * @return Lista de usuarios
     * @throws UserManagementException si hay error al obtener los usuarios
     */
    @Override
    public List<User> getAllUsers() throws UserManagementException {
        return userDAO.getAllUsers();
    }
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return User encontrado
     * @throws UserManagementException si el usuario no existe o hay error
     */
    @Override
    public User getUserById(int id) throws UserManagementException {
        if (id <= 0) {
            throw UserManagementException.validationError("ID debe ser mayor que 0");
        }
        
        return userDAO.getUserById(id);
    }
    
    /**
     * Crea un nuevo usuario con validaciones
     * @param user usuario a crear
     * @return true si se creó correctamente
     * @throws UserManagementException si hay error de validación o al crear
     */
    @Override
    public boolean createUser(User user) throws UserManagementException {
        validateUser(user);

        //Check end praxisglobe.com
        
        if (userDAO.userNameExists(user.getUserName())) {
            throw UserManagementException.userAlreadyExists(user.getUserName());
        }
        
        if (user.getGenderId() != null && !genderDAO.genderExists(user.getGenderId())) {
            throw UserManagementException.genderNotFound(user.getGenderId());
        }
        
        int userId = userDAO.createUser(user);
        return userId > 0;
    }
    
    /**
     * Actualiza un usuario existente con validaciones
     * @param user usuario a actualizar
     * @return true si se actualizó correctamente
     * @throws UserManagementException si hay error de validación o al actualizar
     */
    @Override
    public boolean updateUser(User user) throws UserManagementException {
        if (user.getId() == null || user.getId() <= 0) {
            throw UserManagementException.validationError("ID de usuario inválido");
        }
        
        // Verificar que el usuario existe
        userDAO.getUserById(user.getId());
        
        validateUser(user);
        
        // Verificar si el nombre de usuario ya existe en otro usuario
        User existingUser = userDAO.getUserById(user.getId());
        if (!existingUser.getUserName().equals(user.getUserName()) 
            && userDAO.userNameExists(user.getUserName())) {
            throw UserManagementException.userAlreadyExists(user.getUserName());
        }
        
        if (user.getGenderId() != null && !genderDAO.genderExists(user.getGenderId())) {
            throw UserManagementException.genderNotFound(user.getGenderId());
        }
        
        userDAO.updateUser(user);
        return true;
    }
    
    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     * @throws UserManagementException si el usuario no existe o hay error al eliminar
     */
    @Override
    public boolean deleteUser(int id) throws UserManagementException {
        if (id <= 0) {
            throw UserManagementException.validationError("ID debe ser mayor que 0");
        }
        
        // Verificar que el usuario existe
        userDAO.getUserById(id);
        
        userDAO.deleteUser(id);
        return true;
    }
    
    /**
     * Valida los datos de un usuario
     * @param user usuario a validar
     * @throws UserManagementException si la validación falla
     */
    private void validateUser(User user) throws UserManagementException {
        if (user == null) {
            throw UserManagementException.validationError("Usuario no puede ser null");
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw UserManagementException.validationError("Nombre es obligatorio");
        }
        
        if (user.getName().length() < Constants.MIN_NAME_LENGTH || 
            user.getName().length() > Constants.MAX_NAME_LENGTH) {
            throw UserManagementException.validationError(
                "Nombre debe tener entre " + Constants.MIN_NAME_LENGTH + 
                " y " + Constants.MAX_NAME_LENGTH + " caracteres");
        }
        
        if (user.getAge() != null && (user.getAge() < Constants.MIN_AGE || user.getAge() > Constants.MAX_AGE)) {
            throw UserManagementException.validationError(
                "Edad debe estar entre " + Constants.MIN_AGE + " y " + Constants.MAX_AGE);
        }
        
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw UserManagementException.validationError("Nombre de usuario es obligatorio");
        }
        
        if (user.getUserName().length() < Constants.MIN_USERNAME_LENGTH || 
            user.getUserName().length() > Constants.MAX_USERNAME_LENGTH) {
            throw UserManagementException.validationError(
                "Nombre de usuario debe tener entre " + Constants.MIN_USERNAME_LENGTH + 
                " y " + Constants.MAX_USERNAME_LENGTH + " caracteres");
        }
    }
} 