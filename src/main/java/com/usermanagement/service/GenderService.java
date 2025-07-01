package com.usermanagement.service;

import com.usermanagement.dao.GenderDAO;
import com.usermanagement.model.Gender;
import com.usermanagement.exception.UserManagementException;
import java.util.List;

/**
 * Clase de servicio que contiene la lógica de negocio para géneros
 * Implementa GenderServiceInterface para seguir el principio de inversión de dependencias
 */
public class GenderService implements GenderServiceInterface {
    
    private final GenderDAO genderDAO;
    
    /**
     * Constructor que recibe el DAO por inyección de dependencias
     * @param genderDAO DAO de géneros
     */
    public GenderService(GenderDAO genderDAO) {
        this.genderDAO = genderDAO;
    }
    
    /**
     * Obtiene todos los géneros
     * @return Lista de géneros
     * @throws UserManagementException si hay error al obtener los géneros
     */
    @Override
    public List<Gender> getAllGenders() throws UserManagementException {
        return genderDAO.getAllGenders();
    }
    
    /**
     * Obtiene un género por su ID
     * @param genderId ID del género
     * @return Gender encontrado
     * @throws UserManagementException si el género no existe o hay error
     */
    @Override
    public Gender getGenderById(int genderId) throws UserManagementException {
        if (genderId <= 0) {
            throw UserManagementException.validationError("ID de género debe ser mayor que 0");
        }
        
        return genderDAO.getGenderById(genderId);
    }
    
    /**
     * Verifica si existe un género con el ID especificado
     * @param genderId ID del género a verificar
     * @return true si existe, false en caso contrario
     * @throws UserManagementException si hay error al verificar
     */
    @Override
    public boolean genderExists(int genderId) throws UserManagementException {
        return genderDAO.genderExists(genderId);
    }
} 