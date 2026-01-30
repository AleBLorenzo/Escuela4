# 📚 Sistema de Gestión de Academia

## 🧾 Descripción del Proyecto

Este proyecto implementa un **sistema completo de gestión para una academia de formación tecnológica (TechAcademy)** utilizando **Java, JPA/Hibernate y Maven**.

El objetivo principal es aplicar **relaciones ManyToMany mediante entidades intermedias con atributos adicionales**, el uso de **claves compuestas**, **validaciones**, **consultas JPQL complejas**, **optimización con EntityGraph** y **lógica de negocio realista**.

---

## 🎯 Objetivos Alcanzados

* Implementación de relaciones ManyToMany con entidad intermedia
* Uso de claves compuestas mediante `@EmbeddedId`
* Gestión de matrículas y asignaciones con atributos propios
* Validaciones con Bean Validation
* Uso de enumerados con lógica de negocio
* Consultas JPQL avanzadas con JOIN, GROUP BY y HAVING
* Optimización del rendimiento con `@EntityGraph`
* Arquitectura en capas (modelo, DAO, servicio)

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas**:

* **modelo** → Entidades JPA y enums
* **dao** → Acceso a datos y consultas JPQL
* **servicio** → Lógica de negocio y validaciones
* **util** → Gestión del EntityManager
* **main** → Menú de consola

---

## 🗂️ Modelo de Datos

El sistema está compuesto por las siguientes entidades:

* **Curso**
* **Estudiante**
* **Profesor**
* **Aula**
* **Matrícula** (entidad intermedia Estudiante–Curso)
* **Asignación** (entidad intermedia Profesor–Curso)
* **Evaluación**

📌 El diagrama entidad–relación se incluye en el archivo `docs/modelo_er.png`.

---

## ⚙️ Instalación y Ejecución

### Requisitos

* JDK 21+
* Maven 3.9+
* MySQL 8 / PostgreSQL 14

### Pasos

1. Crear la base de datos:

```sql
CREATE DATABASE academia_db;
```

2. Configurar `persistence.xml` con los datos de conexión.

3. Compilar el proyecto:

```bash
mvn clean compile
```

4. Ejecutar la aplicación:

```bash
mvn exec:java
```

---

## 🧩 Funcionalidades Implementadas

### Gestión de Cursos

* Alta, búsqueda y listado de cursos
* Asignación de aula
* Consulta de profesores y matrículas

### Gestión de Estudiantes

* Registro de estudiantes
* Matriculación en cursos
* Baja de matrículas
* Registro de evaluaciones
* Cálculo automático de nota final

### Gestión de Profesores

* Registro de profesores
* Asignación a cursos con rol
* Cálculo de carga horaria

### Reportes y Estadísticas

* Cursos con más matriculados
* Estudiantes con mejor promedio
* Ingresos totales por curso
* Tasa de abandono
* Cursos por finalizar
* Profesores con mayor carga

---

## 🧪 Casos de Uso Probados

1. Matricular estudiante validando cupo máximo
2. Evitar matrículas duplicadas activas
3. Asignar profesor con rol y horas semanales
4. Registrar evaluaciones y calcular promedio
5. Consultas estadísticas con JPQL
6. Optimización de carga con EntityGraph

---

## ⚠️ Dificultades Encontradas y Soluciones

### 1. Claves compuestas

**Problema:** Errores al persistir entidades intermedias.

**Solución:** Uso correcto de `@EmbeddedId` junto con `@MapsId`.

---

### 2. ManyToMany con atributos

**Problema:** No era posible usar ManyToMany directa.

**Solución:** Crear entidades intermedias (`Matricula`, `Asignacion`).

---

### 3. Problema N+1

**Problema:** Demasiadas consultas al listar cursos.

**Solución:** Implementación de `@EntityGraph`.

---

### 4. Validaciones complejas

**Problema:** Validar edad mínima del estudiante.

**Solución:** Uso de `@AssertTrue` con lógica personalizada.

---

## ✅ Estado del Proyecto

✔ Proyecto completo, funcional y probado

---

**Este ejercicio integra todos los conceptos avanzados de JPA/Hibernate vistos en el módulo.** 🚀
