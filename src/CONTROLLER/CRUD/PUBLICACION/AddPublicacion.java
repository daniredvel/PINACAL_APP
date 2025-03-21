package CONTROLLER.CRUD.PUBLICACION;

import DB.UTIL.GestorConexion;
import MODEL.Publicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AddPublicacion {

    public static boolean crearPublicacion(Publicacion publicacion) {
        String sql = "INSERT INTO PUBLICACIONES (titulo, descripcion, fecha, tipo, id_usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, publicacion.getTitulo());
            ps.setString(2, publicacion.getDescripcion());
            ps.setTimestamp(3, new Timestamp(publicacion.getFecha_publicacion().getTime()));
            ps.setString(4, publicacion.getTipo());
            ps.setInt(5, publicacion.getId_usuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}