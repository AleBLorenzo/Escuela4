# Sistema de Gestión de Biblioteca (Hibernate / JPA)

## 📌 Descripción

Este proyecto es una **aplicación de consola en Java** para la gestión del catálogo de la Biblioteca Municipal **"Cervantes"**, desarrollada con **Hibernate/JPA** siguiendo una arquitectura en capas (Modelo, DAO, Servicio y Vista).

Permite realizar un **CRUD completo de libros**, búsquedas avanzadas y estadísticas, aplicando validaciones de negocio y una correcta gestión de errores.

---

## 🛠 Tecnologías Utilizadas

* **Java 21**
* **Hibernate ORM 7**
* **JPA (Jakarta Persistence)**
* **MySQL 8** o **PostgreSQL 14**
* **Maven 3.9+**

---

## ⚙️ Instalación y Ejecución

### 1️⃣ Requisitos previos

* JDK 21 o superior
* Maven instalado
* MySQL o PostgreSQL
* IDE (IntelliJ IDEA, Eclipse o VS Code)


### 3️⃣ Crear la base de datos

#### MySQL

```sql
CREATE DATABASE biblioteca_db;
```

#### PostgreSQL

```sql
CREATE DATABASE biblioteca_db;
```

---

### 4️⃣ Configurar `persistence.xml`

Ejemplo para **MySQL**:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/biblioteca_db"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="tu_password"/>
<property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>

<property name="hibernate.hbm2ddl.auto" value="update"/>
<property name="hibernate.show_sql" value="true"/>
```

---

### 5️⃣ Compilar y ejecutar

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.biblioteca.Main"
```

---

## 🗂 Diagrama de Relaciones

```text
┌─────────────────────────────┐
│            LIBROS           │
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

* Entidad única: `Libro`
* Clave primaria autogenerada
* ISBN único y obligatorio

---

## 🏗 Arquitectura del Proyecto

```text
biblioteca-hibernate/
├── pom.xml
└── src/main/
    ├── java/com/biblioteca/
    │   ├── modelo/Libro.java
    │   ├── dao/LibroDAO.java
    │   ├── dao/LibroDAOImpl.java
    │   ├── servicio/BibliotecaServicio.java
    │   ├── util/JPAUtil.java
    │   └── Main.java
    └── resources/META-INF/persistence.xml
```

### Capas

* **Modelo**: Entidad JPA `Libro`
* **DAO**: Acceso a datos y consultas JPQL
* **Servicio**: Validaciones y lógica de negocio
* **Vista (Main)**: Menú interactivo por consola

---

## 📝 Casos de Uso Probados

### 1️⃣ Añadir libro

* ISBN único
* Año entre 1000 y el año actual
* Precio mayor o igual a 0
* Fecha de registro automática

✔ Resultado: libro almacenado con ID autogenerado

---

### 2️⃣ Buscar libro por ISBN

* Muestra todos los datos del libro
* Mensaje informativo si no existe

---

### 3️⃣ Buscar libro por ID

* Recuperación correcta usando `EntityManager.find()`

---

### 4️⃣ Listar todos los libros

* Ordenados por título
* Mensaje si la biblioteca está vacía

---

### 5️⃣ Listar libros disponibles

* Solo libros con `disponible = true`
* Ordenados por autor y título
* Conteo total mostrado

---

### 6️⃣ Actualizar libro

* Actualización parcial (campos opcionales)
* Confirmación previa
* Persistencia correcta con `merge()`

---

### 7️⃣ Eliminar libro

* Confirmación del usuario
* Eliminación segura

---

### 8️⃣ Buscar libros por autor

* Búsqueda parcial con `LIKE`
* Ignora mayúsculas/minúsculas

---

### 9️⃣ Estadísticas

* Total de libros
* Libros disponibles y no disponibles
* Precio promedio
* Libro más caro
* Libro más barato

---

## 📋 Ejemplo de Menú

```text
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
```

---

## ⚠ Dificultades Encontradas y Soluciones

| Problema                       | Solución                                      |
| ------------------------------ | --------------------------------------------- |
| ISBN duplicado                 | Validación previa con `buscarPorIsbn()`       |
| Error al introducir números    | Control con `try-catch` y limpieza del buffer |
| Estadísticas devolvían null    | Uso de `COALESCE` y comprobaciones previas    |
| Actualización parcial compleja | Permitir campos vacíos para mantener valores  |
| Cierre incorrecto de recursos  | Centralización en `JPAUtil`                   |

---

## ✅ Buenas Prácticas Aplicadas

* Separación de capas (DAO / Servicio / Vista)
* Validaciones centralizadas
* Uso correcto de JPQL
* Manejo seguro de transacciones
* Mensajes claros al usuario
* Código limpio y legible

---

## 👨‍💻 Uso de la Aplicación

1. Ejecutar `Main.java`
2. Navegar por el menú
3. Realizar operaciones sin reiniciar la aplicación
4. Salir cerrando correctamente recursos