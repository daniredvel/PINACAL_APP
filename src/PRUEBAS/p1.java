package PRUEBAS;

import DB.UTIL.GestorConexion;
import VIEW.ERROR.Error_INICIAR_BD;
import VIEW.INICIO_SESION.InicioSesion_Vista;

import javax.swing.*;
import java.sql.Connection;

import static DB.UTIL.CrearConn.crearConexion;

public class p1 {

    private static Connection conn;
    public static void main(String[] args) {
        if (crearConexion()) {
            conn = GestorConexion.getConexion();
            //Llamamos a la vista de inicio de sesion para iniciar el programa
            SwingUtilities.invokeLater(() -> new InicioSesion_Vista(conn).setVisible(true));
        } else{
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }
    }

}