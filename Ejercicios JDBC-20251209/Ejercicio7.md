Ejercicio 7: PreparedStatement y Prevención de SQL Injection (Intermedio)
📂 ejercicio07_intermedio_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Comprender la diferencia entre Statement y PreparedStatement
Utilizar sentencias preparadas con parámetros posicionales
Prevenir ataques de SQL Injection
Realizar operaciones CRUD seguras con PreparedStatement
Reutilizar sentencias preparadas para mejorar el rendimiento


📋 Descripción del ejercicio
Las sentencias SQL con valores concatenados (hard-coded) son peligrosas y vulnerables a SQL Injection. PreparedStatement soluciona este problema permitiendo parametrizar las consultas. En este ejercicio aprenderás a usar PreparedStatement para realizar operaciones CRUD de forma segura y eficiente.
Deberás crear una aplicación que:

Implemente un sistema de gestión de usuarios con login
Demuestre la vulnerabilidad de Statement ante SQL Injection
Implemente la misma funcionalidad de forma segura con PreparedStatement
Realice operaciones CRUD utilizando parámetros
Compare el rendimiento entre Statement y PreparedStatement


🔧 Configuración del entorno
Base de datos recomendada

MySQL (utiliza el contenedor del Ejercicio 3)

Tabla de trabajo
sqlCREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

📝 Especificaciones técnicas
Parte 1: Demostración de SQL Injection (Statement - INSEGURO)
Implementa un método de login usando Statement con concatenación de strings:
java// CÓDIGO INSEGURO - SOLO PARA DEMOSTRACIÓN
String sql = "SELECT * FROM usuarios WHERE username='" + username + 
             "' AND password='" + password + "'";
Prueba de inyección SQL: Intenta hacer login con:

Username: admin' OR '1'='1
Password: cualquiercosa

Parte 2: Implementación segura (PreparedStatement)
Implementa el login correcto usando PreparedStatement:
java// CÓDIGO SEGURO
String sql = "SELECT * FROM usuarios WHERE username=? AND password=?";
PreparedStatement pstmt = conexion.prepareStatement(sql);
pstmt.setString(1, username);
pstmt.setString(2, password);
Operaciones CRUD con PreparedStatement
INSERT
sqlINSERT INTO usuarios (username, password, email) VALUES (?, ?, ?)
UPDATE
sqlUPDATE usuarios SET email=?, activo=? WHERE username=?
DELETE
sqlDELETE FROM usuarios WHERE id=?
SELECT con filtros
sqlSELECT * FROM usuarios WHERE activo=? AND username LIKE ?
```

---

## 🧪 Casos de prueba

### Caso 1: SQL Injection con Statement (vulnerable)
**Entrada**: 
- Username: `' OR '1'='1' --`
- Password: `ignorado`

**Salida esperada (orientativa)**:
```
=== INTENTO DE LOGIN CON STATEMENT (INSEGURO) ===
Username: ' OR '1'='1' --
Password: ignorado

SQL generado:
SELECT * FROM usuarios WHERE username='' OR '1'='1' --' AND password='ignorado'

⚠️ ALERTA: ¡SQL INJECTION EXITOSO!
✗ Login exitoso sin credenciales válidas
→ Se obtuvieron todos los usuarios de la base de datos
→ ESTO ES UNA VULNERABILIDAD CRÍTICA
```

### Caso 2: Intento de SQL Injection con PreparedStatement (seguro)
**Entrada**:
- Username: `' OR '1'='1' --`
- Password: `ignorado`

**Salida esperada (orientativa)**:
```
=== INTENTO DE LOGIN CON PREPAREDSTATEMENT (SEGURO) ===
Username: ' OR '1'='1' --
Password: ignorado

✓ Sentencia preparada con parámetros
✓ Los caracteres especiales se escapan automáticamente

Resultado: Login fallido
→ No se encontró ningún usuario
→ SQL INJECTION BLOQUEADO CORRECTAMENTE
```

### Caso 3: Inserción de usuario con PreparedStatement
**Entrada**: Crear usuario `carlos` con email `carlos@email.com`
**Salida esperada (orientativa)**:
```
=== INSERCIÓN DE USUARIO ===
Insertando: carlos (carlos@email.com)
✓ Usuario insertado correctamente
→ ID generado: 5
```

### Caso 4: Actualización de datos
**Entrada**: Cambiar email del usuario `carlos`
**Salida esperada (orientativa)**:
```
=== ACTUALIZACIÓN DE USUARIO ===
Actualizando email de: carlos
Nuevo email: carlos.nuevo@email.com
✓ Usuario actualizado correctamente
→ Filas afectadas: 1
```

### Caso 5: Consulta con LIKE
**Entrada**: Buscar usuarios cuyo nombre contenga "car"
**Salida esperada (orientativa)**:
```
=== BÚSQUEDA DE USUARIOS ===
Patrón de búsqueda: %car%

Resultados encontrados:
  - carlos (carlos.nuevo@email.com)
  - carla (carla@email.com)
Total: 2 usuarios

💡 Conceptos clave

SQL Injection: Vulnerabilidad que permite ejecutar SQL arbitrario inyectando código malicioso
PreparedStatement: Sentencia precompilada con parámetros, que escapa caracteres especiales automáticamente
Parámetros posicionales: Marcadores ? que se rellenan con setXXX(posicion, valor)
Precompilación: El SGBD analiza la consulta una vez, mejorando el rendimiento en ejecuciones múltiples
Escape automático: PreparedStatement convierte caracteres especiales para evitar inyección


📌 Pistas generales

Orden de setters: Los parámetros se numeran desde 1, no desde 0
Tipos de datos: Usa el setter apropiado: setString(), setInt(), setBoolean(), setDate()
Reutilización: Puedes reutilizar un PreparedStatement con clearParameters() y nuevos setXXX()
LIKE con parámetros: Incluye el % en el valor: pstmt.setString(1, "%" + busqueda + "%")
NULL values: Usa setNull(posicion, Types.VARCHAR)
Claves generadas: Obtén IDs autogenerados con getGeneratedKeys()
No concatenar: NUNCA concatenes strings en PreparedStatement, usa siempre parámetros


🔐 Comparativa Statement vs PreparedStatement
Documenta en tu aplicación (comentarios o salida):
AspectoStatementPreparedStatementSeguridad❌ Vulnerable a SQL Injection✅ Previene SQL InjectionSintaxisConcatenación de stringsParámetros ?RendimientoParseo en cada ejecuciónPrecompilado, más rápido en múltiples ejecucionesLegibilidadPeor (strings complejos)Mejor (separación SQL/datos)ReutilizaciónNoSí (con clearParameters)Uso recomendadoNunca con datos de usuarioSiempre que haya parámetros

✅ Criterios de éxito

 Se demuestra la vulnerabilidad de Statement ante SQL Injection
 Se implementa login seguro con PreparedStatement
 Se realizan operaciones INSERT con parámetros
 Se realizan operaciones UPDATE con parámetros
 Se realizan operaciones DELETE con parámetros
 Se implementa búsqueda con LIKE y parámetros
 Se obtienen y muestran IDs autogenerados
 Se documenta la comparativa entre ambos métodos
 El código está correctamente comentado


🎯 Extensiones opcionales

Implementa un método genérico de inserción que acepte cualquier número de parámetros
Añade hash de contraseñas (BCrypt) antes de almacenar
Crea una capa de abstracción (DAO) que use PreparedStatement
Implementa un log de intentos de SQL Injection


⏱️ Tiempo estimado
3-4 horas
