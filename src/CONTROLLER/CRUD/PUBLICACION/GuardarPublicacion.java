package CONTROLLER.CRUD.PUBLICACION;

import DB.UTIL.GestorConexion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import MODEL.Publicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import static UTIL.Formato.toStringDate;

public class GuardarPublicacion {


    public static String guardarPublicacion(Publicacion publicacion, Usuario usuario){
        //Indicamos la fecha de guardado, la publicacion guardad y el usuario que guarda dicha publicacion, el que ha iniciado sesion
            String sql = "INSERT INTO PUBLICACIONES_GUARDADAS (fecha_guardado, id_publicacion, id_usuario) VALUES ( ?, ?, ?)";
        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, toStringDate(new Timestamp(System.currentTimeMillis())));
            ps.setInt(2, publicacion.getId_publicacion());
            ps.setInt(3, usuario.getId_usuario());
            ps.executeUpdate();
            return Mensajes.getMensaje(Mensajes.PUBLICACION_GUARDADA);
        } catch (SQLException e) {
            return Mensajes.getMensaje(Mensajes.ERROR_GUARDAR_PUBLICACION);
        }
    }

    public static String retirarGuardadoPublicacion(Publicacion publicacion, Usuario usuario) {
        //Indicamos la publicacion guardada y el usuario que ha guardado dicha publicacion, el que ha iniciado sesion
        //Y eliminamos la publicacion guardada de la tabla de PUBLICACIONES_GUARDADAS
        String sql = "DELETE FROM PUBLICACIONES_GUARDADAS WHERE id_publicacion = ?";
        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, publicacion.getId_publicacion());
            ps.setInt(2, usuario.getId_usuario());
            ps.executeUpdate();
            return Mensajes.getMensaje(Mensajes.PUBLICACION_RETIRADA);
        } catch (SQLException e) {
            return Mensajes.getMensaje(Mensajes.ERROR_RETIRAR_PUBLICACION);
        }
    }

}
