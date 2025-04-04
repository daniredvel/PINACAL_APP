package CONTROLLER.CRUD.PUBLICACION;

import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import MODEL.Publicacion;
import VIEW.ERROR.Error_INICIAR_BD;
import VIEW.PUBLICACIONES.Publicacion_Detalle_Vista;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static DB.UTIL.CrearConn.conn;


public class GuardarPublicacion {
    private static final Logger LOGGER = Logger.getLogger(Publicacion_Detalle_Vista.class.getName());


    public static String guardarPublicacion(Publicacion publicacion, Usuario usuario, Connection conexion){


        Connection conn = conexion;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, Mensajes.getMensaje(Mensajes.FALLO_CONEXION));
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }



        //Indicamos la fecha de guardado, la publicacion guardad y el usuario que guarda dicha publicacion, el que ha iniciado sesion
            //String sql = "INSERT INTO PUBLICACIONES_GUARDADAS (fecha_guardado, id_publicacion, id_usuario) VALUES ( ?, ?, ?)";
            String sql = "INSERT INTO PUBLICACIONES_GUARDADAS (id_publicacion, id_usuario) VALUES (?, ?)";
        try {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                //ps.setString(1, toStringDate(new Timestamp(System.currentTimeMillis())));
                ps.setInt(1, publicacion.getId_publicacion());
                ps.setInt(2, usuario.getId_usuario());
                ps.executeUpdate();
                return Mensajes.getMensaje(Mensajes.PUBLICACION_GUARDADA);
            }
        } catch (SQLException e) {
            return Mensajes.getMensaje(Mensajes.ERROR_GUARDAR_PUBLICACION) + "ERROR: " + e.getMessage();
        }
    }

    public static String retirarGuardadoPublicacion(Publicacion publicacion, Usuario usuario, Connection conexion){


        Connection conn = conexion;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, Mensajes.getMensaje(Mensajes.FALLO_CONEXION));
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }

        //Indicamos la publicacion guardada y el usuario que ha guardado dicha publicacion, el que ha iniciado sesion
        //Y eliminamos la publicacion guardada de la tabla de PUBLICACIONES_GUARDADAS
        String sql = "DELETE FROM PUBLICACIONES_GUARDADAS WHERE id_publicacion = ? AND id_usuario = ?";
        try {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, publicacion.getId_publicacion());
                ps.setInt(2, usuario.getId_usuario());
                ps.executeUpdate();
                return Mensajes.getMensaje(Mensajes.PUBLICACION_RETIRADA);
            }
        } catch (SQLException e) {
            return Mensajes.getMensaje(Mensajes.ERROR_RETIRAR_PUBLICACION);
        }
    }

}
