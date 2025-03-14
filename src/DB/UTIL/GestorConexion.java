package DB.UTIL;

import MODEL.UTIL.Mensajes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encarga de gestionar la conexión con la base de datos.
 */
public class GestorConexion {
    /**
     * Objeto estático de conexión con la base de datos.
     */
    private static Connection conexion=null;


    /**
     * METODO que crea una conexión con la base de datos.
     * @param bd Nombre de la base de datos.
     * @param usr Usuario de la base de datos.
     * @param pass Contraseña del usuario de la base de datos.
     * @return Código de error.
     */
    public static int crearConexion(String bd,String usr,String pass){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+bd,
                    usr,
                    pass
            );

            /*
            //Conexión remota a base de Datos MySQL

            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "mysql://mysql. nombre_dominio.es :3306/"+bd,
                    usr,
                    pass
            );
             */

            return Mensajes.OK;
        }
        catch(ClassNotFoundException cnfe){
            return Mensajes.FALLO_DRIVER;
        }
        catch(SQLException sqle){
            return Mensajes.FALLO_CONEXION;
        }
    }

    /**
     * Método que devuelve la conexión con la base de datos.
     * @return Conexión con la base de datos.
     */
    public static Connection getConexion() {
        return conexion;
    }

    /**
     * Método que cierra la conexión con la base de datos.
     * @return Código de error.
     */
    public static int cerrarConexion(){
        try {
            conexion.close();
            return Mensajes.OK;
        }
        catch(SQLException | NullPointerException sqle){
            return Mensajes.FALLO_CERRAR_CONEXION;
        }
    }
}

