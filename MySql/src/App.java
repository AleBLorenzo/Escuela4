
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws Exception {
        
        //Hay q tener el .jar y tambien impartar la class con este nombre

        Class.forName("com.mysql.jdbc.Driver");
        
        //Establecemos la connecion y ponemos la ruta el user y la passwred
        Connection conecion = DriverManager.getConnection("jdbc:mysql://localhost/Empleados", "root", "root");

        //Crea una sentencia
        Statement sentencia = conecion.createStatement();
        //Envia la sentecia y trae la info
        ResultSet resulset = sentencia.executeQuery("select * FROM empleados");


        //Recorre la informacion obtenida

        while (resulset.next()) { 
            
            //Imprime solo los datos con esa etiqueta

            System.out.println(resulset.getString("nombre"));
        }



        //Recodar cerrar todo en ese orden 
        resulset.close();
        sentencia.close();

        conecion.close();
    }
}
