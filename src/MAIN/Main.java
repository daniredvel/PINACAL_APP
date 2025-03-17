package MAIN;

import DB.UTIL.GestorConexion;
import VIEW.ERROR.Error_INICIAR_BD;
import VIEW.INICIO_SESION.InicioSesion_Vista;

import javax.swing.*;
import java.sql.Connection;

public class Main {
        private static final String DB_NAME = "Pinacal";
        private static final String DB_USER = "Pinacal";
        private static final String DB_PASSWORD = "pinacal";
        private static Connection conn;
    public static void main(String[] args) {
        if (crearConexion()) {
            conn = GestorConexion.getConexion();
            //Llamamos a la vista de inicio de sesion para iniciar el programa
            SwingUtilities.invokeLater(() -> new InicioSesion_Vista(conn).setVisible(true));
        } else{
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }
        GestorConexion.cerrarConexion();
    }
        public static boolean crearConexion() {
            int estadoConexion = GestorConexion.crearConexion(DB_NAME, DB_USER, DB_PASSWORD);
            return estadoConexion == 0;
        }
}