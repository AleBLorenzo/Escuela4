# Sistema de Gestión de Empleados y Departamentos

## 📌 Descripción

Este proyecto es un sistema de gestión de recursos humanos para una empresa, desarrollado en **Java** con **Hibernate/JPA**. Permite:

* Gestionar **departamentos** y **empleados**.
* Mantener la relación **bidireccional** entre empleados y departamentos.
* Realizar **consultas estadísticas** como salario promedio o empleados sin departamento.
* Reasignar empleados entre departamentos y eliminar registros de forma segura.

---

## 🛠 Tecnologías

* **Java 21**
* **Hibernate 7**
* **JPA**
* **MySQL 8** o **PostgreSQL 14**
* Maven para gestión de dependencias

---

## ⚙ Instalación y Ejecución

1. Configurar la base de datos en `persistence.xml`:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/empresa_db"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="tu_contraseña"/>
<property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
```

2. Crear la base de datos:

```sql
CREATE DATABASE empresa_db;
```

3. Compilar y ejecutar:

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.example.Main"
```

4. Seguir las opciones del menú en consola.

---

## 🗂 Diagrama de Relaciones

```text
┌─────────────────────────────┐         ┌─────────────────────────────┐
│      DEPARTAMENTOS          │         │        EMPLEADOS            │
├─────────────────────────────┤         ├─────────────────────────────┤
│ id (PK)                     │ 1     N │ id (PK)                     │
│ nombre                      │◄────────│ nombre                      │
│ codigo                      │         │ apellidos                   │
│ ubicacion                   │         │ email                       │
│ presupuesto                 │         │ salario                     │
│ fechaCreacion               │         │ fechaContratacion           │
│ empleados                   │         │ activo                      │
└─────────────────────────────┘         │ departamento_id (FK)        │
                                        └─────────────────────────────┘
```

**Relación:**

* `Departamento` 1:N `Empleado`
* `Empleado` es el **lado propietario** de la relación (contiene la FK).
* Métodos helper en `Departamento` aseguran sincronía bidireccional:

```java
departamento.addEmpleado(empleado);
departamento.removeEmpleado(empleado);
```

---

## 📝 Casos de Uso Probados

### 1. Crear departamentos

* Nombre: Desarrollo, Código: DEV, Presupuesto: 500000
* Nombre: Marketing, Código: MKT, Presupuesto: 200000

### 2. Crear empleados

* Juan García, [juan.garcia@techcorp.com](mailto:juan.garcia@techcorp.com), salario: 45000, asignado a DEV
* María López, [maria.lopez@techcorp.com](mailto:maria.lopez@techcorp.com), salario: 48000, asignada a DEV
* Pedro Martínez, [pedro.martinez@techcorp.com](mailto:pedro.martinez@techcorp.com), salario: 42000, sin departamento

### 3. Listar empleados y departamentos

* Se listan correctamente con ID generado y departamento asignado.

### 4. Reasignación de empleados

* Pedro Martínez reasignado a Marketing usando `reasignarEmpleado()`.

### 5. Consultas estadísticas

* Empleados sin departamento
* Departamento con más empleados
* Salario promedio por departamento
* Empleados con salario mayor a X
* Conteo de empleados por departamento
* Empleados contratados en rango de fechas

---

## 📋 Ejemplo de Menú de Consola

```**Autor:** Tu Nombre
**Fecha:** Enero 2026
=== SISTEMA DE GESTIÓN DE EMPLEADOS ===

1. Gestión de departamentos
2. Gestión de empleados
3. Consultas y estadísticas
0. Salir
```

### Gestión de departamentos:

```
--- GESTIÓN DE DEPARTAMENTOS ---
1. Crear nuevo departamento
2. Listar todos los departamentos
3. Buscar departamento por código
4. Actualizar departamento
5. Eliminar departamento
6. Ver empleados de un departamento
0. Volver
```

### Gestión de empleados:

```
--- GESTIÓN DE EMPLEADOS ---
1. Añadir nuevo empleado
2. Listar todos los empleados
3. Buscar empleado por email
4. Actualizar empleado
5. Eliminar empleado
6. Reasignar empleado a otro departamento
0. Volver
```

### Consultas y estadísticas:

```
--- CONSULTAS Y ESTADÍSTICAS ---
1. Listar empleados sin departamento
2. Departamentos con más empleados
3. Salario promedio por departamento
4. Empleados con salario superior a X
5. Contar empleados por departamento
6. Empleados contratados en un rango de fechas
0. Volver
```

---

## ⚠ Dificultades Encontradas y Soluciones

| Problema                                          | Solución                                                                                                                             |
| ------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| `Empleado` no se guardaba correctamente, ID null  | Inicializar campos obligatorios (`activo=true`, `fechaContratacion=LocalDate.now()`) antes de persistir                              |
| Sincronía bidireccional rota                      | Crear métodos helper `addEmpleado()` y `removeEmpleado()` en `Departamento` y usarlos siempre al asignar o reasignar empleados       |
| Problema N+1 al listar empleados con departamento | Usar `JOIN FETCH` en consultas JPQL para cargar departamento junto a empleados                                                       |
| Validaciones de negocio                           | Implementar verificaciones de email único y salario > 0 en `EmpresaService` antes de persistir                                       |
| Eliminación de departamento con empleados         | Preguntar al usuario si desea reasignar, dejar sin departamento o cancelar eliminación; usar métodos helper para actualizar relación |

---

## ✅ Buenas prácticas aplicadas

* Relaciones **bidireccionales** consistentes
* Uso de **CascadeType.ALL** y **orphanRemoval**
* **FetchType.LAZY** para optimización
* Métodos DAO y Service separados
* Validaciones de negocio centralizadas
* Transacciones con commit y rollback en caso de error

---

## 👨‍💻 Uso

Ejecutar `Main.java` y navegar por los menús:

```
1. Gestión de departamentos
2. Gestión de empleados
3. Consultas y estadísticas
0. Salir
```

Cada menú permite crear, listar, actualizar, eliminar y consultar estadísticas de forma interactiva.

