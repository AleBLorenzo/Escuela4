package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main5 {

    /*
    Características   SQLite      Postgre             MySQL            Oracle
    
    Tipo              Embebida  Cliente-Servidor  Cliente-Servidor  Cliente-Servidor

    Puerto            No           5432              3306              1521

    Formato URL      jdbc:sqlite:/ruta jdbc:postgresql://host:port/dbjdbc: jdbc:mysql://host:port/  jdbc:oracle:thin:@//host:post/

    Driver JDBC      org.sqlite.JDBC  org.postgresql.Driver  com.mysql.cj.jdbc.Driver  oracle.jdbc.driver.OracleDriver

    Credenciales     No requiere    Si                Si                Si

    Instalación      JAR         Docker/Instalacion   Docker/Instalacion  Docker/Instalación

    Complejidad	     Baja	       Media	           Media        	Alta

    Uso típico	     Apps móviles,  Aplicaciones web,    Aplicaciones web	  Sistemas empresariales
                     prototipos   sistemas medios/grande

 
    */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {

        String user = "system";
        String pass = "Oracle123";
        String SID_ServiceName = "XE";

        @SuppressWarnings("unused")
        String url_formato_SID = "jdbc:oracle:thin:@100.87.253.157:1521:" + SID_ServiceName;
        String url_formato_Service = "jdbc:oracle:thin:@//100.87.253.157:1521/" + SID_ServiceName;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            System.out.println("=== CONEXIÓN A ORACLE DATABASE ===");
            System.out.println("URL: " + url_formato_Service);
            System.out.println("Usuario: " + user);

            /*
            Para poder encontrar en mombre de la instancia creamos un asentecia q le asignamos
            la Query q queremos hacer un SELECT en este casi de las isntancias recoremos ese result ya
            que da variis resultados y sacamos la comumna q queremos con el getString().
            */

            try (Connection conexion = DriverManager.getConnection(url_formato_Service, user, pass);
                 Statement sentencia = conexion.createStatement();
                 ResultSet result = sentencia.executeQuery("SELECT instance_name FROM v$instance")) {

                Info(result, conexion);

            } catch (SQLException e) {
               
                if (e.getMessage().contains("ORA-12541")) {
                    System.out.println("No se pudo conectar a Oracle Database");
                    System.out.println("Listener refused the connection (ORA-12541)");
                    System.out.println("Asegúrate de que Oracle está en ejecución");
                    
                } else{
                    e.printStackTrace();
                }
               
            }

            System.out.println("Conexión cerrada correctamente");

        } catch (ClassNotFoundException ex) {
            System.getLogger(Main5.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    private static void Info(final ResultSet result, final Connection conexion) throws SQLException {
        String sid = null ;
        
        if (result.next()) {
            sid = result.getString("instance_name");
        }
        
        System.out.println("Conexión establecida exitosamente");
        System.out.println("Versión Oracle: " + conexion.getMetaData().getDatabaseProductVersion());
        System.out.println("SID: " + sid);
        System.out.println("Usuario conectado: " + conexion.getMetaData().getUserName());
    }
}