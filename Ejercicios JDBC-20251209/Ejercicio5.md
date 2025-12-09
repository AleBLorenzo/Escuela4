Ejercicio 5: Operaciones CRUD básicas con Statement (Intermedio)
📂 ejercicio05_intermedio_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Ejecutar sentencias SQL desde Java utilizando Statement
Realizar operaciones CRUD (Create, Read, Update, Delete) básicas
Procesar resultados con ResultSet
Comprender la diferencia entre executeQuery() y executeUpdate()


📋 Descripción del ejercicio
Ahora que dominas las conexiones a diferentes SGBD, es momento de realizar operaciones reales sobre los datos. En este ejercicio trabajarás con sentencias SQL fijas utilizando la interfaz Statement para ejecutar operaciones CRUD sobre una base de datos de estudiantes.
Deberás crear una aplicación que:

Cree una tabla estudiantes con campos: id, nombre, apellidos, edad, curso
Inserte varios registros
Consulte y muestre todos los estudiantes
Actualice información de un estudiante específico
Elimine un estudiante
Procese los resultados utilizando ResultSet


🔧 Configuración del entorno
Base de datos a utilizar
Puedes elegir entre:

SQLite (recomendado para simplicidad)
PostgreSQL (si quieres práctica con Docker)

Dependencias (según elección)
Ya las configuraste en ejercicios anteriores.

📝 Especificaciones técnicas
Estructura de la tabla estudiantes
sqlCREATE TABLE estudiantes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,  -- SQLite
    -- id SERIAL PRIMARY KEY,              -- PostgreSQL
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    edad INTEGER CHECK(edad >= 16 AND edad <= 99),
    curso VARCHAR(50)
);
```

### Operaciones CRUD a implementar

**CREATE (INSERT)**
- Insertar al menos 5 estudiantes de ejemplo

**READ (SELECT)**
- Consultar todos los estudiantes
- Mostrar los datos formateados por consola

**UPDATE**
- Modificar el curso de un estudiante por su ID

**DELETE**
- Eliminar un estudiante por su ID

### Métodos clave a utilizar
- `Statement.executeUpdate(String sql)` → Para INSERT, UPDATE, DELETE
- `Statement.executeQuery(String sql)` → Para SELECT
- `ResultSet.next()` → Navegar por los resultados
- `ResultSet.getString()`, `getInt()` → Obtener datos tipados

---

## 🧪 Casos de prueba

### Caso 1: Creación de tabla e inserción
**Entrada**: Ejecutar la aplicación por primera vez
**Salida esperada (orientativa)**:
```
=== OPERACIONES CRUD CON STATEMENT ===

[1] Creando tabla estudiantes...
✓ Tabla creada correctamente

[2] Insertando estudiantes...
✓ Insertado: Ana García López
✓ Insertado: Carlos Pérez Martín
✓ Insertado: María Rodríguez Sánchez
✓ Insertado: Juan González Díaz
✓ Insertado: Laura Fernández Ruiz
→ Total insertados: 5 estudiantes
```

### Caso 2: Consulta de todos los estudiantes
**Entrada**: Listar todos los registros
**Salida esperada (orientativa)**:
```
[3] Listado de estudiantes:
┌────┬─────────────┬──────────────────────┬──────┬──────────┐
│ ID │   NOMBRE    │      APELLIDOS       │ EDAD │  CURSO   │
├────┼─────────────┼──────────────────────┼──────┼──────────┤
│  1 │ Ana         │ García López         │  18  │ DAM1     │
│  2 │ Carlos      │ Pérez Martín         │  20  │ DAM2     │
│  3 │ María       │ Rodríguez Sánchez    │  19  │ DAM1     │
│  4 │ Juan        │ González Díaz        │  21  │ DAM2     │
│  5 │ Laura       │ Fernández Ruiz       │  18  │ DAM1     │
└────┴─────────────┴──────────────────────┴──────┴──────────┘
Total: 5 estudiantes
```

### Caso 3: Actualización de datos
**Entrada**: Actualizar el curso del estudiante con ID=3
**Salida esperada (orientativa)**:
```
[4] Actualizando curso del estudiante ID=3...
✓ Estudiante actualizado correctamente
→ Filas afectadas: 1

[Verificación] Datos actualizados:
ID: 3 | María Rodríguez Sánchez | Edad: 19 | Curso: DAM2
```

### Caso 4: Eliminación de registro
**Entrada**: Eliminar estudiante con ID=5
**Salida esperada (orientativa)**:
```
[5] Eliminando estudiante ID=5...
✓ Estudiante eliminado correctamente
→ Filas afectadas: 1

[6] Listado actualizado:
Total: 4 estudiantes

💡 Conceptos clave

Statement: Interfaz para ejecutar sentencias SQL estáticas (sin parámetros)
executeUpdate(): Retorna el número de filas afectadas (INSERT, UPDATE, DELETE)
executeQuery(): Retorna un ResultSet con los resultados (SELECT)
ResultSet: Cursor que apunta a los resultados de una consulta
next(): Mueve el cursor a la siguiente fila (retorna false cuando no hay más filas)


📌 Pistas generales

Orden de operaciones: Crea la tabla primero, luego inserta, después consulta
Verificación: Tras cada operación de modificación, consulta los datos para verificar
Formato de salida: No es necesario crear tablas ASCII como en los ejemplos, un formato simple es suficiente
Control de errores: Verifica si executeUpdate() retorna 0 (ninguna fila afectada)
Navegación por ResultSet:

java   while(rs.next()) {
       int id = rs.getInt("id");
       String nombre = rs.getString("nombre");
       // ...
   }

SQL embebido: Las sentencias SQL son Strings, ten cuidado con las comillas


✅ Criterios de éxito

 La tabla estudiantes se crea correctamente
 Se insertan al menos 5 registros
 La consulta SELECT muestra todos los estudiantes correctamente
 La operación UPDATE modifica el registro esperado
 La operación DELETE elimina el registro correcto
 Se procesa correctamente el ResultSet
 Se muestran mensajes informativos tras cada operación
 Se gestionan excepciones SQLException
 Los recursos se cierran adecuadamente


🎯 Extensiones opcionales
Si terminas antes del tiempo estimado:

Añade un menú interactivo para elegir operaciones
Implementa búsqueda de estudiantes por nombre
Añade validación de datos (edad, campos vacíos)
Implementa un método para contar estudiantes por curso


⏱️ Tiempo estimado
2-3 horas
