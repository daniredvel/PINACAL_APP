package CONTROLLER.VALIDATION;

import CONTROLLER.ENCRIPTACION.ControladorEncriptacion;
import DB.UTIL.GestorConexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ControladorInicioSesion {


    public static int comprobarPass(String usuario, String pass) throws Exception {

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
            String passDB = resultSet.getString("password");
                //Comprobamos si la contraseña es correcta
            if (ControladorEncriptacion.comprobar(pass, passDB)) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }

    }
}
