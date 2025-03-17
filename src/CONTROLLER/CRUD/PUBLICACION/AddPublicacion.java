package CONTROLLER.CRUD.PUBLICACION;

import DB.UTIL.GestorConexion;
import MODEL.Publicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPublicacion {

    public boolean crearPublicacion(Publicacion publicacion) {
        String sql = "INSERT INTO PUBLICACIONES (titulo, descripcion, fecha_publicacion, tipo, id_usuario, usuario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, publicacion.getTitulo());
            ps.setString(2, publicacion.getDescripcion());
            ps.setDate(3, new java.sql.Date(publicacion.getFecha_publicacion().getTime()));
            ps.setString(4, publicacion.getTipo());
            ps.setInt(5, publicacion.getId_usuario());
            ps.setString(6, publicacion.getUsuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean eliminarPublicacion(Publicacion publicacion) {
        String sql = "DELETE FROM PUBLICACIONES WHERE id_publicacion = ?";

        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, publicacion.getId_publicacion());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}