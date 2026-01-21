Ejercicio 9: Aplicación CRUD Completa con Patrón DAO (Avanzado)
📂 ejercicio09_avanzado_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Implementar el patrón de diseño DAO (Data Access Object)
Separar la lógica de negocio del acceso a datos
Crear una aplicación CRUD completa con menú interactivo
Aplicar buenas prácticas de gestión de recursos (try-with-resources)
Diseñar una arquitectura de aplicación escalable y mantenible


📋 Descripción del ejercicio
En aplicaciones reales, no mezclamos el código SQL con la lógica de negocio. El patrón DAO nos permite abstraer y encapsular todo el acceso a datos. En este ejercicio crearás una aplicación completa de gestión de una biblioteca con arquitectura en capas.
Deberás crear:

Capa de modelo (POJOs/Entidades): Libro, Autor, Prestamo
Capa de acceso a datos (DAOs): Interfaces y sus implementaciones
Capa de utilidades: Gestión de conexiones
Capa de presentación: Menú interactivo de consola
Operaciones CRUD completas sobre múltiples tablas relacionadas


🔧 Configuración del entorno
Base de datos recomendada

PostgreSQL

Estructura de tablas
sqlCREATE TABLE autores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50),
    fecha_nacimiento DATE
);

CREATE TABLE libros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    isbn VARCHAR(13) UNIQUE NOT NULL,
    autor_id INTEGER REFERENCES autores(id) ON DELETE CASCADE,
    genero VARCHAR(50),
    anio_publicacion INTEGER,
    disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE prestamos (
    id SERIAL PRIMARY KEY,
    libro_id INTEGER REFERENCES libros(id),
    nombre_usuario VARCHAR(100) NOT NULL,
    fecha_prestamo DATE DEFAULT CURRENT_DATE,
    fecha_devolucion_esperada DATE,
    fecha_devolucion_real DATE,
    devuelto BOOLEAN DEFAULT FALSE
);
```

---

## 📝 Especificaciones técnicas

### Estructura de paquetes
```
src/
├── modelo/
│   ├── Autor.java
│   ├── Libro.java
│   └── Prestamo.java
├── dao/
│   ├── IAutorDAO.java
│   ├── AutorDAOImpl.java
│   ├── ILibroDAO.java
│   ├── LibroDAOImpl.java
│   ├── IPrestamoDAO.java
│   └── PrestamoDAOImpl.java
├── util/
│   └── ConexionDB.java
└── app/
    └── AplicacionBiblioteca.java
Clases modelo (ejemplo orientativo)
java// Libro.java
public class Libro {
    private int id;
    private String titulo;
    private String isbn;
    private int autorId;
    private String genero;
    private int anioPublicacion;
    private boolean disponible;
    
    // Constructores, getters, setters, toString
}
Interfaces DAO (ejemplo orientativo)
java// ILibroDAO.java
public interface ILibroDAO {
    void insertar(Libro libro) throws SQLException;
    Libro buscarPorId(int id) throws SQLException;
    List<Libro> listarTodos() throws SQLException;
    void actualizar(Libro libro) throws SQLException;
    void eliminar(int id) throws SQLException;
    List<Libro> buscarPorAutor(int autorId) throws SQLException;
    List<Libro> buscarDisponibles() throws SQLException;
}
```

### Gestión de conexiones (singleton)

La clase `ConexionDB` debe:
- Implementar patrón Singleton
- Proporcionar método `getConnection()`
- Gestionar el pool de conexiones (básico)
- Leer configuración desde archivo properties

---

## 🧪 Casos de prueba

### Caso 1: Menú principal
**Entrada**: Ejecutar la aplicación
**Salida esperada (orientativa)**:
```
╔════════════════════════════════════════╗
║   SISTEMA DE GESTIÓN DE BIBLIOTECA    ║
╚════════════════════════════════════════╝

Menú Principal:
  1. Gestión de Autores
  2. Gestión de Libros
  3. Gestión de Préstamos
  4. Consultas y Reportes
  5. Salir

Seleccione una opción:
```

### Caso 2: Insertar autor completo
**Entrada**: Opción 1 → Nuevo Autor
```
Nombre: Gabriel
Apellidos: García Márquez
Nacionalidad: Colombiana
Fecha nacimiento: 1927-03-06
```

**Salida esperada (orientativa)**:
```
[DAO] Insertando autor...
✓ Autor insertado correctamente
→ ID generado: 1
→ Nombre completo: Gabriel García Márquez
```

### Caso 3: Listar libros disponibles
**Entrada**: Opción 2 → Libros Disponibles
**Salida esperada (orientativa)**:
```
=== LIBROS DISPONIBLES ===

ID: 1
Título: Cien años de soledad
ISBN: 9780060883287
Autor: Gabriel García Márquez
Género: Realismo mágico
Año: 1967
────────────────────────────

ID: 2
Título: El amor en los tiempos del cólera
ISBN: 9780307389732
Autor: Gabriel García Márquez
Género: Romance
Año: 1985
────────────────────────────

Total: 2 libros disponibles
```

### Caso 4: Registrar préstamo
**Entrada**: Opción 3 → Nuevo Préstamo
```
ID del libro: 1
Nombre del usuario: Juan Pérez
Días de préstamo: 14
```

**Salida esperada (orientativa)**:
```
[DAO] Iniciando transacción...
  → Verificando disponibilidad del libro... ✓
  → Marcando libro como no disponible... ✓
  → Registrando préstamo... ✓
  → ID préstamo generado: 10
[DAO] Transacción completada (COMMIT)

✓ Préstamo registrado exitosamente
Fecha préstamo: 2025-11-18
Fecha devolución esperada: 2025-12-02
```

### Caso 5: Buscar libros por autor
**Entrada**: Opción 4 → Buscar por Autor → `García Márquez`
**Salida esperada (orientativa)**:
```
[DAO] Buscando libros del autor 'García Márquez'...

Encontrados 2 libros:

1. Cien años de soledad (1967) - Disponible
2. El amor en los tiempos del cólera (1985) - Prestado
```

---

## 💡 Conceptos clave

- **DAO (Data Access Object)**: Patrón que encapsula el acceso a datos
- **POJO (Plain Old Java Object)**: Clase simple con atributos, getters y setters
- **Interfaz DAO**: Define el contrato de operaciones sobre una entidad
- **Implementación DAO**: Contiene el código SQL y JDBC específico
- **Singleton**: Patrón que garantiza una única instancia (útil para conexiones)
- **Try-with-resources**: Gestión automática de recursos AutoCloseable

---

## 📌 Pistas generales

1. **Empezar por el modelo**: Define primero las clases de entidades
2. **Interfaces antes que implementaciones**: Define qué operaciones necesitas antes de cómo hacerlas
3. **Gestión de conexiones**: Crea un método centralizado para obtener conexiones
4. **Try-with-resources**: Usa esta estructura para Statement, PreparedStatement y ResultSet
5. **Transacciones**: Las operaciones complejas (ej. préstamo) deben ser transaccionales
6. **Excepciones**: Decide si propagarlas o manejarlas en cada capa
7. **Validaciones**: Valida datos antes de enviarlos al DAO
8. **Menú modular**: Cada opción del menú debe ser un método separado

---

## 🏗️ Arquitectura en capas
```
┌─────────────────────────────────┐
│   CAPA DE PRESENTACIÓN         │  ← Menú, entrada/salida
│   (AplicacionBiblioteca)       │
└────────────┬────────────────────┘
             │
┌────────────▼────────────────────┐
│   CAPA DE LÓGICA DE NEGOCIO    │  ← Validaciones, reglas
│   (Opcional: Services)          │
└────────────┬────────────────────┘
             │
┌────────────▼────────────────────┐
│   CAPA DE ACCESO A DATOS       │  ← DAOs, SQL
│   (AutorDAO, LibroDAO, etc.)   │
└────────────┬────────────────────┘
             │
┌────────────▼────────────────────┐
│   BASE DE DATOS                 │  ← PostgreSQL
└─────────────────────────────────┘

✅ Criterios de éxito

 Las clases modelo están correctamente definidas
 Las interfaces DAO declaran todas las operaciones necesarias
 Las implementaciones DAO usan PreparedStatement
 La gestión de conexiones está centralizada
 El menú es interactivo y funcional
 Todas las operaciones CRUD funcionan correctamente
 Se implementan al menos 2 consultas complejas
 Los préstamos utilizan transacciones
 Se usa try-with-resources donde sea apropiado
 El código está modularizado y bien organizado
 Las excepciones se gestionan adecuadamente


🎯 Funcionalidades mínimas requeridas
Gestión de Autores

 Insertar autor
 Listar todos los autores
 Buscar autor por ID
 Actualizar datos del autor
 Eliminar autor

Gestión de Libros

 Insertar libro
 Listar todos los libros
 Buscar libro por ISBN
 Listar libros disponibles
 Actualizar información del libro
 Eliminar libro

Gestión de Préstamos

 Registrar nuevo préstamo (transaccional)
 Registrar devolución de libro
 Listar préstamos activos
 Listar historial de préstamos
 Buscar préstamos por usuario

Consultas y Reportes

 Libros más prestados
 Libros por autor
 Préstamos pendientes de devolución
 Estadísticas generales (total libros, total autores, etc.)


🎯 Extensiones opcionales

Implementa paginación en las listas
Añade sistema de multas por retraso
Implementa búsqueda avanzada (múltiples criterios)
Crea un log de operaciones
Añade exportación de reportes a CSV
Implementa un sistema de reservas


⏱️ Tiempo estimado
5-6 horas
