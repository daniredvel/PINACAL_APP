package CONTROLLER.CRUD.USER;

import DB.UTIL.GestorConexion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActualizarUsuario {

    public static String actualizarUsuario(Usuario usuario, Connection conn) {
        //int id, String nombre, String email, String telefono, String password, String calle, String numero, String localidad, String municipio, String provincia, String codigoPostal, String pais

        if (actualizarUsuarioBD(usuario,conn)) return Mensajes.getMensaje(Mensajes.USUARIO_ACTUALIZADO);
        else return Mensajes.getMensaje(Mensajes.ERROR_ACTUALIZAR_USUARIO);
    }

    private static boolean actualizarUsuarioBD(Usuario usuario, Connection conn) {
        String sql = "UPDATE USUARIOS SET nombre = ?, password = ?, email = ?, direccion = ?, telefono = ?, id_tipo_usuario = ? WHERE id_usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getTelefono());
            ps.setInt(6, usuario.getindice_tipo_usuario());
            ps.setInt(7, usuario.getId_usuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
            return false;
        }

    }
}