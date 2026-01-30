# Ejercicio 4: Sistema de Gestión de Academia
## Relaciones ManyToMany con Entidad Intermedia y Atributos Adicionales

---

**Módulo:** Acceso a Datos  
**Ciclo:** Desarrollo de Aplicaciones Multiplataforma (DAM)  
**Nivel:** Avanzado  
**Tiempo estimado:** 6-7 horas

---

## 🎯 Objetivos de Aprendizaje

Al finalizar este ejercicio, serás capaz de:

1. Implementar relaciones **@ManyToMany** con entidad intermedia
2. Añadir **atributos propios** a la entidad intermedia (matrícula)
3. Gestionar **claves compuestas** con `@EmbeddedId` y `@IdClass`
4. Crear consultas JPQL con **múltiples niveles de JOIN**
5. Implementar **herencia** con estrategias `@Inheritance`
6. Aplicar **validaciones de Bean Validation** (`@NotNull`, `@Size`, `@Email`)
7. Gestionar **enumerados complejos** con lógica de negocio
8. Implementar **transacciones complejas** con múltiples operaciones
9. Crear **reportes estadísticos avanzados**
10. Optimizar consultas con **@EntityGraph**

---

## 📋 Descripción del Problema

Debes desarrollar un sistema completo de gestión para una academia de formación que ofrece cursos diversos. El sistema debe gestionar:

- **Cursos** con diferentes modalidades y niveles
- **Estudiantes** matriculados en múltiples cursos
- **Profesores** que imparten múltiples cursos
- **Matrículas** (relación estudiante-curso) con fecha, calificación y estado
- **Asignaciones** (relación profesor-curso) con rol y dedicación
- **Aulas** donde se imparten los cursos
- **Evaluaciones** de cada estudiante en cada curso

### Contexto

**TechAcademy** es una academia de formación tecnológica que necesita:

- Gestionar su catálogo de cursos (Programación, Diseño, Marketing Digital, etc.)
- Matricular estudiantes en múltiples cursos simultáneamente
- Asignar profesores a cursos con diferentes roles (titular, auxiliar, tutor)
- Registrar calificaciones y asistencias
- Generar estadísticas: cursos más populares, mejores estudiantes, carga de profesores
- Gestionar disponibilidad de aulas
- Controlar límites de capacidad por curso
- Emitir certificados al completar cursos

---

## 📊 Modelo de Datos Completo

```
                                   ┌─────────────┐
                                   │    AULA     │
                                   │             │
                                   │  id (PK)    │
                                   │  nombre     │
                                   │  capacidad  │
                                   └──────┬──────┘
                                          │ 1
                                          │
                                          │ N
┌──────────────┐         ┌───────────────▼──────┐         ┌──────────────┐
│  ESTUDIANTE  │         │      CURSO           │         │   PROFESOR   │
│              │         │                      │         │              │
│  id (PK)     │         │  id (PK)             │         │  id (PK)     │
│  nombre      │         │  codigo              │         │  nombre      │
│  email       │         │  nombre              │         │  email       │
│  telefono    │         │  descripcion         │         │  especialidad│
│  fecha_nac   │         │  duracion_horas      │         │  telefono    │
└──────┬───────┘         │  precio              │         └──────┬───────┘
       │ N               │  nivel               │                │ N
       │                 │  modalidad           │                │
       │                 │  max_estudiantes     │                │
       │                 │  aula_id (FK)        │                │
       │                 └──────────────────────┘                │
       │                           │                             │
       │                           │                             │
       │                           │                             │
       │ N                         │ N                         N │
       │                           │                             │
┌──────▼────────┐         ┌────────▼──────┐         ┌──────────▼──────┐
│  MATRICULA    │         │  EVALUACION   │         │  ASIGNACION     │
│  (Intermedia) │         │               │         │  (Intermedia)   │
│               │         │  id (PK)      │         │                 │
│  id (PK comp) │◄────────┤  nota         │         │  id (PK comp)   │
│  estudiante_id│         │  fecha        │         │  profesor_id    │
│  curso_id     │         │  tipo         │         │  curso_id       │
│  fecha_matric │         │  observacion  │         │  fecha_inicio   │
│  calificacion │         │  matricula_id │         │  fecha_fin      │
│  estado       │         └───────────────┘         │  rol            │
│  asistencias  │                                   │  horas_semana   │
└───────────────┘                                   └─────────────────┘
```

---

## 📝 Entidades Detalladas

### Entidad: Curso

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| codigo | String | Código único del curso | Único, no nulo, formato "CUR-XXX" |
| nombre | String | Nombre del curso | No nulo, max 150 caracteres |
| descripcion | String | Descripción detallada | Max 1000 caracteres |
| duracionHoras | Integer | Duración en horas | No nulo, > 0 |
| precio | Double | Precio del curso | No nulo, >= 0 |
| nivel | NivelCurso | Nivel (BASICO, INTERMEDIO, AVANZADO) | Enum, no nulo |
| modalidad | Modalidad | PRESENCIAL, ONLINE, HIBRIDA | Enum, no nulo |
| maxEstudiantes | Integer | Capacidad máxima | No nulo, > 0 |
| activo | Boolean | Si está activo | No nulo, default true |
| fechaInicio | LocalDate | Fecha de inicio | No nulo |
| fechaFin | LocalDate | Fecha de finalización | No nulo |
| aula | Aula | Aula asignada | ManyToOne, opcional |
| matriculas | Set\<Matricula\> | Matrículas del curso | OneToMany |
| asignaciones | Set\<Asignacion\> | Profesores asignados | OneToMany |

### Entidad: Estudiante

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| nombre | String | Nombre completo | No nulo, max 100 caracteres |
| email | String | Correo electrónico | Único, no nulo, validación @Email |
| telefono | String | Teléfono | Max 20 caracteres |
| fechaNacimiento | LocalDate | Fecha de nacimiento | No nulo |
| direccion | String | Dirección | Max 200 caracteres |
| fechaRegistro | LocalDate | Fecha de registro | No nulo, auto-generado |
| activo | Boolean | Si está activo | No nulo, default true |
| matriculas | Set\<Matricula\> | Matrículas del estudiante | OneToMany |

### Entidad: Profesor

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| nombre | String | Nombre completo | No nulo, max 100 caracteres |
| email | String | Correo electrónico | Único, no nulo, validación @Email |
| telefono | String | Teléfono | Max 20 caracteres |
| especialidad | String | Área de especialidad | No nulo, max 100 caracteres |
| titulacion | String | Titulación académica | Max 200 caracteres |
| fechaContratacion | LocalDate | Fecha de contratación | No nulo |
| salarioPorHora | Double | Salario por hora | No nulo, > 0 |
| activo | Boolean | Si está activo | No nulo, default true |
| asignaciones | Set\<Asignacion\> | Cursos asignados | OneToMany |

### Entidad: Aula

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| nombre | String | Nombre/código del aula | Único, no nulo, max 50 caracteres |
| capacidad | Integer | Capacidad máxima | No nulo, > 0 |
| edificio | String | Edificio donde se ubica | Max 100 caracteres |
| piso | Integer | Número de piso | >= 0 |
| equipamiento | String | Equipamiento disponible | Max 500 caracteres |
| cursos | List\<Curso\> | Cursos en esta aula | OneToMany |

### Entidad Intermedia: Matricula (ManyToMany)

**Clave Compuesta:** estudiante_id + curso_id

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | MatriculaId | Clave compuesta | @EmbeddedId |
| estudiante | Estudiante | Estudiante matriculado | ManyToOne, parte de PK |
| curso | Curso | Curso matriculado | ManyToOne, parte de PK |
| fechaMatricula | LocalDate | Fecha de matriculación | No nulo, auto-generado |
| calificacionFinal | Double | Nota final (0-10) | >= 0, <= 10 |
| estado | EstadoMatricula | ACTIVA, COMPLETADA, ABANDONADA | Enum, no nulo |
| porcentajeAsistencia | Double | % de asistencia | >= 0, <= 100 |
| observaciones | String | Observaciones | Max 500 caracteres |
| evaluaciones | List\<Evaluacion\> | Evaluaciones del estudiante | OneToMany |

### Clase Embebible: MatriculaId

```java
@Embeddable
public class MatriculaId implements Serializable {
    
    @Column(name = "estudiante_id")
    private Long estudianteId;
    
    @Column(name = "curso_id")
    private Long cursoId;
    
    // Constructor, equals, hashCode
}
```

### Entidad Intermedia: Asignacion (Profesor-Curso)

**Clave Compuesta:** profesor_id + curso_id

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | AsignacionId | Clave compuesta | @EmbeddedId |
| profesor | Profesor | Profesor asignado | ManyToOne, parte de PK |
| curso | Curso | Curso asignado | ManyToOne, parte de PK |
| fechaInicio | LocalDate | Inicio de asignación | No nulo |
| fechaFin | LocalDate | Fin de asignación | Puede ser null |
| rol | RolProfesor | TITULAR, AUXILIAR, TUTOR | Enum, no nulo |
| horasSemana | Integer | Horas semanales dedicadas | No nulo, > 0 |

### Entidad: Evaluacion

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| matricula | Matricula | Matrícula evaluada | ManyToOne, obligatoria |
| fecha | LocalDate | Fecha de evaluación | No nulo |
| nota | Double | Calificación (0-10) | No nulo, >= 0, <= 10 |
| tipo | TipoEvaluacion | EXAMEN, PRACTICA, PROYECTO, PARTICIPACION | Enum |
| observaciones | String | Comentarios | Max 500 caracteres |

### Enumerados

```java
enum NivelCurso { BASICO, INTERMEDIO, AVANZADO, EXPERTO }

enum Modalidad { PRESENCIAL, ONLINE, HIBRIDA }

enum EstadoMatricula { ACTIVA, COMPLETADA, ABANDONADA, SUSPENDIDA }

enum RolProfesor { TITULAR, AUXILIAR, TUTOR, INVITADO }

enum TipoEvaluacion { EXAMEN, PRACTICA, PROYECTO, PARTICIPACION, TRABAJO_FINAL }
```

---

## 🔧 Configuración Técnica

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
    
    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.8</version>
    </dependency>
    
    <!-- Hibernate Validator (Bean Validation) -->
    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>9.1.0.Final</version>
    </dependency>
</dependencies>
```

---

## 🎨 Funcionalidades Requeridas

### Menú Principal

```
=== SISTEMA DE GESTIÓN DE ACADEMIA ===

GESTIÓN DE CURSOS
1. Crear curso
2. Listar todos los cursos
3. Buscar curso por código
4. Actualizar curso
5. Asignar aula a curso
6. Ver matrículas de un curso
7. Ver profesores de un curso

GESTIÓN DE ESTUDIANTES
8. Registrar estudiante
9. Matricular estudiante en curso
10. Dar de baja matrícula
11. Ver cursos de un estudiante
12. Actualizar calificación final
13. Registrar evaluación

GESTIÓN DE PROFESORES
14. Registrar profesor
15. Asignar profesor a curso
16. Ver cursos de un profesor
17. Calcular carga horaria de profesor

GESTIÓN DE AULAS
18. Crear aula
19. Listar aulas disponibles
20. Ver ocupación de aulas

REPORTES Y ESTADÍSTICAS
21. Cursos con más matriculados
22. Estudiantes con mejor promedio
23. Cursos por completar este mes
24. Ingresos totales por curso
25. Profesores con mayor carga horaria
26. Tasa de abandono por curso
27. Estudiantes sin cursos activos
28. Listado de certificados a emitir

0. Salir
```

---

## 💻 Implementaciones Clave

### 1. Entidad con Clave Compuesta - Matricula

```java
@Entity
@Table(name = "matriculas")
public class Matricula {
    
    @EmbeddedId
    private MatriculaId id;
    
    @ManyToOne
    @MapsId("estudianteId")
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;
    
    @ManyToOne
    @MapsId("cursoId")
    @JoinColumn(name = "curso_id")
    private Curso curso;
    
    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;
    
    @Column(name = "calificacion_final")
    private Double calificacionFinal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMatricula estado;
    
    @Column(name = "porcentaje_asistencia")
    private Double porcentajeAsistencia;
    
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    private List<Evaluacion> evaluaciones = new ArrayList<>();
    
    public Matricula() {
        this.fechaMatricula = LocalDate.now();
        this.estado = EstadoMatricula.ACTIVA;
        this.porcentajeAsistencia = 0.0;
    }
    
    // Constructor de conveniencia
    public Matricula(Estudiante estudiante, Curso curso) {
        this();
        this.estudiante = estudiante;
        this.curso = curso;
        this.id = new MatriculaId(estudiante.getId(), curso.getId());
    }
    
    // Calcular calificación final como promedio de evaluaciones
    public void calcularCalificacionFinal() {
        if (evaluaciones.isEmpty()) {
            this.calificacionFinal = null;
            return;
        }
        
        double suma = evaluaciones.stream()
            .mapToDouble(Evaluacion::getNota)
            .sum();
        this.calificacionFinal = suma / evaluaciones.size();
    }
}

@Embeddable
public class MatriculaId implements Serializable {
    
    @Column(name = "estudiante_id")
    private Long estudianteId;
    
    @Column(name = "curso_id")
    private Long cursoId;
    
    public MatriculaId() {}
    
    public MatriculaId(Long estudianteId, Long cursoId) {
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatriculaId)) return false;
        MatriculaId that = (MatriculaId) o;
        return Objects.equals(estudianteId, that.estudianteId) &&
               Objects.equals(cursoId, that.cursoId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(estudianteId, cursoId);
    }
}
```

### 2. Lógica de Matriculación

```java
public class AcademiaServicio {
    
    public Matricula matricularEstudiante(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteDAO.buscarPorId(estudianteId);
        Curso curso = cursoDAO.buscarPorId(cursoId);
        
        // Validaciones
        if (!curso.isActivo()) {
            throw new IllegalStateException("El curso no está activo");
        }
        
        if (!estudiante.isActivo()) {
            throw new IllegalStateException("El estudiante no está activo");
        }
        
        // Verificar cupo disponible
        long matriculasActivas = matriculaDAO.contarMatriculasActivasPorCurso(cursoId);
        if (matriculasActivas >= curso.getMaxEstudiantes()) {
            throw new IllegalStateException("El curso ha alcanzado su capacidad máxima");
        }
        
        // Verificar que no esté ya matriculado
        MatriculaId id = new MatriculaId(estudianteId, cursoId);
        Matricula existente = matriculaDAO.buscarPorId(id);
        if (existente != null && existente.getEstado() == EstadoMatricula.ACTIVA) {
            throw new IllegalStateException("El estudiante ya está matriculado en este curso");
        }
        
        // Crear matrícula
        Matricula matricula = new Matricula(estudiante, curso);
        matriculaDAO.guardar(matricula);
        
        return matricula;
    }
}
```

### 3. Consultas JPQL Complejas

#### a) Cursos con más estudiantes matriculados

```java
String jpql = "SELECT c.nombre, COUNT(m) as total " +
              "FROM Matricula m " +
              "JOIN m.curso c " +
              "WHERE m.estado = 'ACTIVA' " +
              "GROUP BY c.nombre " +
              "ORDER BY total DESC";
```

#### b) Estudiantes con mejor promedio

```java
String jpql = "SELECT e.nombre, AVG(m.calificacionFinal) as promedio " +
              "FROM Matricula m " +
              "JOIN m.estudiante e " +
              "WHERE m.calificacionFinal IS NOT NULL " +
              "GROUP BY e.nombre " +
              "HAVING AVG(m.calificacionFinal) >= 7.0 " +
              "ORDER BY promedio DESC";
```

#### c) Carga horaria total de un profesor

```java
String jpql = "SELECT SUM(a.horasSemana) " +
              "FROM Asignacion a " +
              "WHERE a.profesor.id = :profesorId " +
              "AND (a.fechaFin IS NULL OR a.fechaFin >= CURRENT_DATE)";
```

#### d) Ingresos totales por curso

```java
String jpql = "SELECT c.nombre, c.precio, COUNT(m), (c.precio * COUNT(m)) as ingresos " +
              "FROM Curso c " +
              "LEFT JOIN c.matriculas m " +
              "WHERE m.estado != 'ABANDONADA' OR m IS NULL " +
              "GROUP BY c.nombre, c.precio " +
              "ORDER BY ingresos DESC";
```

#### e) Tasa de abandono por curso

```java
String jpql = "SELECT c.nombre, " +
              "COUNT(m) as total, " +
              "SUM(CASE WHEN m.estado = 'ABANDONADA' THEN 1 ELSE 0 END) as abandonos, " +
              "(SUM(CASE WHEN m.estado = 'ABANDONADA' THEN 1 ELSE 0 END) * 100.0 / COUNT(m)) as tasa " +
              "FROM Curso c " +
              "JOIN c.matriculas m " +
              "GROUP BY c.nombre " +
              "ORDER BY tasa DESC";
```

#### f) Estudiantes de un curso con sus promedios

```java
String jpql = "SELECT e.nombre, m.porcentajeAsistencia, m.calificacionFinal " +
              "FROM Matricula m " +
              "JOIN m.estudiante e " +
              "WHERE m.curso.codigo = :codigoCurso " +
              "AND m.estado = 'ACTIVA' " +
              "ORDER BY m.calificacionFinal DESC";
```

### 4. Bean Validation

```java
@Entity
public class Estudiante {
    
    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @NotNull(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;
    
    // Validación personalizada
    @AssertTrue(message = "El estudiante debe tener al menos 16 años")
    public boolean isEdadValida() {
        if (fechaNacimiento == null) return true;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 16;
    }
}
```

### 5. @EntityGraph para Optimización

```java
// En el DAO
@NamedEntityGraph(
    name = "Curso.completo",
    attributeNodes = {
        @NamedAttributeNode("aula"),
        @NamedAttributeNode(value = "matriculas", subgraph = "matriculas-estudiantes"),
        @NamedAttributeNode(value = "asignaciones", subgraph = "asignaciones-profesores")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "matriculas-estudiantes",
            attributeNodes = @NamedAttributeNode("estudiante")
        ),
        @NamedSubgraph(
            name = "asignaciones-profesores",
            attributeNodes = @NamedAttributeNode("profesor")
        )
    }
)

// Uso
EntityGraph<?> graph = em.getEntityGraph("Curso.completo");
Map<String, Object> hints = new HashMap<>();
hints.put("javax.persistence.fetchgraph", graph);

Curso curso = em.find(Curso.class, id, hints);
```

---

## ✅ Criterios de Éxito

1. ✅ Todas las entidades se crean con sus relaciones
2. ✅ Clave compuesta funciona correctamente
3. ✅ ManyToMany con atributos adicionales implementado
4. ✅ Validaciones de Bean Validation activas
5. ✅ Matriculación valida cupo y estado
6. ✅ Calificación final se calcula automáticamente
7. ✅ Consultas complejas devuelven datos correctos
8. ✅ No hay problema N+1 en listados
9. ✅ @EntityGraph usado en al menos 2 consultas
10. ✅ Todas las estadísticas funcionan

---

## 💡 Pistas Importantes

### Pista 1: Crear Matrícula Correctamente

```java
// Crear la clave compuesta primero
MatriculaId id = new MatriculaId(estudiante.getId(), curso.getId());

// Crear la matrícula
Matricula matricula = new Matricula();
matricula.setId(id);
matricula.setEstudiante(estudiante);
matricula.setCurso(curso);
// ... resto de atributos

// Persistir
em.persist(matricula);
```

### Pista 2: Buscar Matrícula por Clave Compuesta

```java
MatriculaId id = new MatriculaId(estudianteId, cursoId);
Matricula matricula = em.find(Matricula.class, id);
```

### Pista 3: Validar Entidades

```java
ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
Validator validator = factory.getValidator();

Set<ConstraintViolation<Estudiante>> violations = validator.validate(estudiante);

if (!violations.isEmpty()) {
    for (ConstraintViolation<Estudiante> violation : violations) {
        System.out.println(violation.getMessage());
    }
    throw new ValidationException("Datos inválidos");
}
```

---

## 📤 Entrega

**Estructura del ZIP:**
```
Apellido_Nombre_Ejercicio4.zip
├── src/
├── pom.xml
├── README.md
└── docs/
    ├── modelo_er.png
    └── consultas.sql (ejemplos de verificación)
```

---

## 🌟 Extensiones Opcionales

1. **Notificaciones**: Sistema de notificaciones por email (simulado)
2. **Certificados**: Generar PDF de certificados al completar
3. **Dashboard**: Panel con métricas en tiempo real
4. **Pagos**: Sistema de pagos de matrículas
5. **Calendario**: Gestión de horarios de cursos
6. **Asistencia**: Control de asistencia clase por clase
7. **Foros**: Foros de discusión por curso

---

**¡El ejercicio más completo hasta ahora!** 🚀

Este ejercicio integra todos los conceptos avanzados de Hibernate/JPA: ManyToMany con atributos, claves compuestas, validaciones, consultas complejas y optimización.
