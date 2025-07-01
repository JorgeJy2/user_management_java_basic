package com.usermanagement.dao;

import com.usermanagement.config.DatabaseConfig;
import com.usermanagement.exception.UserManagementException;
import com.usermanagement.model.User;
import com.usermanagement.util.Constants;
import com.usermanagement.util.SqlUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para el acceso a datos de la entidad User
 */
public class UserDAO {
    
    private final DatabaseConfig databaseConfig;
    
    /**
     * Constructor que recibe la configuración de base de datos por inyección
     * @param databaseConfig configuración de la base de datos
     */
    public UserDAO(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
    
    /**
     * Obtiene todos los usuarios de la base de datos
     * @return Lista de usuarios
     * @throws UserManagementException si hay error al acceder a la base de datos
     */
    public List<User> getAllUsers() throws UserManagementException {
        List<User> users = new ArrayList<>();
        // Lista vácia. 

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = databaseConfig.getConnection();
            statement = connection.prepareStatement(Constants.SQL_SELECT_ALL_USERS);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
            
            System.out.println("Usuarios recuperados exitosamente: " + users.size());
            
        } catch (SQLException e) {
            throw UserManagementException.databaseError("Error al obtener usuarios", e);
        } finally {
            SqlUtils.closeResources(resultSet, statement);
        }
       
         

        return users;
    }
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario a buscar
     * @return User encontrado
     * @throws UserManagementException si hay error al acceder a la base de datos o si el usuario no existe
     */
    public User getUserById(int id) throws UserManagementException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = databaseConfig.getConnection();
            statement = connection.prepareStatement(Constants.SQL_SELECT_USER_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            } else {
                throw UserManagementException.userNotFound(id);
            }
            
        } catch (SQLException e) {
            throw UserManagementException.databaseError("Error al obtener usuario con ID: " + id, e);
        } finally {
            SqlUtils.closeResources(resultSet, statement);
        }
    }
    
    /**
     * Crea un nuevo usuario en la base de datos
     * @param user usuario a crear
     * @return ID del usuario creado
     * @throws UserManagementException si hay error al crear el usuario
     */
    public int createUser(User user) throws UserManagementException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.setInt(3, user.getGenderId());
            statement.setString(4, user.getUserName());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        System.out.println(Constants.MSG_USER_CREATED + userId);
                        return userId;
                    }
                }
            }
            
            throw UserManagementException.databaseError("No se pudo crear el usuario", null);
            
        } catch (SQLException e) {
            throw UserManagementException.databaseError("Error al crear usuario", e);
        }
    }
    
    /**
     * Actualiza un usuario existente en la base de datos
     * @param user usuario a actualizar
     * @throws UserManagementException si hay error al actualizar el usuario o si no existe
     */
    public void updateUser(User user) throws UserManagementException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = databaseConfig.getConnection();
            statement = connection.prepareStatement(Constants.SQL_UPDATE_USER);
            
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.setInt(3, user.getGenderId());
            statement.setString(4, user.getUserName());
            statement.setInt(5, user.getId());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println(Constants.MSG_USER_UPDATED);
            } else {
                throw UserManagementException.userNotFound(user.getId());
            }
            
        } catch (SQLException e) {
            throw UserManagementException.databaseError("Error al actualizar usuario con ID: " + user.getId(), e);
        } finally {
            SqlUtils.closeResources(null, statement);
        }
    }
    
    /**
     * Elimina un usuario de la base de datos
     * @param id ID del usuario a eliminar
     * @throws UserManagementException si hay error al eliminar el usuario o si no existe
     */
    public void deleteUser(int id) throws UserManagementException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.SQL_DELETE_USER)) {
            
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println(Constants.MSG_USER_DELETED);
            } else {
                throw UserManagementException.userNotFound(id);
            }
            
        } catch (SQLException e) {
            throw UserManagementException.databaseError("Error al eliminar usuario con ID: " + id, e);
        }
    }
    
    /**
     * Verifica si existe un usuario con el ID especificado
     * @param id ID del usuario a verificar
     * @return true si existe, false en caso contrario
     * @throws UserManagementException si hay error al acceder a la base de datos
     */
    public boolean userExists(int id) throws UserManagementException {
        try {
            getUserById(id);
            return true;
        } catch (UserManagementException e) {
            if (e.getErrorType() == UserManagementException.ErrorType.USER_NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }
    
    /**
     * Verifica si existe un usuario con el nombre de usuario especificado
     * @param userName nombre de usuario a verificar
     * @return true si existe, false en caso contrario
     * @throws UserManagementException si hay error al acceder a la base de datos
     */
    public boolean userNameExists(String userName) throws UserManagementException {
        //TODO: add to constants. 

        String sql = "SELECT COUNT(*) FROM users WHERE user_name = ?";
        
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, userName);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            throw UserManagementException.databaseError("Error al verificar existencia de nombre de usuario: " + userName, e);
        }
        
        return false;
    }
    
    /**
     * Mapea un ResultSet a un objeto User
     * @param resultSet ResultSet con los datos del usuario
     * @return User mapeado
     * @throws SQLException si hay error al leer los datos
     */
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setAge(resultSet.getInt("age"));
        user.setGenderId(resultSet.getInt("gender_id"));
        user.setUserName(resultSet.getString("user_name"));
        
        // Mapear fechas
        Timestamp insertTimestamp = resultSet.getTimestamp("insert_date");
        if (insertTimestamp != null) {
            user.setInsertDate(insertTimestamp.toLocalDateTime());
        }
        
        Timestamp updateTimestamp = resultSet.getTimestamp("update_date");
        if (updateTimestamp != null) {
            user.setUpdateDate(updateTimestamp.toLocalDateTime());
        }
        
        user.setGenderName(resultSet.getString("gender_name"));
        
        return user;
    }
    
} 