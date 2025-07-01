package com.usermanagement.exception;

/**
 * Excepción personalizada para manejar errores de negocio en el sistema de gestión de usuarios
 */
public class UserManagementException extends Exception {
    
    private final ErrorType errorType;
    
    /**
     * Tipos de errores que pueden ocurrir en el sistema
     */
    public enum ErrorType {
        VALIDATION_ERROR("Error de validación"),
        USER_NOT_FOUND("Usuario no encontrado"),
        USER_ALREADY_EXISTS("Usuario ya existe"),
        GENDER_NOT_FOUND("Género no encontrado"),
        DATABASE_ERROR("Error de base de datos"),
        BUSINESS_RULE_VIOLATION("Violación de regla de negocio"),
        UNKNOWN_ERROR("Error desconocido");
        
        private final String description;
        
        ErrorType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Constructor con mensaje y tipo de error
     * @param message mensaje de error
     * @param errorType tipo de error
     */
    public UserManagementException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
    
    /**
     * Constructor con mensaje, tipo de error y causa
     * @param message mensaje de error
     * @param errorType tipo de error
     * @param cause causa original del error
     */
    public UserManagementException(String message, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
    
    /**
     * Obtiene el tipo de error
     * @return tipo de error
     */
    public ErrorType getErrorType() {
        return errorType;
    }
    
    /**
     * Obtiene la descripción del tipo de error
     * @return descripción del error
     */
    public String getErrorDescription() {
        return errorType.getDescription();
    }
    
    /**
     * Crea una excepción de validación
     * @param message mensaje de error
     * @return UserManagementException
     */
    public static UserManagementException validationError(String message) {
        return new UserManagementException(message, ErrorType.VALIDATION_ERROR);
    }
    
    /**
     * Crea una excepción de usuario no encontrado
     * @param userId ID del usuario
     * @return UserManagementException
     */
    public static UserManagementException userNotFound(int userId) {
        return new UserManagementException("Usuario no encontrado con ID: " + userId, ErrorType.USER_NOT_FOUND);
    }
    
    /**
     * Crea una excepción de usuario ya existe
     * @param userName nombre de usuario
     * @return UserManagementException
     */
    public static UserManagementException userAlreadyExists(String userName) {
        return new UserManagementException("El nombre de usuario ya existe: " + userName, ErrorType.USER_ALREADY_EXISTS);
    }
    
    /**
     * Crea una excepción de género no encontrado
     * @param genderId ID del género
     * @return UserManagementException
     */
    public static UserManagementException genderNotFound(int genderId) {
        return new UserManagementException("Género no encontrado con ID: " + genderId, ErrorType.GENDER_NOT_FOUND);
    }
    
    /**
     * Crea una excepción de error de base de datos
     * @param message mensaje de error
     * @param cause causa original
     * @return UserManagementException
     */
    public static UserManagementException databaseError(String message, Throwable cause) {
        return new UserManagementException("Error de base de datos: " + message, ErrorType.DATABASE_ERROR, cause);
    }
    
    /**
     * Crea una excepción de violación de regla de negocio
     * @param message mensaje de error
     * @return UserManagementException
     */
    public static UserManagementException businessRuleViolation(String message) {
        return new UserManagementException(message, ErrorType.BUSINESS_RULE_VIOLATION);
    }
} 