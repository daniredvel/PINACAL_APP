package CONTROLLER.VALIDATION;

import DB.UTIL.GestorConexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ControladorInicioSesion {
    private static String password;
    private static String usuario;

    public static int comprobarPass(String usuario, String password) throws Exception {

        Connection conn = GestorConexion.getConexion();

        //Obtenemos la contraseña del usuario
        PreparedStatement consulta = conn.prepareStatement("SELECT * FROM usuarios WHERE nombre = ?");
        consulta.setString(1, usuario);

        ResultSet resultSet = consulta.executeQuery();

        //Si devuelve 1, el usuario y la contraseña son correctos
        //Si devuelve -1, la contraseña es incorrecta
        //Si devuelve 0, el usuario no existe

        //Comprobamos si el usuario existe
        if (resultSet.next()) {
            //String passDesencriptada = CodificadorPassword.descodificar(resultSet.getString("password"));
            String passDesencriptada = resultSet.getString("password");
                //Comprobamos si la contraseña es correcta
            if (password.equals(passDesencriptada)) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }

    }
//TODO:Eliminar método
    public static int comprobarPass(String usuario, String password, String op) throws Exception {

        //Si devuelve 1, el usuario y la contraseña son correctos
        //Si devuelve -1, la contraseña es incorrecta
        //Si devuelve 0, el usuario no existe

        if (usuario.equals("admin")) {
            if (password.equals("admin")) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }

    }
}
