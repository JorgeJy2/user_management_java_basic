#!/bin/bash

# Script para compilar y ejecutar el Sistema de Gestión de Usuarios
# Asegúrate de tener el MySQL Connector/J en el directorio raíz

echo "=== Sistema de Gestión de Usuarios ==="
echo ""

# Verificar si existe el MySQL Connector
if [ ! -f "mysql-connector-java.jar" ]; then
    echo "❌ Error: mysql-connector-java.jar no encontrado"
    echo "Por favor, descarga MySQL Connector/J y colócalo en el directorio raíz"
    echo "Puedes descargarlo desde: https://dev.mysql.com/downloads/connector/j/"
    exit 1
fi

echo "✅ MySQL Connector encontrado"
echo ""

# Crear directorio de clases si no existe
mkdir -p target/classes

echo "🔨 Compilando el proyecto..."
javac -cp ".:mysql-connector-java.jar" -d target/classes \
    src/main/java/com/usermanagement/*.java \
    src/main/java/com/usermanagement/*/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa"
    echo ""
    echo "🚀 Ejecutando la aplicación..."
    echo ""
    
    java -cp ".:mysql-connector-java.jar:target/classes" \
         com.usermanagement.UserManagementApp
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "✅ Aplicación ejecutada correctamente"
    else
        echo ""
        echo "❌ Error al ejecutar la aplicación"
        exit 1
    fi
else
    echo "❌ Error en la compilación"
    exit 1
fi 