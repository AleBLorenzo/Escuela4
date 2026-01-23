# Ejercicio 2: Sistema de Gestión de Empleados y Departamentos
## Relaciones ManyToOne y OneToMany en Hibernate/JPA

---

**Módulo:** Acceso a Datos  
**Ciclo:** Desarrollo de Aplicaciones Multiplataforma (DAM)  
**Nivel:** Intermedio  
**Tiempo estimado:** 4-5 horas

---

## 🎯 Objetivos de Aprendizaje

Al finalizar este ejercicio, serás capaz de:

1. Diseñar y crear relaciones **@ManyToOne** y **@OneToMany** entre entidades
2. Comprender y aplicar el concepto de **lado propietario** e **inverso**
3. Implementar relaciones **bidireccionales** correctamente
4. Utilizar **Cascade** para operaciones en cascada
5. Aplicar **FetchType.LAZY** para optimizar consultas
6. Realizar consultas JPQL con **JOIN** entre entidades relacionadas
7. Mantener la **sincronización bidireccional** entre entidades
8. Gestionar claves foráneas en el modelo ORM

---

## 📋 Descripción del Problema

Debes desarrollar un sistema de gestión de recursos humanos para una empresa que necesita organizar sus empleados por departamentos. La aplicación debe permitir gestionar tanto departamentos como empleados, manteniendo la relación entre ambos.

### Contexto

La empresa **TechCorp Solutions** está creciendo rápidamente y necesita un sistema para:

- Gestionar los diferentes departamentos de la empresa (IT, Marketing, RRHH, Ventas, etc.)
- Registrar empleados y asignarlos a departamentos
- Consultar qué empleados pertenecen a cada departamento
- Conocer el departamento de cada empleado
- Realizar operaciones estadísticas (salario promedio, empleados por departamento, etc.)
- Reasignar empleados entre departamentos
- Gestionar altas y bajas de empleados y departamentos

---

## 📊 Modelo de Datos

### Entidades y Relación

```
┌─────────────────────────────┐         ┌─────────────────────────────┐
│      DEPARTAMENTOS          │         │        EMPLEADOS            │
├─────────────────────────────┤         ├─────────────────────────────┤
│ id (PK)                     │ 1     N │ id (PK)                     │
│ nombre                      │◄────────│ nombre                      │
│ codigo                      │         │ apellidos                   │
│ ubicacion                   │         │ email                       │
│ presupuesto                 │         │ salario                     │
│ fecha_creacion              │         │ fecha_contratacion          │
│                             │         │ activo                      │
│                             │         │ departamento_id (FK)        │
└─────────────────────────────┘         └─────────────────────────────┘
```

**Relación:** Un **Departamento** tiene muchos **Empleados** (OneToMany)  
Un **Empleado** pertenece a un **Departamento** (ManyToOne)

### Entidad: Departamento

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| nombre | String | Nombre del departamento | No nulo, max 100 caracteres |
| codigo | String | Código único del departamento | Único, no nulo, max 10 caracteres |
| ubicacion | String | Ubicación física | Max 100 caracteres |
| presupuesto | Double | Presupuesto anual | >= 0 |
| fechaCreacion | LocalDate | Fecha de creación del departamento | No nulo, auto-generado |
| empleados | List\<Empleado\> | Lista de empleados | Relación OneToMany |

### Entidad: Empleado

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| nombre | String | Nombre del empleado | No nulo, max 50 caracteres |
| apellidos | String | Apellidos | No nulo, max 100 caracteres |
| email | String | Correo electrónico | Único, no nulo, max 100 caracteres |
| salario | Double | Salario anual | No nulo, >= 0 |
| fechaContratacion | LocalDate | Fecha de ingreso | No nulo, auto-generado |
| activo | Boolean | Si está activo en la empresa | No nulo, default true |
| departamento | Departamento | Departamento al que pertenece | Relación ManyToOne, nullable |

---

## 🎨 Funcionalidades Requeridas

### Menú Principal

```
=== SISTEMA DE GESTIÓN DE EMPLEADOS ===

GESTIÓN DE DEPARTAMENTOS
1. Crear nuevo departamento
2. Listar todos los departamentos
3. Buscar departamento por código
4. Actualizar departamento
5. Eliminar departamento
6. Ver empleados de un departamento

GESTIÓN DE EMPLEADOS
7. Añadir nuevo empleado
8. Listar todos los empleados
9. Buscar empleado por email
10. Actualizar empleado
11. Eliminar empleado
12. Reasignar empleado a otro departamento

CONSULTAS Y ESTADÍSTICAS
13. Listar empleados sin departamento
14. Departamentos con más empleados
15. Salario promedio por departamento
16. Empleados con salario superior a X
17. Contar empleados por departamento
18. Empleados contratados en un rango de fechas

0. Salir

Seleccione una opción:
```

---

## 🔧 Especificaciones Técnicas

### Requisitos Previos

- **JDK 21** o superior
- **Maven 3.9+**
- **MySQL 8.0+** o **PostgreSQL 14+**

### Dependencias Maven

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

### Estructura del Proyecto

```
empleados-departamentos/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── empresa/
        │           ├── modelo/
        │           │   ├── Departamento.java
        │           │   └── Empleado.java
        │           ├── dao/
        │           │   ├── DepartamentoDAO.java
        │           │   ├── DepartamentoDAOImpl.java
        │           │   ├── EmpleadoDAO.java
        │           │   └── EmpleadoDAOImpl.java
        │           ├── servicio/
        │           │   └── EmpresaServicio.java
        │           ├── util/
        │           │   └── JPAUtil.java
        │           └── Main.java
        └── resources/
            └── META-INF/
                └── persistence.xml
```

---

## 📝 Implementación de Relaciones

### 1. Anotaciones Requeridas en Departamento

```java
@Entity
@Table(name = "departamentos")
public class Departamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ... otros atributos
    
    // Relación OneToMany (lado inverso)
    @OneToMany(
        mappedBy = "departamento",           // Atributo en Empleado
        cascade = CascadeType.ALL,           // Propagar operaciones
        orphanRemoval = true,                // Eliminar huérfanos
        fetch = FetchType.LAZY               // Carga perezosa
    )
    private List<Empleado> empleados = new ArrayList<>();
    
    // Métodos helper para mantener sincronía bidireccional
    public void addEmpleado(Empleado empleado) {
        empleados.add(empleado);
        empleado.setDepartamento(this);
    }
    
    public void removeEmpleado(Empleado empleado) {
        empleados.remove(empleado);
        empleado.setDepartamento(null);
    }
}
```

### 2. Anotaciones Requeridas en Empleado

```java
@Entity
@Table(name = "empleados")
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ... otros atributos
    
    // Relación ManyToOne (lado propietario - tiene la FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", nullable = true)
    private Departamento departamento;
    
    // Getters y Setters normales
}
```

### 3. Configuración de Base de Datos

**Crear base de datos:**
```sql
-- MySQL
CREATE DATABASE empresa_db;

-- PostgreSQL
CREATE DATABASE empresa_db;
```

---

## 🎯 Funcionalidades Detalladas

### Gestión de Departamentos

#### 1. Crear Departamento
- Solicitar: nombre, código, ubicación, presupuesto
- Validar código único
- Validar presupuesto >= 0
- Guardar con fecha actual

#### 2. Listar Departamentos
- Mostrar todos los departamentos ordenados por nombre
- Indicar cantidad de empleados en cada uno
- Formato claro y legible

#### 3. Buscar por Código
- Buscar departamento por código único
- Mostrar información completa
- Mostrar lista de empleados del departamento

#### 4. Actualizar Departamento
- Permitir modificar nombre, ubicación, presupuesto
- No permitir cambiar código (unique constraint)
- Mantener empleados asociados

#### 5. Eliminar Departamento
- Verificar si tiene empleados
- Si tiene empleados, preguntar qué hacer:
  - Opción A: Reasignar empleados a otro departamento
  - Opción B: Dejar empleados sin departamento
  - Opción C: Cancelar eliminación
- Confirmar antes de eliminar

#### 6. Ver Empleados de Departamento
- Listar todos los empleados del departamento
- Mostrar información resumida de cada empleado
- Calcular estadísticas: total empleados, salario promedio

### Gestión de Empleados

#### 7. Añadir Empleado
- Solicitar datos del empleado
- Opcionalmente asignar a un departamento
- Validar email único
- Validar salario > 0

#### 8. Listar Empleados
- Mostrar todos los empleados activos
- Incluir nombre del departamento
- Ordenar por apellidos

#### 9. Buscar por Email
- Buscar empleado por email
- Mostrar información completa incluyendo departamento

#### 10. Actualizar Empleado
- Permitir cambiar datos personales
- NO permitir cambiar departamento (usar opción 12)
- Actualizar salario

#### 11. Eliminar Empleado
- Marcar como no activo (soft delete)
- O eliminar físicamente según configuración
- Confirmar acción

#### 12. Reasignar Empleado
- Buscar empleado por email o ID
- Mostrar departamento actual
- Solicitar nuevo departamento
- Actualizar relación manteniendo sincronía

### Consultas y Estadísticas

#### 13. Empleados sin Departamento
```java
"SELECT e FROM Empleado e WHERE e.departamento IS NULL"
```

#### 14. Departamentos con Más Empleados
```java
"SELECT d, COUNT(e) FROM Departamento d LEFT JOIN d.empleados e " +
"GROUP BY d ORDER BY COUNT(e) DESC"
```

#### 15. Salario Promedio por Departamento
```java
"SELECT d.nombre, AVG(e.salario) FROM Departamento d " +
"JOIN d.empleados e GROUP BY d.nombre"
```

#### 16. Empleados con Salario Superior
```java
"SELECT e FROM Empleado e WHERE e.salario > :salario ORDER BY e.salario DESC"
```

#### 17. Contar Empleados por Departamento
- Usar `SIZE(d.empleados)` en JPQL
- O consulta con JOIN y GROUP BY

#### 18. Empleados por Rango de Fechas
```java
"SELECT e FROM Empleado e WHERE e.fechaContratacion BETWEEN :inicio AND :fin"
```

---

## ✅ Criterios de Éxito

Tu aplicación será correcta si cumple:

1. ✅ Las dos entidades se crean correctamente en la BD
2. ✅ La relación bidireccional funciona (FK en tabla empleados)
3. ✅ Puedes crear departamentos con y sin empleados
4. ✅ Puedes crear empleados con y sin departamento
5. ✅ Al consultar un departamento, puedes ver sus empleados
6. ✅ Al consultar un empleado, puedes ver su departamento
7. ✅ La reasignación de empleados funciona correctamente
8. ✅ Las consultas con JOIN funcionan
9. ✅ Los métodos helper mantienen la sincronía bidireccional
10. ✅ Cascade operations funcionan apropiadamente
11. ✅ No hay problema N+1 en las consultas críticas
12. ✅ Las validaciones de negocio funcionan

---

## 💡 Pistas Importantes

### Pista 1: Sincronía Bidireccional

**SIEMPRE** usa los métodos helper para modificar la relación:

```java
// ✅ CORRECTO
departamento.addEmpleado(empleado);

// ❌ INCORRECTO - Rompe la sincronía
departamento.getEmpleados().add(empleado);
empleado.setDepartamento(departamento);
```

### Pista 2: Evitar Problema N+1

Cuando listes empleados con sus departamentos, usa JOIN FETCH:

```java
"SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento"
```

Esto carga el departamento en la misma consulta, evitando N+1 queries.

### Pista 3: Reasignación de Empleado

Para reasignar un empleado:

```java
// 1. Si tiene departamento actual, removerlo
if (empleado.getDepartamento() != null) {
    empleado.getDepartamento().removeEmpleado(empleado);
}

// 2. Asignar al nuevo departamento
nuevoDepartamento.addEmpleado(empleado);

// 3. Persistir cambios (si está en transacción, es automático)
```

### Pista 4: Eliminación de Departamento

Antes de eliminar un departamento, gestiona sus empleados:

```java
// Opción 1: Reasignar a otro departamento
for (Empleado emp : departamento.getEmpleados()) {
    otroDepartamento.addEmpleado(emp);
}

// Opción 2: Dejar sin departamento
for (Empleado emp : new ArrayList<>(departamento.getEmpleados())) {
    departamento.removeEmpleado(emp);
}

// Ahora sí eliminar el departamento
em.remove(departamento);
```

### Pista 5: Consulta con Información de Relación

Para mostrar empleados con nombre de departamento sin cargar toda la entidad:

```java
"SELECT NEW com.empresa.dto.EmpleadoDTO(e.nombre, e.apellidos, d.nombre) " +
"FROM Empleado e LEFT JOIN e.departamento d"
```

---

## 🎓 Conceptos que Practicarás

### Relaciones ORM
- ✅ @ManyToOne (lado propietario)
- ✅ @OneToMany (lado inverso)
- ✅ mappedBy para relaciones bidireccionales
- ✅ @JoinColumn para personalizar FK
- ✅ Cascade operations
- ✅ orphanRemoval

### JPQL Avanzado
- ✅ JOIN y LEFT JOIN
- ✅ JOIN FETCH para optimización
- ✅ GROUP BY y funciones agregadas
- ✅ Consultas con múltiples entidades
- ✅ BETWEEN para rangos de fechas
- ✅ IS NULL / IS NOT NULL

### Patrones de Diseño
- ✅ DAO para cada entidad
- ✅ Servicio que coordina múltiples DAOs
- ✅ Métodos helper para sincronía
- ✅ DTOs para consultas optimizadas (opcional)

---

## 🚀 Pasos Sugeridos de Desarrollo

### Fase 1: Configuración (30 min)
1. Crear proyecto Maven
2. Configurar dependencias
3. Crear base de datos
4. Configurar persistence.xml

### Fase 2: Modelos (1 hora)
5. Crear entidad Departamento (sin relación)
6. Crear entidad Empleado (sin relación)
7. Probar persistencia básica
8. Añadir relaciones bidireccionales
9. Implementar métodos helper

### Fase 3: Capa DAO (1 hora)
10. Crear interfaces DAO
11. Implementar DepartamentoDAOImpl
12. Implementar EmpleadoDAOImpl
13. Probar operaciones CRUD

### Fase 4: Servicio (1 hora)
14. Crear clase EmpresaServicio
15. Implementar lógica de negocio
16. Implementar validaciones
17. Implementar consultas complejas

### Fase 5: Interfaz Usuario (1.5 horas)
18. Crear menú principal
19. Implementar opciones de departamentos
20. Implementar opciones de empleados
21. Implementar consultas y estadísticas

### Fase 6: Pruebas (30 min)
22. Probar flujos completos
23. Verificar sincronía bidireccional
24. Probar eliminaciones
25. Verificar estadísticas

---

## 📚 Ejemplos de Uso

### Crear Departamento y Empleados

```
=== CREAR DEPARTAMENTO ===
Nombre: Desarrollo
Código: DEV
Ubicación: Edificio A, Piso 3
Presupuesto: 500000

✓ Departamento creado con ID: 1

=== AÑADIR EMPLEADO ===
Nombre: Juan
Apellidos: García Pérez
Email: juan.garcia@techcorp.com
Salario: 45000
¿Asignar a departamento? (S/N): S
Código de departamento: DEV

✓ Empleado creado y asignado al departamento Desarrollo
```

### Listar Departamento con Empleados

```
=== VER EMPLEADOS DE DEPARTAMENTO ===
Código: DEV

Departamento: Desarrollo (DEV)
Ubicación: Edificio A, Piso 3
Presupuesto: 500,000.00€

EMPLEADOS (3):
1. Juan García Pérez - juan.garcia@techcorp.com - 45,000€
2. María López Ruiz - maria.lopez@techcorp.com - 48,000€
3. Pedro Martínez Sanz - pedro.martinez@techcorp.com - 42,000€

Salario promedio: 45,000.00€
```

### Estadísticas

```
=== SALARIO PROMEDIO POR DEPARTAMENTO ===

Desarrollo: 45,000.00€
Marketing: 38,500.00€
RRHH: 35,000.00€
Ventas: 52,000.00€

Promedio general: 42,625.00€
```

---

## 🔍 Preguntas de Reflexión

1. ¿Por qué Empleado es el lado propietario de la relación?
2. ¿Qué pasaría si no usáramos los métodos helper?
3. ¿Cuál es la diferencia entre `CascadeType.ALL` y `CascadeType.PERSIST`?
4. ¿Por qué usamos `FetchType.LAZY` en las relaciones?
5. ¿Qué es el problema N+1 y cómo se soluciona?
6. ¿Qué hace `orphanRemoval = true`?
7. ¿Cuándo deberíamos usar `@JoinColumn(nullable = false)`?

---

## 📤 Entrega

### Formato de Entrega

1. Código fuente completo
2. Script SQL de creación de BD (opcional, Hibernate lo genera)
3. README.md con:
   - Instrucciones de instalación y ejecución
   - Diagrama de relaciones
   - Casos de uso probados
   - Dificultades encontradas y soluciones

### Estructura del ZIP

```
Apellido_Nombre_Ejercicio2.zip
├── src/
├── pom.xml
├── README.md
└── capturas/ (opcional: screenshots de la aplicación)
```

---

## 🌟 Extensiones Opcionales

Si terminas antes del tiempo estimado:

1. **Soft Delete**: Implementar borrado lógico en lugar de físico
2. **Auditoría**: Añadir campos `creadoPor`, `modificadoPor`, `fechaModificacion`
3. **Histórico de Departamento**: Guardar historial cuando un empleado cambia de departamento
4. **Jerarquía de Empleados**: Añadir relación empleado-jefe (auto-referencia)
5. **Búsqueda Avanzada**: Filtros múltiples (departamento + rango salarial + fecha)
6. **Exportación**: Exportar listados a CSV
7. **Validaciones Avanzadas**: Bean Validation con `@NotNull`, `@Size`, `@Email`

---

**¡Mucho éxito con las relaciones!** 🚀

Este ejercicio es fundamental para comprender cómo funcionan las relaciones en Hibernate. Tómate tu tiempo para entender bien los conceptos de lado propietario, sincronía bidireccional y cascade operations.
