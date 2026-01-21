# Ejercicio 1: Gestión de Biblioteca
## CRUD Completo con Hibernate/JPA

---

**Módulo:** Acceso a Datos  
**Ciclo:** Desarrollo de Aplicaciones Multiplataforma (DAM)  
**Nivel:** Básico  
**Tiempo estimado:** 3-4 horas

---

## 🎯 Objetivos de Aprendizaje

Al finalizar este ejercicio, serás capaz de:

1. Crear un proyecto Hibernate desde cero de forma autónoma
2. Diseñar e implementar una entidad completa
3. Implementar el patrón DAO (Data Access Object)
4. Realizar operaciones CRUD completas
5. Implementar un menú interactivo de consola
6. Gestionar errores y excepciones
7. Aplicar validaciones básicas

---

## 📋 Descripción del Problema

Debes desarrollar una aplicación de consola para gestionar el catálogo de libros de una biblioteca municipal. La aplicación permitirá realizar operaciones básicas sobre los libros: añadir nuevos libros, buscarlos, listarlos, actualizarlos y eliminarlos.

### Contexto

La Biblioteca Municipal "Cervantes" necesita modernizar su sistema de gestión de catálogo. Actualmente llevan un control manual en papel y necesitan un sistema informatizado que les permita:

- Registrar los nuevos libros que adquieren
- Buscar libros rápidamente por su código ISBN
- Listar todo el catálogo
- Actualizar información de los libros (precio, disponibilidad)
- Dar de baja libros deteriorados o perdidos

---

## 📊 Modelo de Datos

### Entidad: Libro

Un libro debe tener los siguientes atributos:

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| isbn | String | Código ISBN del libro | Único, no nulo, max 20 caracteres |
| titulo | String | Título del libro | No nulo, max 200 caracteres |
| autor | String | Nombre del autor | No nulo, max 100 caracteres |
| editorial | String | Editorial | Max 100 caracteres |
| año | Integer | Año de publicación | No nulo |
| precio | Double | Precio del libro | No nulo, >= 0 |
| disponible | Boolean | Si está disponible para préstamo | No nulo, default true |
| fechaRegistro | LocalDate | Fecha de alta en el sistema | No nulo, auto-generado |

### Diagrama ER

```
┌─────────────────────────────┐
│         LIBROS              │
├─────────────────────────────┤
│ id (PK)                     │
│ isbn (UNIQUE)               │
│ titulo                      │
│ autor                       │
│ editorial                   │
│ año                         │
│ precio                      │
│ disponible                  │
│ fecha_registro              │
└─────────────────────────────┘
```

---

## 🎨 Funcionalidades Requeridas

### Menú Principal

La aplicación debe mostrar un menú interactivo con las siguientes opciones:

```
=== GESTIÓN DE BIBLIOTECA ===
1. Añadir nuevo libro
2. Buscar libro por ISBN
3. Buscar libro por ID
4. Listar todos los libros
5. Listar libros disponibles
6. Actualizar libro
7. Eliminar libro
8. Buscar libros por autor
9. Estadísticas
0. Salir

Seleccione una opción:
```

### Descripción de Funcionalidades

#### 1. Añadir nuevo libro
- Solicitar todos los datos del libro al usuario
- Validar que el ISBN no exista ya en la base de datos
- Validar que el año sea razonable (entre 1000 y año actual)
- Validar que el precio sea positivo
- Mostrar mensaje de confirmación con el ID generado

#### 2. Buscar libro por ISBN
- Solicitar ISBN al usuario
- Mostrar toda la información del libro si existe
- Mostrar mensaje de error si no existe

#### 3. Buscar libro por ID
- Solicitar ID al usuario
- Mostrar toda la información del libro si existe
- Mostrar mensaje de error si no existe

#### 4. Listar todos los libros
- Mostrar listado completo de libros ordenados por título
- Si no hay libros, mostrar mensaje informativo
- Formato de salida claro y legible

#### 5. Listar libros disponibles
- Mostrar solo los libros con `disponible = true`
- Ordenados por autor y título
- Indicar cuántos libros disponibles hay en total

#### 6. Actualizar libro
- Solicitar ISBN del libro a actualizar
- Mostrar datos actuales
- Solicitar nuevos valores (permitir dejar en blanco para no cambiar)
- Confirmar la actualización
- Mostrar datos actualizados

#### 7. Eliminar libro
- Solicitar ISBN del libro a eliminar
- Mostrar información del libro
- Solicitar confirmación (S/N)
- Eliminar solo si confirma
- Mostrar mensaje de éxito o cancelación

#### 8. Buscar libros por autor
- Solicitar nombre del autor (búsqueda parcial)
- Mostrar todos los libros de ese autor
- Si no hay resultados, informar al usuario

#### 9. Estadísticas
- Total de libros en la biblioteca
- Total de libros disponibles
- Total de libros no disponibles
- Precio promedio de los libros
- Libro más caro
- Libro más barato

---

## 🏗️ Arquitectura del Proyecto

### Requisitos Previos

- **JDK 21** o superior
- **Maven 3.9+**
- **MySQL 8.0+** o **PostgreSQL 14+**
- IDE de tu elección (VS Code con Extension Pack for Java, IntelliJ IDEA, Eclipse)

### Dependencias Maven Requeridas

Configura tu `pom.xml` con las siguientes dependencias actualizadas:

```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

<dependencies>
    <!-- Hibernate Core -->
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>7.2.0.Final</version>
    </dependency>
    
    <!-- MySQL Driver -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.5.0</version>
    </dependency>
    
    <!-- PostgreSQL Driver (opcional) -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.8</version>
    </dependency>
</dependencies>
```

### Estructura de Paquetes

```
biblioteca-hibernate/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── biblioteca/
        │           ├── modelo/
        │           │   └── Libro.java
        │           ├── dao/
        │           │   ├── LibroDAO.java
        │           │   └── LibroDAOImpl.java
        │           ├── util/
        │           │   └── JPAUtil.java
        │           ├── servicio/
        │           │   └── BibliotecaServicio.java
        │           └── Main.java
        └── resources/
            └── META-INF/
                └── persistence.xml
```

### Responsabilidades de cada Clase

#### 1. `Libro.java` (modelo)
- Entidad JPA con todas las anotaciones
- Getters y setters
- Constructores
- `toString()`, `equals()`, `hashCode()`

#### 2. `LibroDAO.java` (interfaz)
- Define los métodos de acceso a datos
- Operaciones CRUD + consultas personalizadas

#### 3. `LibroDAOImpl.java` (implementación)
- Implementa la interfaz LibroDAO
- Contiene toda la lógica de acceso a datos con EntityManager

#### 4. `JPAUtil.java` (utilidad)
- Gestiona el EntityManagerFactory
- Proporciona EntityManagers

#### 5. `BibliotecaServicio.java` (lógica de negocio)
- Coordina las operaciones del DAO
- Aplica validaciones de negocio
- Gestiona transacciones complejas

#### 6. `Main.java` (interfaz de usuario)
- Menú interactivo
- Entrada/salida de datos
- Llamadas al servicio

---

## 📝 Especificaciones Técnicas

### Interfaz LibroDAO

Debes definir (como mínimo) los siguientes métodos:

```java
public interface LibroDAO {
    void guardar(Libro libro);
    Libro buscarPorId(Long id);
    Libro buscarPorIsbn(String isbn);
    List<Libro> listarTodos();
    List<Libro> listarDisponibles();
    List<Libro> buscarPorAutor(String autor);
    void actualizar(Libro libro);
    void eliminar(Long id);
    long contarTotal();
    long contarDisponibles();
    Double precioPromedio();
    Libro libroMasCaro();
    Libro libroMasBarato();
}
```

### Configuración de Base de Datos

**Crear base de datos:**
```sql
-- MySQL
CREATE DATABASE biblioteca_db;

-- PostgreSQL
CREATE DATABASE biblioteca_db;
```

**Configurar `persistence.xml` para MySQL o PostgreSQL** (a tu elección)

### Validaciones Requeridas

1. **ISBN único**: No permitir duplicados
2. **Año válido**: Entre 1000 y año actual
3. **Precio positivo**: >= 0
4. **Campos obligatorios**: ISBN, título, autor, año, precio no pueden ser null/vacíos

### Gestión de Errores

- Capturar excepciones de Hibernate
- Mostrar mensajes de error claros al usuario
- No mostrar stack traces completos (solo en modo debug)
- Validar entrada del usuario (números, opciones del menú)

---

## 🎨 Ejemplo de Ejecución

### Añadir libro

```
=== AÑADIR NUEVO LIBRO ===

ISBN: 978-84-376-0494-7
Título: Cien años de soledad
Autor: Gabriel García Márquez
Editorial: Sudamericana
Año de publicación: 1967
Precio: 18.50
¿Disponible? (S/N): S

✓ Libro guardado correctamente con ID: 1
```

### Listar libros

```
=== LISTADO DE LIBROS ===

ID: 1 | ISBN: 978-84-376-0494-7
Título: Cien años de soledad
Autor: Gabriel García Márquez
Editorial: Sudamericana
Año: 1967 | Precio: 18.50€ | Disponible: Sí
Fecha de registro: 2025-01-10
---

ID: 2 | ISBN: 978-84-663-0015-1
Título: El Quijote
Autor: Miguel de Cervantes
Editorial: Austral
Año: 1605 | Precio: 12.95€ | Disponible: No
Fecha de registro: 2025-01-10
---

Total: 2 libros
```

### Estadísticas

```
=== ESTADÍSTICAS DE LA BIBLIOTECA ===

📚 Total de libros: 15
✅ Libros disponibles: 12
❌ Libros no disponibles: 3
💰 Precio promedio: 16.75€
⬆️  Libro más caro: Don Quijote de la Mancha (25.00€)
⬇️  Libro más barato: El Principito (8.50€)
```

---

## ✅ Criterios de Éxito

Tu aplicación será correcta si cumple:

1. ✅ Compila sin errores
2. ✅ La tabla se crea automáticamente en la base de datos
3. ✅ Todas las operaciones del menú funcionan correctamente
4. ✅ Las validaciones funcionan (ISBN único, año válido, etc.)
5. ✅ Los datos persisten correctamente en la BD
6. ✅ El menú es claro e intuitivo
7. ✅ Los mensajes de error son informativos
8. ✅ El código está bien estructurado (separación de capas)
9. ✅ Se pueden ejecutar múltiples operaciones sin reiniciar
10. ✅ La aplicación se cierra correctamente liberando recursos

---

## 🚀 Pasos Sugeridos de Desarrollo

### Fase 1: Configuración (30 min)
1. Crear proyecto Maven
2. Configurar `pom.xml`
3. Crear base de datos
4. Configurar `persistence.xml`
5. Crear estructura de paquetes

### Fase 2: Modelo y Utilidades (30 min)
6. Crear entidad `Libro`
7. Crear clase `JPAUtil`
8. Probar conexión básica

### Fase 3: Capa DAO (1 hora)
9. Crear interfaz `LibroDAO`
10. Implementar `LibroDAOImpl`
11. Probar operaciones CRUD básicas

### Fase 4: Servicio (30 min)
12. Crear clase `BibliotecaServicio`
13. Implementar validaciones
14. Probar validaciones

### Fase 5: Interfaz de Usuario (1 hora)
15. Crear menú principal
16. Implementar cada opción del menú
17. Gestión de excepciones y validaciones de entrada

### Fase 6: Funcionalidades Avanzadas (1 hora)
18. Implementar búsqueda por autor
19. Implementar estadísticas
20. Refinar formato de salida

### Fase 7: Pruebas y Refinamiento (30 min)
21. Probar todos los flujos
22. Corregir errores
23. Mejorar experiencia de usuario

---

## 💡 Pistas y Consejos

### Pista 1: Búsqueda parcial de autor
Para buscar por nombre parcial, usa LIKE en JPQL:
```java
"SELECT l FROM Libro l WHERE LOWER(l.autor) LIKE LOWER(:patron)"
```

### Pista 2: Validación de ISBN único
Antes de guardar, consulta si ya existe:
```java
Libro existe = buscarPorIsbn(isbn);
if (existe != null) {
    throw new IllegalArgumentException("ISBN ya existe");
}
```

### Pista 3: Lectura segura de números
```java
Scanner scanner = new Scanner(System.in);
int opcion = -1;
try {
    opcion = scanner.nextInt();
    scanner.nextLine(); // Limpiar buffer
} catch (InputMismatchException e) {
    scanner.nextLine(); // Limpiar buffer
    System.out.println("Opción inválida");
}
```

### Pista 4: Actualización con valores opcionales
Si el usuario deja en blanco, mantener valor anterior:
```java
String nuevoTitulo = scanner.nextLine();
if (!nuevoTitulo.isBlank()) {
    libro.setTitulo(nuevoTitulo);
}
```

### Pista 5: Estadísticas con JPQL
```java
// Promedio
"SELECT AVG(l.precio) FROM Libro l"

// Máximo
"SELECT l FROM Libro l ORDER BY l.precio DESC"
// Luego: .setMaxResults(1)
```

---

## 🎓 Conceptos que Practicarás

- ✅ Mapeo de entidades con JPA
- ✅ Patrón DAO (Data Access Object)
- ✅ Operaciones CRUD completas
- ✅ Consultas JPQL básicas
- ✅ Gestión de transacciones
- ✅ Validaciones de datos
- ✅ Manejo de excepciones
- ✅ Arquitectura en capas
- ✅ Interacción con usuario por consola

---

## 📚 Recursos de Apoyo

### Documentación JPA
- Anotaciones: `@Entity`, `@Table`, `@Column`, `@Id`, `@GeneratedValue`
- EntityManager: `persist()`, `find()`, `merge()`, `remove()`
- JPQL: `SELECT`, `WHERE`, `ORDER BY`, `LIKE`

### Documentación Java
- `Scanner` para entrada de usuario
- `LocalDate` para fechas
- `List` y `ArrayList`
- Try-catch para excepciones

---

## 🔍 Preguntas de Reflexión

1. ¿Por qué usamos una interfaz y una implementación para el DAO?
2. ¿Qué ventajas tiene separar la capa de servicio del DAO?
3. ¿Qué pasa si intentamos guardar un libro con ISBN duplicado?
4. ¿Cómo afecta `hbm2ddl.auto=update` al esquema de la BD?
5. ¿Por qué es importante cerrar el EntityManager después de cada operación?

---

## 📤 Entrega

### Formato de Entrega

1. Código fuente completo del proyecto
2. Archivo `README.md` con:
   - Instrucciones de instalación
   - Instrucciones de ejecución
   - Credenciales de base de datos usadas
   - Dificultades encontradas y cómo las resolviste

### Estructura del ZIP

```
Apellido_Nombre_Ejercicio1.zip
├── src/
├── pom.xml
└── README.md
```

---

## 🌟 Extensiones Opcionales (Para Nota Extra)

Si terminas antes del tiempo estimado, puedes implementar:

1. **Exportar catálogo a CSV**: Guardar listado en archivo
2. **Importar libros desde CSV**: Cargar libros desde archivo
3. **Búsqueda avanzada**: Por rango de precios, por año
4. **Ordenamiento personalizado**: Permitir al usuario elegir criterio
5. **Paginación**: Si hay muchos libros, mostrar de 10 en 10
6. **Configuración de BD por properties**: Leer config desde archivo externo
7. **Logs con Log4j**: En lugar de System.out.println

---

**¡Mucho éxito en tu desarrollo!** 🚀

Recuerda: La clave está en ir paso a paso. Si te atascas en algo, revisa los ejemplos del Ejercicio 0 y consulta la documentación.
