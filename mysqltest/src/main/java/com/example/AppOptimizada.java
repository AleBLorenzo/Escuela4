package com.example;


import java.sql.ResultSet;

public class AppOptimizada {

    public static void main(String[] args) {
        
        ConexionMySql conexion = new ConexionMySql("localhost", "empleaods", "root", "pass");

        conexion.ejecutarConsulta("Select");

        ResultSet rs = conexion.getResulSet();

        try {
            while (rs.next()) {
                
                System.out.println(rs.getString("datos"));
                
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
