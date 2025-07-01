# Diagrama de Arquitectura - Sistema de Gestión de Usuarios

## Arquitectura General

El proyecto sigue una arquitectura en capas (Layered Architecture) con separación clara de responsabilidades y principios SOLID.

```mermaid
graph TB
    subgraph "Capa de Presentación"
        A[UserManagementApp.java] --> B[UserController.java]
    end
    
    subgraph "Capa de Servicios"
        B --> C[UserService.java]
        B --> D[GenderService.java]
        C --> E[UserServiceInterface.java]
        D --> F[GenderServiceInterface.java]
    end
    
    subgraph "Capa de Acceso a Datos"
        C --> G[UserDAO.java]
        D --> H[GenderDAO.java]
    end
    
    subgraph "Capa de Configuración"
        G --> I[DatabaseConfig.java]
        H --> I
    end
    
    subgraph "Capa de Modelos"
        C --> J[User.java]
        D --> K[Gender.java]
        G --> J
        H --> K
    end
    
    subgraph "Capa de Utilidades"
        G --> L[SqlUtils.java]
        H --> L
        C --> M[Constants.java]
        D --> M
    end
    
    subgraph "Capa de Excepciones"
        C --> N[UserManagementException.java]
        D --> N
        G --> N
        H --> N
    end
    
    subgraph "Base de Datos"
        I --> O[(MySQL Database)]
    end
    
    style A fill:#e1f5fe
    style B fill:#e1f5fe
    style C fill:#f3e5f5
    style D fill:#f3e5f5
    style E fill:#f3e5f5
    style F fill:#f3e5f5
    style G fill:#e8f5e8
    style H fill:#e8f5e8
    style I fill:#fff3e0
    style J fill:#fce4ec
    style K fill:#fce4ec
    style L fill:#f1f8e9
    style M fill:#f1f8e9
    style N fill:#ffebee
    style O fill:#e0f2f1
```

## Diagrama de Clases Detallado

```mermaid
classDiagram
    class UserManagementApp {
        +main(String[] args)
    }
    
    class UserController {
        -UserService userService
        -GenderService genderService
        +UserController(UserService, GenderService)
        +showAllUsers()
        +showUserById(int id)
        +createUser(User user)
        +updateUser(User user)
        +deleteUser(int id)
        +showAllGenders()
    }
    
    class UserService {
        -UserDAO userDAO
        -GenderDAO genderDAO
        +UserService(UserDAO, GenderDAO)
        +getAllUsers() List~User~
        +getUserById(int id) User
        +createUser(User user) boolean
        +updateUser(User user) boolean
        +deleteUser(int id) boolean
        -validateUser(User user)
    }
    
    class GenderService {
        -GenderDAO genderDAO
        +GenderService(GenderDAO)
        +getAllGenders() List~Gender~
        +getGenderById(int genderId) Gender
        +genderExists(int genderId) boolean
    }
    
    class UserServiceInterface {
        <<interface>>
        +getAllUsers() List~User~
        +getUserById(int id) User
        +createUser(User user) boolean
        +updateUser(User user) boolean
        +deleteUser(int id) boolean
    }
    
    class GenderServiceInterface {
        <<interface>>
        +getAllGenders() List~Gender~
        +getGenderById(int genderId) Gender
        +genderExists(int genderId) boolean
    }
    
    class UserDAO {
        -DatabaseConfig databaseConfig
        +UserDAO(DatabaseConfig)
        +getAllUsers() List~User~
        +getUserById(int id) User
        +createUser(User user) int
        +updateUser(User user) void
        +deleteUser(int id) void
        +userExists(int id) boolean
        +userNameExists(String userName) boolean
        -mapResultSetToUser(ResultSet) User
    }
    
    class GenderDAO {
        -DatabaseConfig databaseConfig
        +GenderDAO(DatabaseConfig)
        +getAllGenders() List~Gender~
        +getGenderById(int genderId) User
        +genderExists(int genderId) boolean
    }
    
    class DatabaseConfig {
        -static DatabaseConfig instance
        -Connection connection
        -DatabaseConfig()
        +getInstance() DatabaseConfig
        +getConnection() Connection
        +closeConnection()
    }
    
    class User {
        -Integer id
        -String name
        -Integer age
        -Integer genderId
        -String userName
        -LocalDateTime insertDate
        -LocalDateTime updateDate
        -String genderName
        +getters()
        +setters()
    }
    
    class Gender {
        -Integer genderId
        -String genderName
        +getters()
        +setters()
    }
    
    class UserManagementException {
        -ErrorType errorType
        +UserManagementException(String, ErrorType)
        +UserManagementException(String, ErrorType, Throwable)
        +getErrorType() ErrorType
        +getErrorDescription() String
        +validationError(String) UserManagementException
        +userNotFound(int) UserManagementException
        +userAlreadyExists(String) UserManagementException
        +genderNotFound(int) UserManagementException
        +databaseError(String, Throwable) UserManagementException
    }
    
    class Constants {
        <<final>>
        +SQL_SELECT_ALL_USERS
        +SQL_SELECT_USER_BY_ID
        +SQL_INSERT_USER
        +SQL_UPDATE_USER
        +SQL_DELETE_USER
        +SQL_SELECT_ALL_GENDERS
        +SQL_SELECT_GENDER_BY_ID
        +MIN_NAME_LENGTH
        +MAX_NAME_LENGTH
        +MIN_AGE
        +MAX_AGE
        +MIN_USERNAME_LENGTH
        +MAX_USERNAME_LENGTH
    }
    
    class SqlUtils {
        +closeResources(ResultSet, PreparedStatement)
    }
    
    UserManagementApp --> UserController
    UserController --> UserService
    UserController --> GenderService
    UserService ..|> UserServiceInterface
    GenderService ..|> GenderServiceInterface
    UserService --> UserDAO
    UserService --> GenderDAO
    GenderService --> GenderDAO
    UserDAO --> DatabaseConfig
    GenderDAO --> DatabaseConfig
    UserDAO --> User
    GenderDAO --> Gender
    UserService --> User
    GenderService --> Gender
    UserService --> UserManagementException
    GenderService --> UserManagementException
    UserDAO --> UserManagementException
    GenderDAO --> UserManagementException
    UserDAO --> SqlUtils
    GenderDAO --> SqlUtils
    UserService --> Constants
    GenderService --> Constants
    UserDAO --> Constants
    GenderDAO --> Constants
```

## Patrones de Diseño Utilizados

### 1. **Patrón Singleton**
- **Clase**: `DatabaseConfig`
- **Propósito**: Garantizar una única instancia de conexión a la base de datos

### 2. **Patrón DAO (Data Access Object)**
- **Clases**: `UserDAO`, `GenderDAO`
- **Propósito**: Separar la lógica de acceso a datos de la lógica de negocio

### 3. **Patrón MVC (Model-View-Controller)**
- **Model**: `User`, `Gender`
- **View**: `UserManagementApp` (consola)
- **Controller**: `UserController`

### 4. **Inyección de Dependencias**
- **Implementación**: Constructor injection
- **Beneficio**: Desacoplamiento y testabilidad

### 5. **Patrón Strategy (Interfaces)**
- **Interfaces**: `UserServiceInterface`, `GenderServiceInterface`
- **Propósito**: Permitir diferentes implementaciones de servicios

## Flujo de Datos

```mermaid
sequenceDiagram
    participant App as UserManagementApp
    participant Controller as UserController
    participant Service as UserService
    participant DAO as UserDAO
    participant DB as DatabaseConfig
    participant MySQL as MySQL Database
    
    App->>Controller: showAllUsers()
    Controller->>Service: getAllUsers()
    Service->>DAO: getAllUsers()
    DAO->>DB: getConnection()
    DB->>MySQL: SELECT * FROM users
    MySQL-->>DB: ResultSet
    DB-->>DAO: Connection
    DAO-->>Service: List<User>
    Service-->>Controller: List<User>
    Controller-->>App: Display users
```

## Características de la Arquitectura

### ✅ **Principios SOLID Aplicados**
- **S**: Single Responsibility Principle - Cada clase tiene una responsabilidad específica
- **O**: Open/Closed Principle - Extensible a través de interfaces
- **L**: Liskov Substitution Principle - Las interfaces permiten sustitución
- **I**: Interface Segregation Principle - Interfaces específicas para cada servicio
- **D**: Dependency Inversion Principle - Dependencias a través de abstracciones

### ✅ **Clean Code**
- Nombres descriptivos y significativos
- Métodos pequeños y con una sola responsabilidad
- Comentarios JavaDoc completos
- Manejo centralizado de excepciones

### ✅ **Buenas Prácticas**
- Separación de capas clara
- Reutilización de conexiones a base de datos
- Constantes centralizadas
- Manejo de recursos con try-with-resources
- Validaciones en la capa de servicio

### ✅ **Manejo de Errores**
- Excepción personalizada `UserManagementException`
- Tipos de error específicos
- Mensajes descriptivos
- Propagación adecuada de errores

## Estructura de Paquetes

```
com.usermanagement/
├── config/          # Configuración de base de datos
├── controller/      # Controladores (Capa de presentación)
├── dao/            # Data Access Objects
├── exception/      # Excepciones personalizadas
├── model/          # Entidades/Modelos
├── service/        # Lógica de negocio
└── util/           # Utilidades y constantes
```

Esta arquitectura proporciona:
- **Mantenibilidad**: Código organizado y fácil de modificar
- **Escalabilidad**: Fácil agregar nuevas funcionalidades
- **Testabilidad**: Componentes desacoplados y con interfaces
- **Reutilización**: Componentes modulares y reutilizables 