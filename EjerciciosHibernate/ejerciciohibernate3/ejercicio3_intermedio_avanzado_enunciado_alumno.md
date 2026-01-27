# Ejercicio 3: Sistema de Tienda Online
## Relaciones Múltiples, Consultas JPQL Avanzadas y OneToOne

---

**Módulo:** Acceso a Datos  
**Ciclo:** Desarrollo de Aplicaciones Multiplataforma (DAM)  
**Nivel:** Intermedio-Avanzado  
**Tiempo estimado:** 5-6 horas

---

## 🎯 Objetivos de Aprendizaje

Al finalizar este ejercicio, serás capaz de:

1. Diseñar modelos de datos con **múltiples relaciones** en la misma entidad
2. Implementar relaciones **@OneToOne** unidireccionales y bidireccionales
3. Crear consultas JPQL **complejas** con múltiples JOIN
4. Utilizar **subconsultas** en JPQL
5. Implementar **DTOs** para proyecciones optimizadas
6. Aplicar **Criteria API** para consultas dinámicas
7. Gestionar relaciones **opcionales vs obligatorias**
8. Optimizar rendimiento con **batch fetching** y estrategias de carga

---

## 📋 Descripción del Problema

Debes desarrollar el sistema backend de una tienda online que vende productos organizados por categorías. El sistema debe gestionar:

- **Categorías** de productos (Electrónica, Ropa, Hogar, etc.)
- **Productos** que pertenecen a categorías
- **Clientes** registrados en la tienda
- **Direcciones** de envío de los clientes
- **Pedidos** realizados por clientes
- **Líneas de pedido** (productos incluidos en cada pedido)

### Contexto

**ShopEase** es una tienda online en crecimiento que necesita un sistema robusto para:

- Organizar su catálogo de productos por categorías
- Gestionar perfiles de clientes con múltiples direcciones
- Procesar pedidos con múltiples productos
- Consultar estadísticas de ventas
- Generar reportes de productos más vendidos
- Calcular ingresos por categoría
- Identificar clientes VIP (más pedidos/mayor gasto)

---

## 📊 Modelo de Datos Completo

```
┌──────────────┐
│  CATEGORIA   │
│              │
│  id (PK)     │
│  nombre      │
│  descripcion │
└──────┬───────┘
       │ 1
       │
       │ N
┌──────▼───────┐         ┌────────────────┐
│  PRODUCTO    │         │  LINEA_PEDIDO  │
│              │ N     N │                │
│  id (PK)     │◄────────┤  id (PK)       │
│  nombre      │         │  cantidad      │
│  precio      │         │  precio_unidad │
│  stock       │         │  producto_id   │
│  categoria_id│         │  pedido_id     │
└──────────────┘         └────────┬───────┘
                                  │ N
                                  │
                         ┌────────▼───────┐
                         │   PEDIDO       │
                         │                │
                         │   id (PK)      │
                         │   numero       │
                         │   fecha        │
                         │   total        │
                         │   estado       │
                         │   cliente_id   │
                         └────────┬───────┘
                                  │ N
                                  │
                         ┌────────▼───────┐         ┌────────────┐
                         │   CLIENTE      │ 1     1 │  DIRECCION │
                         │                │◄────────│            │
                         │   id (PK)      │         │  id (PK)   │
                         │   nombre       │         │  calle     │
                         │   email        │ 1    N  │  ciudad    │
                         │   telefono     │◄──────┐ │  cp        │
                         │   direccion_   │       │ │  cliente_id│
                         │   principal_id │       │ └────────────┘
                         └────────────────┘       │
                                  └───────────────┘
```

---

## 📝 Entidades Detalladas

### Entidad: Categoria

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| nombre | String | Nombre de categoría | No nulo, único, max 50 caracteres |
| descripcion | String | Descripción | Max 255 caracteres |
| activa | Boolean | Si está activa | No nulo, default true |
| productos | List\<Producto\> | Productos de esta categoría | Relación OneToMany |

### Entidad: Producto

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| nombre | String | Nombre del producto | No nulo, max 100 caracteres |
| descripcion | String | Descripción detallada | Max 500 caracteres |
| precio | Double | Precio unitario | No nulo, >= 0 |
| stock | Integer | Unidades disponibles | No nulo, >= 0 |
| activo | Boolean | Si está disponible | No nulo, default true |
| fechaAlta | LocalDate | Fecha de alta | No nulo, auto-generado |
| categoria | Categoria | Categoría del producto | Relación ManyToOne, obligatoria |
| lineasPedido | List\<LineaPedido\> | Líneas donde aparece | Relación OneToMany |

### Entidad: Cliente

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| nombre | String | Nombre completo | No nulo, max 100 caracteres |
| email | String | Correo electrónico | Único, no nulo, max 100 caracteres |
| telefono | String | Teléfono de contacto | Max 20 caracteres |
| fechaRegistro | LocalDate | Fecha de registro | No nulo, auto-generado |
| pedidos | List\<Pedido\> | Pedidos del cliente | Relación OneToMany |
| direcciones | List\<Direccion\> | Direcciones del cliente | Relación OneToMany |
| direccionPrincipal | Direccion | Dirección principal | Relación OneToOne, opcional |

### Entidad: Direccion

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| calle | String | Calle y número | No nulo, max 200 caracteres |
| ciudad | String | Ciudad | No nulo, max 100 caracteres |
| provincia | String | Provincia | Max 100 caracteres |
| codigoPostal | String | Código postal | No nulo, max 10 caracteres |
| pais | String | País | No nulo, default "España" |
| cliente | Cliente | Cliente propietario | Relación ManyToOne, obligatoria |

### Entidad: Pedido

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| numeroPedido | String | Número único de pedido | Único, no nulo, formato "PED-YYYYMMDD-XXX" |
| fechaPedido | LocalDateTime | Fecha y hora del pedido | No nulo, auto-generado |
| total | Double | Total del pedido | No nulo, >= 0 |
| estado | EstadoPedido | Estado actual | Enum, no nulo |
| cliente | Cliente | Cliente del pedido | Relación ManyToOne, obligatoria |
| lineas | List\<LineaPedido\> | Líneas del pedido | Relación OneToMany |

**Enum EstadoPedido:** PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO

### Entidad: LineaPedido

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único | PK, auto-generado |
| cantidad | Integer | Cantidad de unidades | No nulo, > 0 |
| precioUnidad | Double | Precio por unidad | No nulo, >= 0 |
| subtotal | Double | cantidad * precioUnidad | Calculado |
| producto | Producto | Producto de la línea | Relación ManyToOne, obligatoria |
| pedido | Pedido | Pedido al que pertenece | Relación ManyToOne, obligatoria |

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
tienda-online/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── tienda/
        │           ├── modelo/
        │           │   ├── Categoria.java
        │           │   ├── Producto.java
        │           │   ├── Cliente.java
        │           │   ├── Direccion.java
        │           │   ├── Pedido.java
        │           │   ├── LineaPedido.java
        │           │   └── EstadoPedido.java (enum)
        │           ├── dto/
        │           │   ├── ProductoDTO.java
        │           │   ├── PedidoResumenDTO.java
        │           │   └── EstadisticasDTO.java
        │           ├── dao/
        │           │   ├── CategoriaDAO.java
        │           │   ├── ProductoDAO.java
        │           │   ├── ClienteDAO.java
        │           │   ├── PedidoDAO.java
        │           │   └── (implementaciones)
        │           ├── servicio/
        │           │   ├── TiendaServicio.java
        │           │   └── PedidoServicio.java
        │           ├── util/
        │           │   └── JPAUtil.java
        │           └── Main.java
        └── resources/
            └── META-INF/
                └── persistence.xml
```

---

## 🎨 Funcionalidades Requeridas

### Menú Principal

```
=== TIENDA ONLINE - SHOPEASE ===

GESTIÓN DE CATÁLOGO
1. Crear categoría
2. Crear producto
3. Listar productos por categoría
4. Actualizar stock de producto
5. Buscar productos por nombre
6. Productos con stock bajo (<10 unidades)

GESTIÓN DE CLIENTES
7. Registrar nuevo cliente
8. Añadir dirección a cliente
9. Establecer dirección principal
10. Listar clientes con pedidos
11. Buscar cliente por email

GESTIÓN DE PEDIDOS
12. Crear nuevo pedido
13. Añadir producto a pedido
14. Finalizar pedido
15. Cambiar estado de pedido
16. Ver detalles de pedido
17. Cancelar pedido

CONSULTAS Y REPORTES
18. Productos más vendidos (top 10)
19. Clientes con más pedidos (top 5)
20. Ingresos totales por categoría
21. Pedidos por estado
22. Productos sin ventas
23. Valor total de inventario
24. Clientes sin pedidos

0. Salir
```

---

## 💡 Implementaciones Clave

### 1. Relación OneToOne - Cliente y Dirección Principal

```java
@Entity
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relación OneToMany con direcciones
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones = new ArrayList<>();
    
    // Relación OneToOne con dirección principal (OPCIONAL)
    @OneToOne
    @JoinColumn(name = "direccion_principal_id", nullable = true)
    private Direccion direccionPrincipal;
    
    // Método para establecer dirección principal
    public void setDireccionPrincipal(Direccion direccion) {
        if (!direcciones.contains(direccion)) {
            throw new IllegalArgumentException("La dirección debe pertenecer al cliente");
        }
        this.direccionPrincipal = direccion;
    }
}
```

### 2. Gestión de Pedido con Líneas

```java
@Entity
public class Pedido {
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaPedido> lineas = new ArrayList<>();
    
    // Método para añadir línea y actualizar total
    public void addLinea(LineaPedido linea) {
        lineas.add(linea);
        linea.setPedido(this);
        calcularTotal();
    }
    
    public void removeLinea(LineaPedido linea) {
        lineas.remove(linea);
        linea.setPedido(null);
        calcularTotal();
    }
    
    private void calcularTotal() {
        this.total = lineas.stream()
            .mapToDouble(LineaPedido::getSubtotal)
            .sum();
    }
}

@Entity
public class LineaPedido {
    
    // Calcular subtotal automáticamente
    public Double getSubtotal() {
        return cantidad * precioUnidad;
    }
    
    // Establecer precio del producto actual al crear la línea
    public void setPrecioDesdeProducto(Producto producto) {
        this.precioUnidad = producto.getPrecio();
    }
}
```

### 3. Consultas JPQL Complejas Requeridas

#### a) Productos más vendidos
```java
String jpql = "SELECT p.nombre, SUM(lp.cantidad) as total " +
              "FROM LineaPedido lp JOIN lp.producto p " +
              "GROUP BY p.nombre " +
              "ORDER BY total DESC";
```

#### b) Ingresos por categoría
```java
String jpql = "SELECT c.nombre, SUM(lp.subtotal) as ingresos " +
              "FROM LineaPedido lp " +
              "JOIN lp.producto p " +
              "JOIN p.categoria c " +
              "GROUP BY c.nombre " +
              "ORDER BY ingresos DESC";
```

#### c) Clientes VIP (más gasto)
```java
String jpql = "SELECT c.nombre, SUM(ped.total) as totalGastado " +
              "FROM Cliente c JOIN c.pedidos ped " +
              "WHERE ped.estado != 'CANCELADO' " +
              "GROUP BY c.nombre " +
              "ORDER BY totalGastado DESC";
```

#### d) Productos con stock bajo en categoría específica
```java
String jpql = "SELECT p FROM Producto p " +
              "WHERE p.categoria.nombre = :categoria " +
              "AND p.stock < :stockMinimo " +
              "ORDER BY p.stock ASC";
```

#### e) Pedidos de un cliente con JOIN FETCH
```java
String jpql = "SELECT DISTINCT p FROM Pedido p " +
              "LEFT JOIN FETCH p.lineas l " +
              "LEFT JOIN FETCH l.producto " +
              "WHERE p.cliente.id = :clienteId";
```

### 4. DTO para Reportes

```java
public class ProductoVentasDTO {
    private String nombreProducto;
    private Long cantidadVendida;
    private Double ingresoTotal;
    
    // Constructor para JPQL con NEW
    public ProductoVentasDTO(String nombre, Long cantidad, Double ingresos) {
        this.nombreProducto = nombre;
        this.cantidadVendida = cantidad;
        this.ingresoTotal = ingresos;
    }
    
    // Getters y toString
}

// Consulta con DTO
String jpql = "SELECT NEW com.tienda.dto.ProductoVentasDTO(" +
              "p.nombre, SUM(lp.cantidad), SUM(lp.subtotal)) " +
              "FROM LineaPedido lp JOIN lp.producto p " +
              "GROUP BY p.nombre";
```

---

## 💡 Pistas Importantes

### Pista 1: Validación de Stock

Antes de añadir un producto a un pedido:

```java
if (producto.getStock() < cantidad) {
    throw new IllegalArgumentException("Stock insuficiente");
}

// Al finalizar el pedido, descontar stock
producto.setStock(producto.getStock() - cantidad);
```

### Pista 2: Generación de Número de Pedido

```java
private String generarNumeroPedido() {
    LocalDate hoy = LocalDate.now();
    String fecha = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    
    // Obtener siguiente número del día
    Long contador = obtenerContadorDelDia(hoy);
    
    return String.format("PED-%s-%03d", fecha, contador);
}
```

### Pista 3: Optimización con JOIN FETCH

Para evitar N+1 al listar pedidos con líneas:

```java
// ❌ Sin optimización - N+1 problem
List<Pedido> pedidos = em.createQuery("SELECT p FROM Pedido p").getResultList();

// ✅ Con optimización
List<Pedido> pedidos = em.createQuery(
    "SELECT DISTINCT p FROM Pedido p " +
    "LEFT JOIN FETCH p.lineas " +
    "LEFT JOIN FETCH p.cliente"
).getResultList();
```

### Pista 4: Criteria API para Búsquedas Dinámicas

Para búsqueda de productos con filtros opcionales:

```java
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
Root<Producto> producto = cq.from(Producto.class);

List<Predicate> predicates = new ArrayList<>();

if (nombre != null) {
    predicates.add(cb.like(cb.lower(producto.get("nombre")), "%" + nombre.toLowerCase() + "%"));
}

if (categoriaId != null) {
    predicates.add(cb.equal(producto.get("categoria").get("id"), categoriaId));
}

if (precioMin != null) {
    predicates.add(cb.greaterThanOrEqualTo(producto.get("precio"), precioMin));
}

cq.where(predicates.toArray(new Predicate[0]));
```

---

## ✅ Criterios de Éxito

1. ✅ Todas las entidades se crean correctamente con sus relaciones
2. ✅ Relación OneToOne funciona (dirección principal)
3. ✅ Se puede crear un pedido completo con múltiples líneas
4. ✅ El stock se descuenta correctamente al finalizar pedido
5. ✅ El total del pedido se calcula automáticamente
6. ✅ Las consultas de reportes devuelven datos correctos
7. ✅ No hay problema N+1 en listados principales
8. ✅ Los DTOs se usan para consultas optimizadas
9. ✅ Criteria API implementado para al menos una búsqueda
10. ✅ Validaciones de negocio funcionan (stock, etc.)

---

## 🎓 Conceptos que Practicarás

### Relaciones Avanzadas
- ✅ OneToOne opcional y obligatoria
- ✅ Múltiples relaciones en una entidad
- ✅ Relaciones con entidad intermedia (LineaPedido)
- ✅ Cascade apropiado según caso

### JPQL Avanzado
- ✅ Múltiples JOIN en una consulta
- ✅ Funciones agregadas (SUM, AVG, COUNT)
- ✅ GROUP BY y HAVING
- ✅ ORDER BY con funciones
- ✅ Subconsultas
- ✅ DTOs con constructor en JPQL

### Optimización
- ✅ JOIN FETCH para evitar N+1
- ✅ Proyecciones con DTOs
- ✅ Criteria API para consultas dinámicas
- ✅ Índices y restricciones de BD

---

## 📤 Entrega

**Estructura del ZIP:**
```
Apellido_Nombre_Ejercicio3.zip
├── src/
├── README.md
└── diagramas/
    └── modelo_er.png (opcional)
```

---

## 🌟 Extensiones Opcionales

1. **Auditoría**: Campos `creadoPor`, `fechaCreacion`, `modificadoPor`, `fechaModificacion`
2. **Valoraciones**: Entidad `Valoracion` (Cliente → Producto, 1-5 estrellas)
3. **Descuentos**: Sistema de códigos de descuento en pedidos
4. **Carrito**: Entidad intermedia antes de crear pedido
5. **Notificaciones**: Sistema de alertas por email (simulado)
6. **Dashboard**: Panel con métricas en tiempo real
7. **API REST**: Exponer funcionalidades mediante Spring Boot (avanzado)

---

**¡Este ejercicio integra todo lo aprendido!** 🚀

Tómate tu tiempo para diseñar bien el modelo y las relaciones antes de empezar a programar. Un buen diseño inicial facilitará mucho la implementación.
