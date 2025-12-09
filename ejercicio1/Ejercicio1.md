Ejercicio 1: Conexión a SQLite (Básico)
📂 ejercicio01_basico_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Comprender el concepto de base de datos embebida
Realizar la primera conexión JDBC a una base de datos
Gestionar correctamente la carga del driver JDBC
Verificar la conexión y manejar excepciones básicas


📋 Descripción del ejercicio
SQLite es una base de datos embebida que no requiere un servidor independiente, lo que la hace ideal para aprender los fundamentos de JDBC. En este ejercicio realizarás tu primera conexión a una base de datos utilizando el API JDBC de Java.
Deberás crear una aplicación Java que:

Cargue el driver JDBC de SQLite
Establezca una conexión con una base de datos SQLite
Verifique que la conexión se ha realizado correctamente
Cierre adecuadamente la conexión


🔧 Configuración del entorno
Dependencia Maven
xml<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.45.0.0</version>
</dependency>
Dependencia Gradle
gradleimplementation 'org.xerial:sqlite-jdbc:3.45.0.0'
```

---

## 📝 Especificaciones técnicas

### Información de conexión
- **Base de datos**: `biblioteca.db` (se creará automáticamente si no existe)
- **Ubicación**: En el directorio raíz del proyecto
- **URL de conexión**: `jdbc:sqlite:biblioteca.db`
- **Driver**: `org.sqlite.JDBC`

### Estructura de la aplicación

Tu aplicación debe tener como mínimo:
- Una clase principal con el método `main`
- Un método que gestione la conexión a la base de datos
- Manejo adecuado de excepciones

---

## 🧪 Casos de prueba

### Caso 1: Conexión exitosa
**Entrada**: Ejecutar la aplicación
**Salida esperada (orientativa)**:
```
=== PRUEBA DE CONEXIÓN A SQLITE ===
Intentando conectar a: jdbc:sqlite:biblioteca.db
✓ Conexión establecida correctamente
✓ Conexión cerrada correctamente
```

### Caso 2: Error en la conexión
**Entrada**: Modificar intencionadamente la URL de conexión a una ruta inválida
**Salida esperada (orientativa)**:
```
✗ Error al conectar con la base de datos
Detalle: [mensaje de error correspondiente]

💡 Conceptos clave

JDBC (Java Database Connectivity): API estándar de Java para conectar con bases de datos
Driver JDBC: Biblioteca específica que permite a Java comunicarse con un SGBD concreto
Connection: Objeto que representa una sesión con la base de datos
DriverManager: Clase que gestiona los drivers JDBC y establece conexiones


📌 Pistas generales

Carga del driver: Aunque en versiones modernas de JDBC no siempre es necesario, es buena práctica cargar explícitamente el driver
Gestión de recursos: Asegúrate de cerrar la conexión en un bloque finally o utiliza try-with-resources
Excepciones: Las operaciones JDBC lanzan SQLException, debes capturarlas adecuadamente
Archivo de base de datos: Si el archivo .db no existe, SQLite lo creará automáticamente


✅ Criterios de éxito

 La aplicación compila sin errores
 El driver JDBC se carga correctamente
 La conexión se establece sin lanzar excepciones
 Se muestra un mensaje confirmando la conexión exitosa
 La conexión se cierra correctamente
 Las excepciones se capturan y muestran mensajes informativos
 El código está correctamente documentado


⏱️ Tiempo estimado
1-2 horas
