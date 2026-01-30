# Sistema de Tienda Online – ShopEase

## Descripción del Proyecto

Este proyecto implementa el backend de una **tienda online** utilizando **Java, JPA/Hibernate y Maven**

El sistema permite gestionar categorías, productos, clientes, direcciones, pedidos y líneas de pedido, aplicando **relaciones avanzadas**, consultas **JPQL complejas**, uso de **DTOs**, **Criteria API** y optimización del acceso a datos.

---

## Requisitos del Sistema

* Java JDK 21 o superior
* Maven 3.9+
* MySQL 8.0+ o PostgreSQL 14+
* IDE Java (IntelliJ IDEA, Eclipse o VS Code)

---

## Instalación y Ejecución

1. Crear la base de datos:

```sql
CREATE DATABASE tienda_online_db;
```

2. Configurar el archivo `persistence.xml` con los datos de conexión:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/tienda_online_db"/>
<property name="jakarta.persistence.jdbc.user" value="usuario"/>
<property name="jakarta.persistence.jdbc.password" value="password"/>
```

3. Compilar el proyecto:

```bash
mvn clean compile
```

4. Ejecutar la aplicación:

```bash
mvn exec:java
```

---

## Diagrama de Relaciones (Modelo ER)

```
CATEGORIA 1 ──── N PRODUCTO
PRODUCTO 1 ──── N LINEA_PEDIDO
PEDIDO 1 ──── N LINEA_PEDIDO
CLIENTE 1 ──── N PEDIDO
CLIENTE 1 ──── N DIRECCION
CLIENTE 1 ──── 1 DIRECCION (PRINCIPAL)
```

---

## Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas**:

* **Modelo (`modelo`)**: Entidades JPA y enumeraciones
* **DAO (`dao`)**: Acceso a datos y consultas JPQL
* **DTO (`dto`)**: Proyecciones optimizadas para reportes
* **Servicio (`servicio`)**: Lógica de negocio y validaciones
* **Util (`util`)**: Gestión del `EntityManager`
* **Main**: Menú de consola

---

## Funcionalidades Implementadas

### Gestión de Catálogo

* Crear y listar categorías
* Crear productos asociados a categorías
* Actualizar stock de productos
* Buscar productos por nombre
* Listar productos con stock bajo

### Gestión de Clientes

* Registrar clientes
* Añadir múltiples direcciones
* Establecer dirección principal (OneToOne)
* Buscar clientes por email
* Listar clientes con pedidos

### Gestión de Pedidos

* Crear pedidos con múltiples líneas
* Añadir productos a pedidos
* Calcular total automáticamente
* Cambiar estado del pedido
* Cancelar pedidos

### Consultas y Reportes

* Productos más vendidos
* Clientes con más pedidos
* Ingresos por categoría
* Pedidos por estado
* Productos sin ventas
* Valor total del inventario

---

## Casos de Uso Probados

1. Alta de categoría y productos
2. Registro de cliente con varias direcciones
3. Asignación de dirección principal
4. Creación de pedido con múltiples productos
5. Descuento automático de stock
6. Generación correcta del total del pedido
7. Consultas JPQL con JOIN y GROUP BY
8. Uso de DTOs para reportes
9. Búsquedas dinámicas con Criteria API

---

## Dificultades Encontradas y Soluciones

### 1. Relaciones complejas entre entidades

**Problema:** Confusión entre relaciones OneToMany, ManyToOne y OneToOne.

**Solución:** Diseño previo del diagrama ER y definición clara del lado propietario de cada relación.

---

### 2. Problema N+1 en consultas

**Problema:** Múltiples consultas al cargar pedidos con líneas.

**Solución:** Uso de `JOIN FETCH` en JPQL para optimizar la carga.

---

### 3. Cálculo incorrecto del total del pedido

**Problema:** El total no se actualizaba al añadir líneas.

**Solución:** Centralizar el cálculo del total en un método privado del pedido.

---

### 4. Proyecciones lentas en reportes

**Problema:** Carga innecesaria de entidades completas.

**Solución:** Uso de DTOs con constructor en JPQL.

---

## Buenas Prácticas Aplicadas

* Separación clara de capas
* Uso de DTOs para optimización
* Validaciones de negocio en la capa servicio
* Manejo correcto de transacciones
* Uso de enums para estados
* Código limpio y legible

---

## Estado del Proyecto

✅ Proyecto funcional y probado

---

**Este ejercicio consolida el uso avanzado de JPA/Hibernate y prepara para proyectos reales de backend.** 🚀
