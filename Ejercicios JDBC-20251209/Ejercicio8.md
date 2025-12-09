Ejercicio 8: Transacciones y Gestión de Errores (Avanzado)
📂 ejercicio08_avanzado_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Comprender el concepto de transacción en bases de datos
Gestionar transacciones manualmente con JDBC
Utilizar commit() y rollback() correctamente
Implementar control de errores robusto con SQLException
Garantizar la integridad referencial mediante transacciones
Comprender los niveles de aislamiento de transacciones


📋 Descripción del ejercicio
Las transacciones garantizan que un conjunto de operaciones se ejecuten completamente o no se ejecuten en absoluto (atomicidad). En este ejercicio implementarás un sistema de transferencias bancarias que requiere control transaccional estricto para mantener la consistencia de los datos.
Deberás crear una aplicación que:

Implemente un sistema de cuentas bancarias
Realice transferencias entre cuentas utilizando transacciones
Gestione errores y realice rollback cuando sea necesario
Garantice que ninguna operación deje los datos en estado inconsistente
Implemente diferentes escenarios: éxito, error, saldo insuficiente


🔧 Configuración del entorno
Base de datos recomendada

PostgreSQL (mejor soporte para transacciones y niveles de aislamiento)

Estructura de tablas
sqlCREATE TABLE cuentas (
    id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20) UNIQUE NOT NULL,
    titular VARCHAR(100) NOT NULL,
    saldo DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    fecha_apertura DATE DEFAULT CURRENT_DATE,
    activa BOOLEAN DEFAULT TRUE,
    CONSTRAINT saldo_positivo CHECK (saldo >= 0)
);

CREATE TABLE movimientos (
    id SERIAL PRIMARY KEY,
    cuenta_origen_id INTEGER REFERENCES cuentas(id),
    cuenta_destino_id INTEGER REFERENCES cuentas(id),
    concepto VARCHAR(200),
    importe DECIMAL(12,2) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'COMPLETADO',
    CONSTRAINT importe_positivo CHECK (importe > 0)
);
Datos iniciales: Crea al menos 3 cuentas con saldos diferentes.

📝 Especificaciones técnicas
Estructura de una transacción
javaConnection conn = null;
try {
    conn = obtenerConexion();
    
    // DESACTIVAR AUTOCOMMIT
    conn.setAutoCommit(false);
    
    // OPERACIONES DE LA TRANSACCIÓN
    // ... (varias operaciones)
    
    // SI TODO OK → COMMIT
    conn.commit();
    
} catch (SQLException e) {
    // SI ERROR → ROLLBACK
    if (conn != null) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            // Log del error de rollback
        }
    }
} finally {
    // RESTAURAR AUTOCOMMIT
    if (conn != null) {
        conn.setAutoCommit(true);
        conn.close();
    }
}
```

### Operación de transferencia (debe ser transaccional)

Una transferencia completa implica:
1. Verificar que la cuenta origen existe y está activa
2. Verificar que la cuenta destino existe y está activa
3. Verificar que hay saldo suficiente
4. Restar el importe de la cuenta origen
5. Sumar el importe a la cuenta destino
6. Registrar el movimiento en la tabla `movimientos`
7. Si cualquier paso falla → ROLLBACK de todo

---

## 🧪 Casos de prueba

### Caso 1: Transferencia exitosa
**Entrada**:
- Cuenta origen: `ES0001` (saldo: 1000€)
- Cuenta destino: `ES0002` (saldo: 500€)
- Importe: 200€

**Salida esperada (orientativa)**:
```
=== INICIANDO TRANSFERENCIA ===
Cuenta origen: ES0001 (Ana García) - Saldo: 1000.00€
Cuenta destino: ES0002 (Carlos Pérez) - Saldo: 500.00€
Importe: 200.00€

[TRANSACCIÓN INICIADA]
  → Verificando cuenta origen... ✓
  → Verificando cuenta destino... ✓
  → Verificando saldo suficiente... ✓ (1000.00€ >= 200.00€)
  → Actualizando cuenta origen... ✓ (-200.00€)
  → Actualizando cuenta destino... ✓ (+200.00€)
  → Registrando movimiento... ✓
[COMMIT REALIZADO]

✓ Transferencia completada exitosamente

Saldos finales:
  ES0001: 800.00€
  ES0002: 700.00€
```

### Caso 2: Saldo insuficiente (rollback)
**Entrada**:
- Cuenta origen: `ES0001` (saldo: 800€)
- Cuenta destino: `ES0002` (saldo: 700€)
- Importe: 1000€

**Salida esperada (orientativa)**:
```
=== INICIANDO TRANSFERENCIA ===
Cuenta origen: ES0001 (Ana García) - Saldo: 800.00€
Cuenta destino: ES0002 (Carlos Pérez) - Saldo: 700.00€
Importe: 1000.00€

[TRANSACCIÓN INICIADA]
  → Verificando cuenta origen... ✓
  → Verificando cuenta destino... ✓
  → Verificando saldo suficiente... ✗ (800.00€ < 1000.00€)
[ROLLBACK REALIZADO]

✗ Transferencia cancelada: Saldo insuficiente

Saldos sin cambios:
  ES0001: 800.00€
  ES0002: 700.00€
```

### Caso 3: Cuenta destino inexistente (rollback)
**Entrada**:
- Cuenta origen: `ES0001` (saldo: 800€)
- Cuenta destino: `ES9999` (no existe)
- Importe: 100€

**Salida esperada (orientativa)**:
```
=== INICIANDO TRANSFERENCIA ===
Cuenta origen: ES0001 (Ana García) - Saldo: 800.00€
Cuenta destino: ES9999
Importe: 100.00€

[TRANSACCIÓN INICIADA]
  → Verificando cuenta origen... ✓
  → Verificando cuenta destino... ✗ (No existe)
[ROLLBACK REALIZADO]

✗ Transferencia cancelada: Cuenta destino no encontrada

Saldos sin cambios:
  ES0001: 800.00€
```

### Caso 4: Error durante la transacción (rollback automático)
**Entrada**: Simula un error (ej. cerrar conexión durante la transacción)
**Salida esperada (orientativa)**:
```
=== INICIANDO TRANSFERENCIA ===
[TRANSACCIÓN INICIADA]
  → Verificando cuenta origen... ✓
  → Actualizando cuenta origen... ✓
  → [SIMULACIÓN DE ERROR]
[ROLLBACK AUTOMÁTICO POR EXCEPCIÓN]

✗ Error durante la transferencia: [Detalle del error]
✓ Todas las operaciones revertidas

Saldos sin cambios (integridad garantizada)

💡 Conceptos clave

Transacción: Conjunto de operaciones que se ejecutan como una unidad atómica
ACID: Propiedades de las transacciones (Atomicity, Consistency, Isolation, Durability)
Autocommit: Modo por defecto donde cada sentencia hace commit automático
Commit: Confirma los cambios de la transacción en la base de datos
Rollback: Deshace todos los cambios de la transacción
SQLException: Excepción que contiene información detallada del error


📌 Pistas generales

Desactivar autocommit: Siempre usar setAutoCommit(false) al inicio de la transacción
Try-catch-finally: Estructura fundamental para gestión de transacciones
Rollback en catch: Siempre hacer rollback si hay excepción
Restaurar autocommit: En el finally, volver a activar autocommit
Verificaciones previas: Valida todo ANTES de modificar datos
Orden de operaciones: Planifica el orden lógico de las operaciones
SQLException.getErrorCode(): Útil para distinguir tipos de errores
Mensajes detallados: Informa al usuario qué salió mal y por qué


🔄 Propiedades ACID
Documenta en tu código cómo tu implementación garantiza:

Atomicity (Atomicidad): Todo se ejecuta o nada se ejecuta
Consistency (Consistencia): Los datos quedan en estado válido
Isolation (Aislamiento): Las transacciones no interfieren entre sí
Durability (Durabilidad): Los cambios confirmados persisten


✅ Criterios de éxito

 Las tablas se crean con las restricciones adecuadas
 La transferencia exitosa actualiza ambas cuentas y registra el movimiento
 Se desactiva autocommit al inicio de la transacción
 Se realiza commit solo si todas las operaciones son exitosas
 Se realiza rollback en caso de cualquier error
 Se restaura autocommit en el bloque finally
 Se valida saldo suficiente antes de modificar datos
 Se verifican cuentas existentes antes de operar
 Los mensajes de error son claros e informativos
 La integridad de los datos se mantiene siempre


🎯 Extensiones opcionales

Implementa niveles de aislamiento diferentes (setTransactionIsolation())
Añade un historial completo de movimientos por cuenta
Implementa transferencias programadas (con fecha futura)
Crea un sistema de auditoría de transacciones fallidas
Añade límites diarios de transferencia por cuenta


📚 Niveles de aislamiento
Investiga y documenta (opcional):
NivelDescripciónProblema que previeneREAD_UNCOMMITTEDMenor aislamientoNingunoREAD_COMMITTEDPor defecto en muchos SGBDDirty readsREPEATABLE_READLee datos consistentesNon-repeatable readsSERIALIZABLEMáximo aislamientoPhantom reads

⏱️ Tiempo estimado
3-4 horas
