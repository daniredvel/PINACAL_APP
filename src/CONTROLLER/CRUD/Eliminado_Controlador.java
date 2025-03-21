package CONTROLLER.CRUD;

import DB.UTIL.GestorConexion;
import MODEL.ELIMINADO.Eliminado;
import MODEL.Publicacion;
import MODEL.ELIMINADO.Publicacion_eliminada;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import MODEL.ELIMINADO.Usuario_eliminado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Eliminado_Controlador extends Eliminado {


    // Method to delete a user
    public static String usuario_Eliminado(String mensaje, Usuario usuario) {
        String mensaje_retorno = Mensajes.getMensaje(Mensajes.ERROR_ELIMINAR);

        try (Connection conn = GestorConexion.getConexion()) {
            // Add the deleted user to the deleted users table
            Usuario_eliminado usuarioEliminado = new Usuario_eliminado(mensaje, usuario);
            String usuarioEliminadoSQL = "INSERT INTO Justificacion_eliminacion_usuario (mensaje, fecha, usuario_id) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(usuarioEliminadoSQL)) {
                pstmt.setString(1, usuarioEliminado.getMensaje());
                pstmt.setTimestamp(2, usuarioEliminado.getFecha());
                pstmt.setInt(3, usuario.getId_usuario());
                pstmt.executeUpdate();
            }
            // Delete the user from the database
            String deleteUserSQL = "DELETE FROM Usuarios WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteUserSQL)) {
                pstmt.setInt(1, usuario.getId_usuario());
                pstmt.executeUpdate();
            }

            mensaje_retorno = Mensajes.getMensaje(Mensajes.USUARIO_ELIMINADO);
        } catch (SQLException ignored) {}
        return mensaje_retorno;
    }

    // Method to delete a publication
    public static String publicacion_Eliminada(String mensaje, Publicacion publicacion) {
        String mensaje_retorno = Mensajes.getMensaje(Mensajes.ERROR_ELIMINAR);

        try (Connection conn = GestorConexion.getConexion()) {
            // Add the deleted publication to the deleted publications table
            Publicacion_eliminada publicacionEliminada = new Publicacion_eliminada(mensaje, publicacion);
            String insertPublicationEliminadaSQL = "INSERT INTO Justificaciones_eliminaciones_publicaciones (mensaje, fecha, publicacion_id) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertPublicationEliminadaSQL)) {
                pstmt.setString(1, "Publicación: " + publicacion.getTitulo().toUpperCase() + " eliminada por: " + publicacionEliminada.getMensaje());
                pstmt.setTimestamp(2, publicacionEliminada.getFecha());
                pstmt.setInt(3, publicacion.getId_publicacion());
                pstmt.executeUpdate();
            }
            // Delete the publication from the database
            String deletePublicationSQL = "DELETE FROM Publicaciones WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deletePublicationSQL)) {
                pstmt.setInt(1, publicacion.getId_publicacion());
                pstmt.executeUpdate();
            }

            mensaje_retorno = Mensajes.getMensaje(Mensajes.PUBLICACION_ELIMINADA);
        } catch (SQLException ignored) {}
        return mensaje_retorno;
    }
}