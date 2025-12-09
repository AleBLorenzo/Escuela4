Ejercicio 4: Conexión a Oracle Database (Básico)
📂 ejercicio04_basico_ENUNCIADO_ALUMNO.md
🎯 Objetivos de aprendizaje

Conectar a Oracle Database mediante JDBC
Comprender las particularidades del driver JDBC de Oracle
Realizar una comparativa completa de los 4 SGBD trabajados
Consolidar conocimientos sobre conexiones JDBC


📋 Descripción del ejercicio
Oracle Database es uno de los sistemas gestores de bases de datos más utilizados en entornos empresariales. En este ejercicio aprenderás a conectarte a Oracle y consolidarás los conocimientos adquiridos realizando una comparativa de los cuatro SGBD estudiados.
Deberás:

Desplegar Oracle Database (mediante Docker o instalación local)
Configurar el driver JDBC de Oracle (ojdbc)
Establecer conexión desde Java
Realizar una comparativa completa de los 4 SGBD: SQLite, PostgreSQL, MySQL y Oracle


🔧 Configuración del entorno
Opción 1: Oracle con Docker (Recomendada)
bashdocker run --name oracle-acceso-datos \
  -e ORACLE_PASSWORD=Oracle123 \
  -p 1521:1521 \
  -d gvenzl/oracle-xe:21-slim
Nota: La primera vez puede tardar varios minutos en estar disponible.
Verificar logs:
bashdocker logs -f oracle-acceso-datos
Espera hasta ver el mensaje: DATABASE IS READY TO USE!
Opción 2: Oracle Express Edition (XE) local
Si prefieres instalación local, descarga Oracle XE desde oracle.com
Dependencias del proyecto
Maven:
xml<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc8</artifactId>
    <version>23.3.0.23.09</version>
</dependency>
Gradle:
gradleimplementation 'com.oracle.database.jdbc:ojdbc8:23.3.0.23.09'
```

---

## 📝 Especificaciones técnicas

### Información de conexión (Docker)
- **Host**: `localhost`
- **Puerto**: `1521`
- **SID/Service Name**: `XE`
- **Usuario**: `system`
- **Contraseña**: `Oracle123`
- **URL formato SID**: `jdbc:oracle:thin:@localhost:1521:XE`
- **URL formato Service**: `jdbc:oracle:thin:@//localhost:1521/XE`
- **Driver**: `oracle.jdbc.driver.OracleDriver`

### Estructura de la aplicación

Tu aplicación debe incluir:
- Clase principal con método `main`
- Método para conectar a Oracle
- Método que muestre información de la conexión
- **TABLA COMPARATIVA** completa de los 4 SGBD trabajados
- Conclusiones sobre ventajas/desventajas de cada uno

---

## 🧪 Casos de prueba

### Caso 1: Conexión exitosa
**Entrada**: Ejecutar con Oracle Database disponible
**Salida esperada (orientativa)**:
```
=== CONEXIÓN A ORACLE DATABASE ===
URL: jdbc:oracle:thin:@localhost:1521:XE
Usuario: system
✓ Conexión establecida exitosamente
✓ Versión Oracle: Oracle Database 21c Express Edition
✓ SID: XE
✓ Usuario conectado: SYSTEM
✓ Conexión cerrada correctamente

=== COMPARATIVA DE LOS 4 SGBD ===
[Mostrar tabla comparativa]
```

### Caso 2: Base de datos no disponible
**Entrada**: Oracle Database no iniciado
**Salida esperada (orientativa)**:
```
✗ No se pudo conectar a Oracle Database
Listener refused the connection (ORA-12541)
Asegúrate de que Oracle está en ejecución

📊 Tabla comparativa requerida
Tu aplicación debe mostrar (por consola o en comentarios) una comparativa que incluya al menos:
CaracterísticaSQLitePostgreSQLMySQLOracleTipoEmbebidaCliente-ServidorCliente-ServidorCliente-ServidorPuerto por defectoN/A543233061521Formato URLjdbc:sqlite:rutajdbc:postgresql://host:port/dbjdbc:mysql://host:port/dbjdbc:oracle:thin:@host:port:sidDriverorg.sqlite.JDBCorg.postgresql.Drivercom.mysql.cj.jdbc.Driveroracle.jdbc.driver.OracleDriverCredencialesNo requiereSíSíSíInstalaciónJARDocker/InstalaciónDocker/InstalaciónDocker/InstalaciónComplejidadBajaMediaMediaAltaUso típicoApps móviles, prototiposAplicaciones web, sistemas medianos/grandesAplicaciones webSistemas empresariales

💡 Conceptos clave

SID vs Service Name: Oracle puede usar dos formatos de identificación de base de datos
Thin Driver: Driver JDBC puro de Java para Oracle (no requiere Oracle Client)
Listener: Servicio de Oracle que escucha conexiones entrantes en el puerto 1521
Usuario SYSTEM: Usuario administrativo predeterminado en Oracle


📌 Pistas generales

Tiempo de inicio: Oracle puede tardar 2-5 minutos en estar disponible tras levantar el contenedor
Logs de Docker: Monitoriza con docker logs -f oracle-acceso-datos
Formato de URL: Oracle acepta dos formatos (SID y Service Name), prueba ambos
Credenciales: Usuario system es el administrador por defecto
Comparativa: Incluye aspectos técnicos, pero también reflexiones sobre cuándo usar cada SGBD


✅ Criterios de éxito

 Oracle Database se ejecuta correctamente (Docker o local)
 La conexión JDBC se establece exitosamente
 Se muestra información del servidor Oracle
 Se incluye una tabla comparativa completa de los 4 SGBD
 Se añaden conclusiones sobre ventajas/desventajas de cada SGBD
 El código gestiona excepciones adecuadamente
 La conexión se cierra correctamente
 El código está bien documentado


🎓 Reflexión adicional
Incluye en tu proyecto (como comentario o documento) una reflexión sobre:

¿Cuándo usarías SQLite vs un SGBD cliente-servidor?
¿Qué ventajas tiene PostgreSQL sobre MySQL o viceversa?
¿En qué escenarios justifica usar Oracle a pesar de su complejidad?
¿Qué SGBD recomendarías para un proyecto académico? ¿Y para producción?


🐳 Comandos Docker útiles para Oracle
bash# Ver logs (espera hasta DATABASE IS READY TO USE!)
docker logs -f oracle-acceso-datos

# Detener Oracle
docker stop oracle-acceso-datos

# Iniciar Oracle
docker start oracle-acceso-datos

# Acceder a SQL*Plus dentro del contenedor
docker exec -it oracle-acceso-datos sqlplus system/Oracle123@XE

# Eliminar contenedor
docker rm -f oracle-acceso-datos

⏱️ Tiempo estimado
2-2.5 horas (incluye tiempo de descarga e inicio de Oracle)
