Ejercicio 10: Proyecto Integrador Multi-SGBD (Avanzado)
📂 ejercicio10_avanzado_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Trabajar simultáneamente con múltiples sistemas gestores de bases de datos
Diseñar una capa de abstracción que permita cambiar de SGBD fácilmente
Realizar migración de datos entre diferentes SGBD
Comparar rendimiento entre diferentes sistemas
Aplicar todos los conocimientos adquiridos en la unidad


📋 Descripción del ejercicio
Este es el proyecto final integrador de la unidad. Crearás una aplicación capaz de trabajar con diferentes SGBD (SQLite, PostgreSQL, MySQL) de forma transparente. La aplicación permitirá migrar datos entre ellos y comparar su rendimiento.
Deberás crear:

Sistema de gestión de inventario que funcione con los 3 SGBD
Capa de abstracción que permita cambiar de SGBD mediante configuración
Herramienta de migración de datos entre SGBD
Sistema de medición y comparación de rendimiento
Generación de reportes comparativos


🔧 Configuración del entorno
SGBDs a utilizar

SQLite: Base de datos local (archivo)
PostgreSQL: Contenedor Docker
MySQL: Contenedor Docker

Modelo de datos común
sqlCREATE TABLE categorias (
    id [TIPO_SEGUN_SGBD] PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE productos (
    id [TIPO_SEGUN_SGBD] PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    categoria_id [TIPO_FK] REFERENCES categorias(id),
    precio DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    fecha_alta [TIPO_FECHA],
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE movimientos_stock (
    id [TIPO_SEGUN_SGBD] PRIMARY KEY,
    producto_id [TIPO_FK] REFERENCES productos(id),
    tipo VARCHAR(20), -- 'ENTRADA' o 'SALIDA'
    cantidad INTEGER NOT NULL,
    motivo VARCHAR(200),
    fecha [TIPO_TIMESTAMP]
);
```

**Nota**: Los tipos de datos varían entre SGBD. Tu aplicación debe adaptar las sentencias DDL según el SGBD seleccionado.

---

## 📝 Especificaciones técnicas

### Estructura de paquetes
```
src/
├── modelo/
│   ├── Categoria.java
│   ├── Producto.java
│   └── MovimientoStock.java
├── dao/
│   ├── IProductoDAO.java
│   ├── ProductoDAOImpl.java
│   └── [otros DAOs]
├── factory/
│   ├── DAOFactory.java
│   └── SGBDFactory.java
├── util/
│   ├── ConexionDB.java
│   ├── ConfiguracionSGBD.java
│   └── GestorMigracion.java
├── rendimiento/
│   ├── MedidorRendimiento.java
│   └── ReporteComparativo.java
└── app/
    └── AplicacionInventario.java
Patrón Factory para abstracción
La aplicación debe poder cambiar de SGBD mediante configuración:
properties# config.properties
sgbd.actual=postgresql
# Valores posibles: sqlite | postgresql | mysql

# Configuración SQLite
sqlite.ruta=inventario.db

# Configuración PostgreSQL
postgresql.host=localhost
postgresql.port=5432
postgresql.database=inventario
postgresql.user=admin
postgresql.password=admin123

# Configuración MySQL
mysql.host=localhost
mysql.port=3306
mysql.database=inventario
mysql.user=usuario
mysql.password=usuario123
```

### Funcionalidades obligatorias

1. **Gestión de Inventario**
   - CRUD completo de categorías y productos
   - Registro de movimientos de stock
   - Consultas de stock actual
   - Alertas de stock bajo

2. **Migración de Datos**
   - Exportar todo el contenido de un SGBD
   - Importar contenido a otro SGBD
   - Mantener integridad referencial
   - Log del proceso de migración

3. **Comparación de Rendimiento**
   - Medir tiempo de inserción (lotes de 1000, 5000, 10000 registros)
   - Medir tiempo de consultas complejas
   - Medir tiempo de actualizaciones masivas
   - Generar informe comparativo

---

## 🧪 Casos de prueba

### Caso 1: Cambio dinámico de SGBD
**Entrada**: Modificar `config.properties` de `postgresql` a `mysql` y reiniciar
**Salida esperada (orientativa)**:
```
=== SISTEMA DE INVENTARIO ===
Leyendo configuración...
✓ SGBD seleccionado: MySQL
✓ Conexión establecida: mysql://localhost:3306/inventario

[El sistema funciona normalmente con MySQL]

Modificar config.properties → sgbd.actual=postgresql

[Reiniciar aplicación]

=== SISTEMA DE INVENTARIO ===
Leyendo configuración...
✓ SGBD seleccionado: PostgreSQL
✓ Conexión establecida: postgresql://localhost:5432/inventario

[El sistema funciona normalmente con PostgreSQL]
```

### Caso 2: Migración de datos SQLite → PostgreSQL
**Entrada**: Ejecutar migración con 500 productos en SQLite
**Salida esperada (orientativa)**:
```
=== MIGRACIÓN DE DATOS ===
Origen: SQLite (inventario.db)
Destino: PostgreSQL (localhost:5432/inventario)

Iniciando migración...
[1/4] Extrayendo categorías de SQLite...
  → Encontradas: 15 categorías
  → Exportadas: 15 categorías ✓

[2/4] Extrayendo productos de SQLite...
  → Encontrados: 500 productos
  → Exportados: 500 productos ✓

[3/4] Importando categorías a PostgreSQL...
  → Insertadas: 15/15 (100%) ✓

[4/4] Importando productos a PostgreSQL...
  [█████████████████████] 500/500 (100%)
  → Insertados: 500 productos ✓

✓ Migración completada exitosamente
Tiempo total: 2.34 segundos
No se encontraron errores
```

### Caso 3: Comparativa de rendimiento - Inserciones
**Entrada**: Ejecutar test de rendimiento con 10,000 inserciones
**Salida esperada (orientativa)**:
```
=== TEST DE RENDIMIENTO: INSERCIONES ===
Insertando 10,000 productos en cada SGBD...

SQLite:
  Preparando... ✓
  [████████████████████] 10000/10000
  Tiempo: 1.234 segundos
  Velocidad: 8103 inserciones/seg

PostgreSQL:
  Preparando... ✓
  [████████████████████] 10000/10000
  Tiempo: 2.156 segundos
  Velocidad: 4638 inserciones/seg

MySQL:
  Preparando... ✓
  [████████████████████] 10000/10000
  Tiempo: 1.987 segundos
  Velocidad: 5032 inserciones/seg

🏆 Ganador: SQLite (43% más rápido que el segundo)
```

### Caso 4: Comparativa de rendimiento - Consultas complejas
**Entrada**: Ejecutar 5 consultas JOIN complejas en cada SGBD
**Salida esperada (orientativa)**:
```
=== TEST DE RENDIMIENTO: CONSULTAS COMPLEJAS ===

Consulta: Productos con stock bajo agrupados por categoría

SQLite:    0.089 seg
PostgreSQL: 0.045 seg ✓ (Más rápido)
MySQL:     0.067 seg

Consulta: Top 10 productos más vendidos del mes

SQLite:    0.234 seg
PostgreSQL: 0.112 seg ✓ (Más rápido)
MySQL:     0.189 seg

[... más consultas ...]

═══════════════════════════════════
RESUMEN GENERAL:
PostgreSQL: 3 consultas más rápidas
SQLite: 1 consulta más rápida
MySQL: 1 consulta más rápida

🏆 Mejor rendimiento en consultas: PostgreSQL

💡 Conceptos clave

Patrón Factory: Crea objetos sin especificar la clase exacta
Abstracción de persistencia: Oculta los detalles específicos del SGBD
Migración de datos: Transferencia de datos entre sistemas diferentes
Benchmarking: Medición comparativa de rendimiento
Portabilidad: Capacidad de funcionar en diferentes entornos


📌 Pistas generales

Factory Pattern: Crea una fábrica que devuelva el DAO correcto según configuración
Diferencias SQL: Algunos tipos de datos y funciones varían entre SGBD:

Autoincremento: AUTOINCREMENT (SQLite), SERIAL (PostgreSQL), AUTO_INCREMENT (MySQL)
Fecha actual: date('now') (SQLite), CURRENT_DATE (PostgreSQL/MySQL)


Migración: Usa transacciones para garantizar consistencia
Rendimiento: Desactiva autocommit en inserciones masivas
Lotes: Para inserciones masivas, usa addBatch() y executeBatch()
Medición precisa: Usa System.nanoTime() para medir microsegundos
Configuración: Usa un archivo .properties para no hardcodear valores


✅ Criterios de éxito

 La aplicación se conecta correctamente a los 3 SGBD
 Se puede cambiar de SGBD mediante configuración
 El código DAO es común para los 3 SGBD (o con mínimas variaciones)
 La migración transfiere todos los datos correctamente
 Se mantiene la integridad referencial en las migraciones
 Los tests de rendimiento son precisos y reproducibles
 Se genera un informe comparativo legible
 Todas las operaciones CRUD funcionan en los 3 SGBD
 El código está bien estructurado y comentado
 Se aplican todos los conceptos de la unidad (PreparedStatement, transacciones, etc.)


🎯 Funcionalidades mínimas requeridas
Gestión de Inventario

 CRUD de categorías
 CRUD de productos
 Registro de movimientos de stock
 Consulta de stock actual por producto
 Alerta de productos con stock bajo

Migración

 Exportar datos desde SQLite
 Exportar datos desde PostgreSQL
 Exportar datos desde MySQL
 Importar datos a cualquier SGBD
 Log del proceso de migración

Medición de Rendimiento

 Test de inserciones masivas (3 tamaños: 1k, 5k, 10k)
 Test de consultas simples
 Test de consultas complejas (JOINs, agregaciones)
 Test de actualizaciones masivas
 Generación de informe comparativo


📊 Informe comparativo final
El informe debe incluir:

Tabla de tiempos de operaciones

OperaciónSQLitePostgreSQLMySQLGanadorInserción 1kX.XX sX.XX sX.XX sXXXInserción 5kX.XX sX.XX sX.XX sXXX...............

Gráfico de barras (ASCII art es suficiente)
Análisis y conclusiones:

¿Cuál es el más rápido en inserciones?
¿Cuál es el más rápido en consultas?
¿Cuál recomendarías para una aplicación web?
¿Cuál recomendarías para una aplicación móvil?
Ventajas y desventajas de cada uno




🎯 Extensiones opcionales (muy avanzado)

Implementa pool de conexiones real (HikariCP)
Añade soporte para Oracle Database
Implementa caché de consultas frecuentes
Crea una interfaz gráfica (JavaFX o Swing)
Añade exportación/importación a CSV y JSON
Implementa versionado de esquema (migrations)


⏱️ Tiempo estimado
6-8 horas

🎓 Reflexión final del proyecto
Al completar este ejercicio deberás ser capaz de responder:

Comparativa técnica: ¿Qué SGBD es objetivamente mejor según tus tests?
Casos de uso: ¿En qué escenarios usarías cada uno?
Portabilidad: ¿Qué tan difícil fue hacer la aplicación portable?
Aprendizajes: ¿Qué has aprendido sobre cada SGBD?
Mejoras futuras: ¿Cómo mejorarías la arquitectura de tu aplicación?


¡Este es el proyecto más complejo y completo de la Unidad 2!
Demuestra tu dominio de JDBC, arquitectura de software y gestión de bases de datos.
