package com.usermanagement.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase POJO que representa la entidad User
 */
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private Integer genderId;
    private String userName;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
    private String genderName; // Campo adicional para mostrar el nombre del género
    
    // Constructor por defecto
    public User() {}
    
    // Constructor con parámetros básicos
    public User(String name, Integer age, Integer genderId, String userName) {
        this.name = name;
        this.age = age;
        this.genderId = genderId;
        this.userName = userName;
    }
    
    // Constructor completo
    public User(Integer id, String name, Integer age, Integer genderId, String userName, 
                LocalDateTime insertDate, LocalDateTime updateDate, String genderName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.genderId = genderId;
        this.userName = userName;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
        this.genderName = genderName;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Integer getGenderId() {
        return genderId;
    }
    
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public LocalDateTime getInsertDate() {
        return insertDate;
    }
    
    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
    
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
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
        User user = (User) obj;
        return Objects.equals(id, user.id) &&
               Objects.equals(name, user.name) &&
               Objects.equals(age, user.age) &&
               Objects.equals(genderId, user.genderId) &&
               Objects.equals(userName, user.userName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, genderId, userName);
    }
    
    // Método toString
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", genderId=" + genderId +
                ", userName='" + userName + '\'' +
                ", insertDate=" + insertDate +
                ", updateDate=" + updateDate +
                ", genderName='" + genderName + '\'' +
                '}';
    }
} 