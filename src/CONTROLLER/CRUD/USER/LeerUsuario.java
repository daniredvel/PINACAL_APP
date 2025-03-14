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
        // Buscamos el usuario por su nombre de usuario
        String sql = "SELECT * FROM USUARIOS WHERE usuario = ?";
        try (Connection conn = GestorConexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    //Consultamos la dirección
                    String direccion = rs.getString("direccion");

                    //Si la direccion no es nula usamos el constructor de la empresa, con direccion
                    if (direccion != null) {
                        return new Usuario(
                                Integer.parseInt(rs.getString("id_usuario")),
                                rs.getString("usuario"),
                                rs.getString("password"),
                                rs.getString("email"),
                                direccion,
                                rs.getString("telefono"),
                                Integer.parseInt(rs.getString("tipo")),
                                rs.getString("permisos")
                        );
                    } else {
                        //Si la direccion es nula usamos el constructor del usuario, sin direccion
                        return new Usuario(
                                Integer.parseInt(rs.getString("id_usuario")),
                                rs.getString("usuario"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("telefono"),
                                Integer.parseInt(rs.getString("tipo")),
                                rs.getString("permisos")
                        );
                    }
                } else {
                    mensaje = Mensajes.getMensaje(Mensajes.USUARIO_NO_EXISTE);
                }
            }
        } catch (SQLException e) {
            mensaje = Mensajes.getMensaje(Mensajes.FALLO_CONEXION);
        }
        return null;
    }
}