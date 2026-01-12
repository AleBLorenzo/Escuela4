package com.example.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ConexionBD {

    protected Connection connection;
    protected Statement sentencia;
    protected ResultSet resulSet;

    public ConexionBD(String ClaseNombre, String cadenaConexion) {

        try {
            Class.forName(ClaseNombre);
            connection = DriverManager.getConnection(cadenaConexion);

            // Evita q los comint se hagas hasta q uno se lo indique es por seguridad
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {

            System.out.println(e.getMessage());
        }

    }

    public ConexionBD(String ClaseNombre, String cadenaConexion, String usuario, String pass) {

        try {
            Class.forName(ClaseNombre);
            connection = DriverManager.getConnection(cadenaConexion, usuario, pass);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {

            System.out.println(e.getMessage());
        }

    }

    //--GETTERS AND SETTERS--

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getSentencia() {
        return sentencia;
    }

    public void setSentencia(Statement sentencia) {
        this.sentencia = sentencia;
    }

    public ResultSet getResulSet() {
        return resulSet;
    }

    public void setResulSet(ResultSet resulSet) {
        this.resulSet = resulSet;
    }

    //--METODOS--

    public void commit() {

        try {
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {

        try {
            connection.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarResult() {

        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarSentencia() {

        try {
            sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarConexion() {

        try {
            if (resulSet != null) {
                cerrarResult();

            }
            if (sentencia != null) {
                cerrarSentencia();
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ejecutarConsulta(String consulta) {

        try {

            sentencia = connection.createStatement();
            resulSet = sentencia.executeQuery(consulta);

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        public int ejecutarIntruccion(String intruccion) {

            int filas = 0 ;
        try {

            sentencia = connection.createStatement();
            filas = sentencia.executeUpdate(intruccion);

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filas;
    }

            public int ejecutarIntruccionCommit(String intruccion , boolean commit) {

            int filas = 0 ;
         try {

            sentencia = connection.createStatement();
            filas = sentencia.executeUpdate(intruccion);

            if (commit) {
                commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filas;
    }



            public boolean existeValor(String valor ,String columna ,String tabla ) {

            boolean existe = false;

            Statement sentenciaAux;
         try {

            sentenciaAux = connection.createStatement();
            ResultSet aux = sentenciaAux.executeQuery("select count(*) from "+tabla+" where upper("+ columna+") = "+ valor);

            aux.next();

            if (aux.getInt(1) >= 1) {
                existe= true;
            }

            aux.close();
            sentenciaAux.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }



}
