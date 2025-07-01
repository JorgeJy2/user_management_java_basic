package com.usermanagement.service;

import com.usermanagement.model.Gender;
import com.usermanagement.exception.UserManagementException;
import java.util.List;

/**
 * Interfaz que define los contratos para el servicio de géneros
 */
public interface GenderServiceInterface {
    
    /**
     * Obtiene todos los géneros
     * @return Lista de géneros
     * @throws UserManagementException si hay error al obtener los géneros
     */
    List<Gender> getAllGenders() throws UserManagementException;
    
    /**
     * Obtiene un género por su ID
     * @param genderId ID del género
     * @return Gender encontrado
     * @throws UserManagementException si el género no existe o hay error
     */
    Gender getGenderById(int genderId) throws UserManagementException;
    
    /**
     * Verifica si existe un género con el ID especificado
     * @param genderId ID del género a verificar
     * @return true si existe, false en caso contrario
     * @throws UserManagementException si hay error al verificar
     */
    boolean genderExists(int genderId) throws UserManagementException;
} 