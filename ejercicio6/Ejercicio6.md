Ejercicio 6: Gestión Avanzada de ResultSet (Intermedio)
📂 ejercicio06_intermedio_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Dominar la navegación bidireccional en ResultSet
Utilizar métodos avanzados de posicionamiento (first, last, absolute, relative)
Extraer metadatos de ResultSet (ResultSetMetaData)
Gestionar ResultSet vacíos y valores NULL
Comprender los diferentes tipos de ResultSet (scrollable, updatable)


📋 Descripción del ejercicio
El ResultSet no es solo un cursor unidireccional. Existen ResultSet "scrollables" que permiten navegación en ambas direcciones, y ResultSet "updateables" que permiten modificar datos directamente. En este ejercicio explorarás estas capacidades avanzadas y aprenderás a extraer metadatos.
Deberás crear una aplicación que:

Cree un ResultSet scrollable (navegable en ambas direcciones)
Navegue por los registros utilizando first(), last(), next(), previous(), absolute(), relative()
Extraiga y muestre metadatos del ResultSet (nombres de columnas, tipos de datos)
Gestione correctamente ResultSet vacíos
Detecte y maneje valores NULL en los registros


🔧 Configuración del entorno
Base de datos recomendada

PostgreSQL (mejor soporte para ResultSet scrollable)
Utiliza el contenedor creado en el Ejercicio 2

Tabla de trabajo
sqlCREATE TABLE productos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    precio DECIMAL(10,2),
    stock INTEGER,
    descripcion TEXT
);
Datos de ejemplo: Inserta al menos 10 productos, algunos con valores NULL en campos opcionales (categoría, descripción).

📝 Especificaciones técnicas
Tipos de ResultSet
Al crear el Statement, especifica el tipo:
javaStatement stmt = conexion.createStatement(
    ResultSet.TYPE_SCROLL_INSENSITIVE,  // Scrollable
    ResultSet.CONCUR_READ_ONLY          // Solo lectura
);
```

### Métodos de navegación a implementar

- `first()` → Posiciona en el primer registro
- `last()` → Posiciona en el último registro
- `next()` → Avanza al siguiente
- `previous()` → Retrocede al anterior
- `absolute(n)` → Posiciona en el registro n
- `relative(n)` → Se mueve n posiciones (+ adelante, - atrás)
- `getRow()` → Obtiene el número de fila actual
- `isFirst()`, `isLast()` → Verifica posición

### Metadatos a extraer

Utilizando `ResultSetMetaData`:
- Número de columnas
- Nombre de cada columna
- Tipo de dato de cada columna
- Si permite NULL
- Tamaño/precisión del campo

---

## 🧪 Casos de prueba

### Caso 1: Navegación básica
**Entrada**: Ejecutar operaciones de navegación
**Salida esperada (orientativa)**:
```
=== NAVEGACIÓN POR RESULTSET ===

[FIRST] Primer producto:
ID: 1 | Nombre: Teclado Mecánico | Precio: 89.99€

[LAST] Último producto:
ID: 10 | Nombre: Alfombrilla Gaming | Precio: 15.50€

[PREVIOUS] Producto anterior al último:
ID: 9 | Nombre: Auriculares USB | Precio: 45.00€

[ABSOLUTE(5)] Producto en posición 5:
ID: 5 | Nombre: Monitor 24" | Precio: 180.00€

[RELATIVE(2)] Dos posiciones adelante (ahora en 7):
ID: 7 | Nombre: Cable HDMI | Precio: 12.99€

[RELATIVE(-3)] Tres posiciones atrás (ahora en 4):
ID: 4 | Nombre: Mouse Inalámbrico | Precio: 25.99€
```

### Caso 2: Extracción de metadatos
**Entrada**: Obtener información de la estructura del ResultSet
**Salida esperada (orientativa)**:
```
=== METADATOS DEL RESULTSET ===

Número de columnas: 6

Columna 1:
  Nombre: id
  Tipo: INTEGER
  Permite NULL: NO
  Tamaño: 10

Columna 2:
  Nombre: nombre
  Tipo: VARCHAR
  Permite NULL: NO
  Tamaño: 100

Columna 3:
  Nombre: categoria
  Tipo: VARCHAR
  Permite NULL: SÍ
  Tamaño: 50

[... resto de columnas ...]
```

### Caso 3: Manejo de valores NULL
**Entrada**: Producto con campos opcionales NULL
**Salida esperada (orientativa)**:
```
=== DETECCIÓN DE VALORES NULL ===

Producto ID: 3
  Nombre: Webcam HD
  Categoría: [NULL - sin categoría]
  Precio: 55.00€
  Stock: 20
  Descripción: [NULL - sin descripción]
```

### Caso 4: ResultSet vacío
**Entrada**: Consulta que no devuelve resultados
**Salida esperada (orientativa)**:
```
=== CONSULTA SIN RESULTADOS ===
Buscando productos con precio > 1000€...
⚠ La consulta no devolvió resultados
ResultSet vacío (0 filas)

💡 Conceptos clave

TYPE_SCROLL_INSENSITIVE: ResultSet que no refleja cambios en la BD durante su uso
TYPE_SCROLL_SENSITIVE: ResultSet que refleja cambios en tiempo real
TYPE_FORWARD_ONLY: ResultSet unidireccional (por defecto)
CONCUR_READ_ONLY: No se puede modificar a través del ResultSet
CONCUR_UPDATABLE: Permite actualizaciones directas
ResultSetMetaData: Información sobre la estructura de los resultados
wasNull(): Método para verificar si el último valor leído era NULL


📌 Pistas generales

Creación de Statement: Recuerda especificar el tipo scrollable al crear el Statement
Verificación de posición: Usa getRow() para saber en qué fila estás
Detección de NULL: Después de leer un valor, usa rs.wasNull() para verificar
ResultSet vacío: Comprueba con if(!rs.next()) o rs.first() que retorna false
Metadatos: Obtén ResultSetMetaData con rs.getMetaData()
Índices de columnas: Comienzan en 1, no en 0
Performance: Los ResultSet scrollables consumen más memoria


✅ Criterios de éxito

 Se crea correctamente un ResultSet scrollable
 Se implementan todos los métodos de navegación (first, last, next, previous, absolute, relative)
 Se extraen y muestran los metadatos del ResultSet
 Se detectan y manejan correctamente los valores NULL
 Se gestiona correctamente un ResultSet vacío
 Se muestra la posición actual durante la navegación
 El código gestiona excepciones SQLException
 Se documentan las diferencias con ResultSet forward-only


🎯 Extensiones opcionales

Implementa actualización de datos directamente desde el ResultSet (CONCUR_UPDATABLE)
Crea una función genérica que imprima cualquier ResultSet con formato tabla
Implementa paginación manual utilizando absolute()
Compara el rendimiento entre ResultSet scrollable y forward-only


📚 Comparativa de tipos de ResultSet
Documenta en comentarios:
CaracterísticaFORWARD_ONLYSCROLL_INSENSITIVESCROLL_SENSITIVENavegaciónSolo adelanteBidireccionalBidireccionalCambios en BDN/ANo se reflejanSe reflejanMemoriaBajaMedia-AltaAltaUso típicoConsultas simplesNavegación complejaDatos en tiempo real

⏱️ Tiempo estimado
2.5-3 horas
