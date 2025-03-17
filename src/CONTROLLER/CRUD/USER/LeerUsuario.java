package CONTROLLER.CRUD.USER;

import DB.UTIL.GestorConexion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeerUsuario {
    static String mensaje;

    public static Usuario leerUsuarioPorNombre(String username) {
        // Consulta SQL para unir las tablas USUARIOS y TIPOS_USUARIOS
        String sql = "SELECT u.*, t.permisos FROM USUARIOS u " +
                "JOIN TIPOS_USUARIOS t ON u.id_tipo_usuario = t.id_tipo_usuario " +
                "WHERE u.nombre = ?";
        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Consultamos la dirección
                    String direccion = rs.getString("direccion");

                    // Si la direccion no es nula usamos el constructor de la empresa, con direccion
                    if (direccion != null) {
                        Usuario usuario = new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("nombre"),
                                rs.getString("password"),
                                rs.getString("email"),
                                direccion,
                                rs.getString("telefono"),
                                rs.getInt("id_tipo_usuario"),
                                rs.getString("permisos")
                        );
                        System.out.println("Usuario encontrado: " + usuario.getUsuario()); // Debug statement
                        return usuario;
                    } else {
                        // Si la direccion es nula usamos el constructor del usuario, sin direccion
                        Usuario usuario = new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("nombre"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("telefono"),
                                rs.getInt("id_tipo_usuario"),
                                rs.getString("permisos")
                        );
                        System.out.println("Usuario encontrado: " + usuario.getUsuario()); // Debug statement
                        return usuario;
                    }
                } else {
                    mensaje = Mensajes.getMensaje(Mensajes.USUARIO_NO_EXISTE);
                    System.out.println("Usuario no encontrado: " + username); // Debug statement
                }
            }
        } catch (SQLException e) {
            mensaje = Mensajes.getMensaje(Mensajes.FALLO_CONEXION);
            e.printStackTrace();
        }
        return null;
    }
}