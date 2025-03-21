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

        if (actualizarUsuarioBD(usuario)) return Mensajes.getMensaje(Mensajes.USUARIO_ACTUALIZADO);
        else return Mensajes.getMensaje(Mensajes.ERROR_ACTUALIZAR_USUARIO);
    }

    private static boolean actualizarUsuarioBD(Usuario usuario) {
        //Para actualizar el usuario le indicamos un nuevo objeto usuario, incluyendo los datos no modificados, que permanecen iguales
        String sql = "UPDATE USUARIOS SET usuario = ?, password = ?, email = ?, direccion = ?, telefono = ?, tipo = ?, permisos = ? WHERE id = ?";

        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getTipo());
            ps.setString(7, usuario.getPermisos());
            ps.setInt(8, usuario.getId_usuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}