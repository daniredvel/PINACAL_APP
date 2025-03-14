package CONTROLLER.GUARDAR;

import DB.UTIL.GestorConexion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import MODEL.Publicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class GuardarPublicacion {

    private static Connection connect() throws SQLException {
        // Conectar a la base de datos
        return GestorConexion.getConexion();
    }

    public static String guardarPublicacion(Publicacion publicacion, Usuario usuario){
            String sql = "INSERT INTO PUBLICACIONES_GUARDADAS (fecha_guardado, id_publicacion, id_usuario) VALUES ( ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, Timestamp.from(Instant.ofEpochSecond(System.currentTimeMillis())).toString());
            ps.setInt(2, publicacion.getId_publicacion());
            ps.setInt(3, usuario.getId_usuario());
            ps.executeUpdate();
            return Mensajes.getMensaje(Mensajes.PUBLICACION_GUARDADA);
        } catch (SQLException e) {
            return Mensajes.getMensaje(Mensajes.ERROR_GUARDAR_PUBLICACION);
        }
    }

    public static String retirarGuardadoPublicacion(Publicacion publicacion, Usuario usuario) {
        String sql = "DELETE FROM PUBLICACIONES_GUARDADAS WHERE id_publicacion = ? AND id_usuario = ?";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, publicacion.getId_publicacion());
            ps.setInt(2, usuario.getId_usuario());
            ps.executeUpdate();
            return Mensajes.getMensaje(Mensajes.PUBLICACION_RETIRADA);
        } catch (SQLException e) {
            return Mensajes.getMensaje(Mensajes.ERROR_RETIRAR_PUBLICACION);
        }
    }

}
