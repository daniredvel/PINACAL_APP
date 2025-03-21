package CONTROLLER.CRUD.PUBLICACION;

import DB.UTIL.GestorConexion;
import MODEL.Publicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class EliminarPublicacion {

      public static boolean eliminarPublicacion(Publicacion publicacion) {
        String sql = "DELETE FROM PUBLICACIONES WHERE id_publicacion = ?";

        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, publicacion.getId_publicacion());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Debug statement
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}