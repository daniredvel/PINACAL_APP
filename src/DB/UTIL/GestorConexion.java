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
    private static Connection conexion = null;

    /**
     * METODO que crea una conexión con la base de datos.
     * @param bd Nombre de la base de datos.
     * @param usr Usuario de la base de datos.
     * @param pass Contraseña del usuario de la base de datos.
     * @return Código de error.
     */
    public static int crearConexion(String bd, String usr, String pass) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + bd,
                    usr,
                    pass
            );
            System.out.println("Conexión a la base de datos establecida correctamente."); // Debug statement
            return Mensajes.OK;
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error: Driver no encontrado."); // Debug statement
            return Mensajes.FALLO_DRIVER;
        } catch (SQLException sqle) {
            System.err.println("Error: No se pudo establecer la conexión a la base de datos."); // Debug statement
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
    public static int cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión a la base de datos cerrada correctamente."); // Debug statement
                return Mensajes.OK;
            } else {
                return Mensajes.FALLO_CERRAR_CONEXION;
            }
        } catch (SQLException sqle) {
            System.err.println("Error: No se pudo cerrar la conexión a la base de datos."); // Debug statement
            return Mensajes.FALLO_CERRAR_CONEXION;
        }
    }
}