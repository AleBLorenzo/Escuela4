package com.example;


public class ConexionMySql extends ConexionBD{

    public ConexionMySql(String host ,String puerto ,String baseDatos ,String usuario ,String pass){
        super("com.mysql.jdbc.Driver","jdbc:mysql://"+host+":"+puerto+"/"+ baseDatos, usuario , pass);
    }

     public ConexionMySql(String host ,String baseDatos ,String usuario ,String pass){
        super("com.mysql.jdbc.Driver","jdbc:mysql://"+host+"/"+ baseDatos, usuario , pass);
        
    }
}
