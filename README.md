# Sistema de Gestión de Usuarios

Este proyecto implementa un sistema completo de gestión de usuarios en Java con arquitectura MVC, siguiendo las mejores prácticas de Clean Code y principios SOLID.

## Estructura del Proyecto

```
src/main/java/com/usermanagement/
├── config/
│   └── DatabaseConfig.java          # Configuración de base de datos (Singleton)
├── controller/
│   └── UserController.java          # Controlador de usuarios
├── dao/
│   ├── UserDAO.java                 # Data Access Object para usuarios
│   └── GenderDAO.java               # Data Access Object para géneros
├── model/
│   ├── User.java                    # POJO para usuarios
│   └── Gender.java                  # POJO para géneros
├── service/
│   ├── UserService.java             # Lógica de negocio para usuarios
│   └── GenderService.java           # Lógica de negocio para géneros
├── util/
│   └── Constants.java               # Constantes de la aplicación
└── UserManagementApp.java           # Clase principal
```

## Características Implementadas

### ✅ Arquitectura MVC
- **Model**: Clases POJO (User, Gender)
- **View**: Salida por consola
- **Controller**: UserController que coordina las operaciones

### ✅ Patrones de Diseño
- **Singleton**: DatabaseConfig para gestión de conexión
- **DAO**: Data Access Objects para acceso a datos
- **Service Layer**: Capa de servicios para lógica de negocio
- **Dependency Injection**: Inyección manual de dependencias

### ✅ Clean Code
- Nombres descriptivos y significativos
- Métodos pequeños y con responsabilidad única
- Comentarios JavaDoc completos
- Separación de responsabilidades

### ✅ Validaciones
- Validación de datos de entrada
- Verificación de existencia de registros
- Validación de reglas de negocio

### ✅ Manejo de Errores
- Try-catch apropiados
- Mensajes de error descriptivos
- Logging de operaciones

## Configuración de Base de Datos

### Estructura de Tablas

```sql
USE user_management;

-- Tabla referencial para tipos de género
CREATE TABLE gender (
   gender_id INT AUTO_INCREMENT PRIMARY KEY,
   gender_name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla principal de usuarios
CREATE TABLE users (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   age INT CHECK (age >= 0),
   gender_id INT,
   user_name VARCHAR(50) NOT NULL UNIQUE,
   insert_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (gender_id) REFERENCES gender(gender_id)
);
```

### Datos de Ejemplo

```sql
-- Insertar géneros
INSERT INTO gender (gender_name) VALUES ('Masculino');
INSERT INTO gender (gender_name) VALUES ('Femenino');
INSERT INTO gender (gender_name) VALUES ('No especificado');
```

### Configuración de Conexión

Modifica las constantes en `Constants.java` según tu configuración:

```java
public static final String DB_URL = "jdbc:mysql://localhost:3306/user_management";
public static final String DB_USER = "tu_usuario";
public static final String DB_PASSWORD = "tu_password";
```

## Funcionalidades CRUD

### Create (Crear)
- Crear usuarios con validaciones
- Verificación de nombres de usuario únicos
- Validación de géneros existentes

### Read (Leer)
- Obtener todos los usuarios
- Buscar usuario por ID
- Mostrar información detallada

### Update (Actualizar)
- Actualizar datos de usuarios existentes
- Validaciones de integridad
- Verificación de existencia

### Delete (Eliminar)
- Eliminar usuarios por ID
- Verificación de existencia previa

## Compilación y Ejecución

### Prerrequisitos
- Java 8 o superior
- MySQL Server
- MySQL Connector/J (mysql-connector-java.jar)

### Compilación
```bash
# Compilar desde el directorio raíz
javac -cp ".:mysql-connector-java.jar" src/main/java/com/usermanagement/*.java src/main/java/com/usermanagement/*/*.java
```

### Ejecución
```bash
# Ejecutar la aplicación
java -cp ".:mysql-connector-java.jar:src/main/java" com.usermanagement.UserManagementApp
```

## Ejemplo de Uso

La aplicación ejecuta automáticamente operaciones de ejemplo que demuestran:

1. **Mostrar usuarios existentes**
2. **Crear nuevos usuarios**
3. **Buscar usuarios por ID**
4. **Actualizar usuarios**
5. **Eliminar usuarios**

### Salida de Ejemplo
```
Iniciando Sistema de Gestión de Usuarios...
Conexión a la base de datos establecida exitosamente

=== DEMOSTRACIÓN DEL SISTEMA DE GESTIÓN DE USUARIOS ===

=== LISTA DE USUARIOS ===
No hay usuarios registrados.

=== CREAR USUARIO ===
Usuario creado exitosamente con ID: 1
Usuario creado exitosamente

=== LISTA DE USUARIOS ===
ID: 1
Nombre: María García
Edad: 30
Género: Femenino
Nombre de usuario: maria.garcia
Fecha de inserción: 2024-01-15T10:30:00
Fecha de actualización: 2024-01-15T10:30:00
---
...
```

## Buenas Prácticas Implementadas

### 🔒 Encapsulación
- Atributos privados con getters/setters públicos
- Métodos privados para lógica interna

### 🏗️ Separación de Responsabilidades
- Cada clase tiene una responsabilidad específica
- Capas bien definidas (DAO, Service, Controller)

### 🔄 Inyección de Dependencias
- Dependencias inyectadas por constructor
- Fácil testing y mantenimiento

### 📝 Documentación
- JavaDoc completo
- Comentarios explicativos
- README detallado

### 🛡️ Validaciones
- Validación de entrada de datos
- Verificación de reglas de negocio
- Manejo de casos edge

### 🎯 Convenciones de Código
- Nomenclatura consistente
- Estructura de paquetes clara
- Formato de código estándar

## Extensibilidad

El proyecto está diseñado para ser fácilmente extensible:

- **Nuevas entidades**: Crear nuevos modelos, DAOs y servicios
- **Nuevas validaciones**: Agregar reglas en la capa de servicio
- **Nuevas funcionalidades**: Extender controladores existentes
- **Cambio de base de datos**: Modificar solo la capa DAO

## Contribución

Para contribuir al proyecto:

1. Mantener la arquitectura MVC
2. Seguir las convenciones de nomenclatura
3. Agregar documentación JavaDoc
4. Implementar validaciones apropiadas
5. Mantener la separación de responsabilidades 