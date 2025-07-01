package com.usermanagement.dao;

import com.usermanagement.config.DatabaseConfig;
import com.usermanagement.exception.UserManagementException;
import com.usermanagement.model.Gender;
import com.usermanagement.util.Constants;
import com.usermanagement.util.SqlUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para el acceso a datos de la entidad Gender
 */
public class GenderDAO {
    
    private final DatabaseConfig databaseConfig;
    
    /**
     * Constructor que recibe la configuración de base de datos por inyección
     * @param databaseConfig configuración de la base de datos
     */
    public GenderDAO(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
    
    /**
     * Obtiene todos los géneros de la base de datos
     * @return Lista de géneros
     * @throws UserManagementException si hay error al acceder a la base de datos
     */
    public List<Gender> getAllGenders() throws UserManagementException {
        List<Gender> genders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = databaseConfig.getConnection();
            statement = connection.prepareStatement(Constants.SQL_SELECT_ALL_GENDERS);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Gender gender = new Gender();
                gender.setGenderId(resultSet.getInt("gender_id"));
                gender.setGenderName(resultSet.getString("gender_name"));
                genders.add(gender);
            }
            
            System.out.println("Géneros recuperados exitosamente: " + genders.size());
            
        } catch (SQLException e) {
            throw UserManagementException.databaseError("Error al obtener géneros", e);
        } finally {
            SqlUtils.closeResources(resultSet, statement);
        }
        
        return genders;
    }
    
    /**
     * Obtiene un género por su ID
     * @param genderId ID del género a buscar
     * @return Gender encontrado
     * @throws UserManagementException si hay error al acceder a la base de datos o si el género no existe
     */
    public Gender getGenderById(int genderId) throws UserManagementException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = databaseConfig.getConnection();
            statement = connection.prepareStatement(Constants.SQL_SELECT_GENDER_BY_ID);
            statement.setInt(1, genderId);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Gender gender = new Gender();
                gender.setGenderId(resultSet.getInt("gender_id"));
                gender.setGenderName(resultSet.getString("gender_name"));
                return gender;
            } else {
                throw UserManagementException.genderNotFound(genderId);
            }
            
        } catch (SQLException e) {
            throw UserManagementException.databaseError("Error al obtener género con ID: " + genderId, e);
        } finally {
            SqlUtils.closeResources(resultSet, statement);
        }
    }
    
    /**
     * Verifica si existe un género con el ID especificado
     * @param genderId ID del género a verificar
     * @return true si existe, false en caso contrario
     * @throws UserManagementException si hay error al acceder a la base de datos
     */
    public boolean genderExists(int genderId) throws UserManagementException {
        try {
            getGenderById(genderId);
            return true;
        } catch (UserManagementException e) {
            if (e.getErrorType() == UserManagementException.ErrorType.GENDER_NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }
    
} 