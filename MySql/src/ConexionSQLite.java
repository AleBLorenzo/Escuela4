public class ConexionSQLite extends ConexionBD {

    public ConexionSQLite(String ruta) {

        super("org.sqlite.JDBC", "jdbc:sqlite:" + ruta);

    }

}
