Ejercicio 2: Conexión a PostgreSQL con Docker (Básico)
📂 ejercicio02_basico_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Utilizar Docker para desplegar servicios de bases de datos
Conectar a una base de datos PostgreSQL mediante JDBC
Comprender las diferencias entre bases de datos embebidas y cliente-servidor
Gestionar credenciales y puertos de conexión


📋 Descripción del ejercicio
PostgreSQL es un sistema gestor de bases de datos relacional que se ejecuta como servidor independiente. En este ejercicio aprenderás a desplegar un contenedor Docker con PostgreSQL y a conectarte a él desde una aplicación Java.
Deberás:

Levantar un contenedor Docker con PostgreSQL
Configurar el driver JDBC de PostgreSQL en tu proyecto
Establecer conexión desde Java utilizando credenciales
Verificar la conexión
Cerrar adecuadamente los recursos


🔧 Configuración del entorno
Paso 1: Levantar PostgreSQL con Docker
Ejecuta el siguiente comando en tu terminal:
bashdocker run --name postgres-acceso-datos \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin123 \
  -e POSTGRES_DB=instituto \
  -p 5432:5432 \
  -d postgres:16
Verificar que el contenedor está activo:
bashdocker ps
Paso 2: Dependencias del proyecto
Maven:
xml<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
</dependency>
Gradle:
gradleimplementation 'org.postgresql:postgresql:42.7.1'
```

---

## 📝 Especificaciones técnicas

### Información de conexión
- **Host**: `localhost`
- **Puerto**: `5432`
- **Base de datos**: `instituto`
- **Usuario**: `admin`
- **Contraseña**: `admin123`
- **URL de conexión**: `jdbc:postgresql://localhost:5432/instituto`
- **Driver**: `org.postgresql.Driver`

### Estructura de la aplicación

Tu aplicación debe incluir:
- Clase principal con método `main`
- Método para establecer conexión
- Método para mostrar información de la conexión (versión de PostgreSQL, nombre de la BD)
- Gestión adecuada de excepciones y recursos

---

## 🧪 Casos de prueba

### Caso 1: Contenedor activo y conexión exitosa
**Entrada**: Ejecutar la aplicación con el contenedor Docker activo
**Salida esperada (orientativa)**:
```
=== CONEXIÓN A POSTGRESQL ===
Conectando a: jdbc:postgresql://localhost:5432/instituto
Usuario: admin
✓ Conexión establecida exitosamente
✓ Versión de PostgreSQL: PostgreSQL 16.x
✓ Base de datos: instituto
✓ Conexión cerrada correctamente
```

### Caso 2: Contenedor no activo
**Entrada**: Detener el contenedor Docker y ejecutar la aplicación
**Salida esperada (orientativa)**:
```
✗ No se pudo conectar al servidor PostgreSQL
Causa: Connection refused
Verifica que el contenedor Docker esté activo
```

### Caso 3: Credenciales incorrectas
**Entrada**: Modificar usuario o contraseña en el código
**Salida esperada (orientativa)**:
```
✗ Error de autenticación
Verifica el usuario y contraseña

💡 Conceptos clave

Docker: Plataforma de contenedores que permite ejecutar servicios de forma aislada
Cliente-Servidor: A diferencia de SQLite, PostgreSQL requiere un servidor activo
Puerto: Número que identifica un servicio en una máquina (PostgreSQL usa 5432 por defecto)
Credenciales: Usuario y contraseña necesarios para autenticarse en la base de datos


📌 Pistas generales

Docker Desktop: Asegúrate de tener Docker instalado y en ejecución
Orden de ejecución: Primero levanta el contenedor, luego ejecuta tu aplicación Java
Detener el contenedor: docker stop postgres-acceso-datos
Iniciar el contenedor existente: docker start postgres-acceso-datos
Eliminar el contenedor: docker rm -f postgres-acceso-datos
Información de conexión: Puedes obtener metadatos de la conexión usando métodos del objeto Connection


✅ Criterios de éxito

 El contenedor PostgreSQL se levanta correctamente
 La aplicación compila sin errores
 La conexión se establece correctamente con las credenciales proporcionadas
 Se muestra información sobre la conexión (versión de PostgreSQL, base de datos)
 Los errores se manejan adecuadamente (contenedor no activo, credenciales incorrectas)
 La conexión se cierra correctamente
 El código incluye comentarios explicativos


🐳 Comandos Docker útiles
bash# Ver contenedores activos
docker ps

# Ver todos los contenedores (activos e inactivos)
docker ps -a

# Detener contenedor
docker stop postgres-acceso-datos

# Iniciar contenedor existente
docker start postgres-acceso-datos

# Ver logs del contenedor
docker logs postgres-acceso-datos

# Eliminar contenedor
docker rm -f postgres-acceso-datos

⏱️ Tiempo estimado
1.5-2 horas
