package com.usermanagement.model;

import java.util.Objects;

/**
 * Clase POJO que representa la entidad Gender
 */
public class Gender {
    private Integer genderId;
    private String genderName;
    
    // Constructor por defecto
    public Gender() {}
    
    // Constructor con parámetros
    public Gender(Integer genderId, String genderName) {
        this.genderId = genderId;
        this.genderName = genderName;
    }
    
    // Getters y Setters
    public Integer getGenderId() {
        return genderId;
    }
    
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }
    
    public String getGenderName() {
        return genderName;
    }
    
    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
    
    // Métodos equals y hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Gender gender = (Gender) obj;
        return Objects.equals(genderId, gender.genderId) &&
               Objects.equals(genderName, gender.genderName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(genderId, genderName);
    }
    
    // Método toString
    @Override
    public String toString() {
        return "Gender{" +
                "genderId=" + genderId +
                ", genderName='" + genderName + '\'' +
                '}';
    }
} 