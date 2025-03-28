package CONTROLLER.CRUD.PUBLICACION;

import CONTROLLER.ControladorDatos;
import DB.UTIL.GestorConexion;
import MODEL.Publicacion;
import MODEL.UTIL.Mensajes;
import VIEW.ERROR.Error_INICIAR_BD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import static DB.UTIL.CrearConn.conn;

public class EliminarPublicacion {
    private static final Logger LOGGER = Logger.getLogger(EliminarPublicacion.class.getName());

      public static boolean eliminarPublicacion(Publicacion publicacion) {

          Connection conn = GestorConexion.getConexion();

          // Si la conexión es nula, se crea una nueva
          if (conn == null) conn = conn();

          // Nos aseguramos de que la conexión no sea nula
          // Si la conexión es nula, se muestra la ventana de error de la aplicación
          if (conn == null) {
              LOGGER.log(Level.SEVERE, Mensajes.getMensaje(Mensajes.FALLO_CONEXION));
              SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
          }


        String sql = "DELETE FROM PUBLICACIONES WHERE id_publicacion = ?";

        try {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, publicacion.getId_publicacion());
                int rowsAffected = ps.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected); // Debug statement
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}