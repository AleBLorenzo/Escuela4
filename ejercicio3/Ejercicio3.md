Ejercicio 3: Conexión a MySQL con Docker (Básico)
📂 ejercicio03_basico_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Desplegar un servidor MySQL mediante Docker
Identificar diferencias en la cadena de conexión entre PostgreSQL y MySQL
Gestionar zonas horarias en MySQL
Aplicar buenas prácticas en la gestión de conexiones


📋 Descripción del ejercicio
MySQL es otro sistema gestor de bases de datos ampliamente utilizado. En este ejercicio aprenderás a conectarte a MySQL, identificando las particularidades de su driver JDBC y las diferencias respecto a PostgreSQL.
Deberás:

Desplegar un contenedor Docker con MySQL
Configurar el driver JDBC de MySQL (Connector/J)
Establecer conexión desde Java
Comparar las diferencias con la conexión a PostgreSQL del ejercicio anterior
Gestionar correctamente los recursos


🔧 Configuración del entorno
Paso 1: Levantar MySQL con Docker
bashdocker run --name mysql-acceso-datos \
  -e MYSQL_ROOT_PASSWORD=root123 \
  -e MYSQL_DATABASE=academia \
  -e MYSQL_USER=usuario \
  -e MYSQL_PASSWORD=usuario123 \
  -p 3306:3306 \
  -d mysql:8.0
Verificar que el contenedor está activo:
bashdocker ps | grep mysql
Paso 2: Dependencias del proyecto
Maven:
xml<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.2.0</version>
</dependency>
Gradle:
gradleimplementation 'com.mysql:mysql-connector-j:8.2.0'
```

---

## 📝 Especificaciones técnicas

### Información de conexión
- **Host**: `localhost`
- **Puerto**: `3306`
- **Base de datos**: `academia`
- **Usuario**: `usuario`
- **Contraseña**: `usuario123`
- **URL de conexión básica**: `jdbc:mysql://localhost:3306/academia`
- **URL con parámetros**: `jdbc:mysql://localhost:3306/academia?serverTimezone=UTC`
- **Driver**: `com.mysql.cj.jdbc.Driver`

### Estructura de la aplicación

Tu aplicación debe incluir:
- Clase principal con método `main`
- Método para establecer la conexión
- Método para mostrar información de la conexión (versión MySQL, usuario conectado)
- Comparación explícita con PostgreSQL (en comentarios o salida por consola)
- Gestión de excepciones y recursos

---

## 🧪 Casos de prueba

### Caso 1: Conexión exitosa con serverTimezone
**Entrada**: Ejecutar con URL completa incluyendo `serverTimezone=UTC`
**Salida esperada (orientativa)**:
```
=== CONEXIÓN A MYSQL ===
URL: jdbc:mysql://localhost:3306/academia?serverTimezone=UTC
Usuario: usuario
✓ Conexión establecida exitosamente
✓ Versión MySQL: 8.0.x
✓ Base de datos actual: academia
✓ Usuario conectado: usuario@localhost
✓ Conexión cerrada correctamente
```

### Caso 2: Conexión sin serverTimezone
**Entrada**: Ejecutar sin el parámetro `serverTimezone`
**Comportamiento**: Puede funcionar o lanzar advertencia (depende de configuración)
**Acción**: Documentar en comentarios qué ocurre

### Caso 3: Credenciales incorrectas
**Entrada**: Usuario o contraseña erróneos
**Salida esperada (orientativa)**:
```
✗ Error de autenticación en MySQL
Access denied for user 'usuario'@'localhost'

💡 Conceptos clave

Connector/J: Driver JDBC oficial de MySQL
serverTimezone: Parámetro necesario en MySQL 8.0+ para especificar la zona horaria
Puerto por defecto: MySQL utiliza el puerto 3306 (diferente al 5432 de PostgreSQL)
Diferencias de sintaxis: Aunque JDBC estandariza, cada SGBD tiene particularidades en sus URLs


📌 Pistas generales

Zona horaria: Si omites serverTimezone, MySQL puede lanzar excepciones o warnings según la configuración
Espera de inicio: MySQL puede tardar unos segundos en estar completamente disponible tras iniciar el contenedor
Comparativa: Reflexiona sobre las diferencias entre MySQL y PostgreSQL:

Sintaxis de URL
Parámetros adicionales
Puertos por defecto
Comportamiento del driver


Metadatos: Utiliza DatabaseMetaData para obtener información del servidor


🔄 Comparativa PostgreSQL vs MySQL
Incluye en tu aplicación (como comentario o salida) una tabla comparativa:
AspectoPostgreSQLMySQLPuerto por defecto54323306Formato URLjdbc:postgresql://host:port/dbjdbc:mysql://host:port/dbDriverorg.postgresql.Drivercom.mysql.cj.jdbc.DriverParámetros especiales-serverTimezone

✅ Criterios de éxito

 El contenedor MySQL se inicia correctamente
 La aplicación establece conexión exitosa
 Se incluye el parámetro serverTimezone en la URL
 Se muestra información del servidor (versión, usuario, base de datos)
 Se documenta la comparativa con PostgreSQL
 Las excepciones se gestionan adecuadamente
 La conexión se cierra correctamente
 El código está bien comentado


🐳 Comandos Docker útiles para MySQL
bash# Ver logs del contenedor MySQL
docker logs mysql-acceso-datos

# Acceder al cliente MySQL dentro del contenedor
docker exec -it mysql-acceso-datos mysql -u usuario -p

# Detener MySQL
docker stop mysql-acceso-datos

# Iniciar MySQL
docker start mysql-acceso-datos

# Eliminar contenedor
docker rm -f mysql-acceso-datos

⏱️ Tiempo estimado
1.5-2 horas
