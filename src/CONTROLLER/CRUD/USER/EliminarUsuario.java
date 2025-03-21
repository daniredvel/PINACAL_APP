package CONTROLLER.CRUD.USER;

import DB.UTIL.GestorConexion;
import MODEL.Publicacion;
import MODEL.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EliminarUsuario {
    public static boolean eliminarUsuario(Connection conn, Usuario usuario) {
        String sql = "DELETE FROM USUARIOS WHERE id_usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuario.getId_usuario());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Debug statement
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
