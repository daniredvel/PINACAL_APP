package CONTROLLER.CRUD.USER;

import DB.UTIL.GestorConexion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class AddUsuario {
    //Indicamos los tipos y permisos de usuario

    public static String addEmpresa(Usuario empresa) {

        empresa.setTipo(Usuario.getTipos(Usuario.EMPRESA_NO_ASOCIADA));
        empresa.setPermisos("1");

        return responder(insertUsuario(empresa));
    }

    public static String addUsuario(Usuario usuario) {

        usuario.setTipo(Usuario.getTipos(Usuario.USUARIO));
        usuario.setPermisos("1");

        return responder(insertUsuario(usuario));
    }

    private static String responder(boolean usuario) {
        if(usuario) return Mensajes.getMensaje(Mensajes.USUARIO_ANADIDO);
        else return Mensajes.getMensaje(Mensajes.ERROR_ANADIR_USUARIO);
    }

    private static boolean insertUsuario(Usuario usuario) {
        //SQL con dirección para las empresas
        String sqlDireccion = "INSERT INTO USUARIOS (nombre, password, email, direccion, telefono, id_tipo_usuario) VALUES (?, ?, ?, ?, ?, ?)";

        //SQL sin dirección para los usuarios
        String sqlSinDireccion = "INSERT INTO USUARIOS (nombre, password, email, telefono, id_tipo_usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = GestorConexion.getConexion()) {
            PreparedStatement ps;
            if (usuario.getDireccion() != null) {
                ps = conn.prepareStatement(sqlDireccion);
                ps.setString(1, usuario.getUsuario());
                ps.setString(2, usuario.getPassword());
                ps.setString(3, usuario.getEmail());
                ps.setString(4, usuario.getDireccion());
                ps.setString(5, usuario.getTelefono());
                ps.setString(6, usuario.getPermisos());
            } else {
                ps = conn.prepareStatement(sqlSinDireccion);
                ps.setString(1, usuario.getUsuario());
                ps.setString(2, usuario.getPassword());
                ps.setString(3, usuario.getEmail());
                ps.setString(4, usuario.getTelefono());
                ps.setString(5, usuario.getPermisos());
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}