package CONTROLLER.CRUD.USER;

import DB.UTIL.GestorConexion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import VIEW.ERROR.Error_INICIAR_BD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static DB.UTIL.CrearConn.conn;

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
       final Logger LOGGER = Logger.getLogger(AddUsuario.class.getName());


        Connection conn = GestorConexion.getConexion();

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, Mensajes.getMensaje(Mensajes.FALLO_CONEXION));
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }


        //SQL con dirección para las empresas
        String sqlDireccion = "INSERT INTO USUARIOS (nombre, password, email, direccion, telefono, id_tipo_usuario) VALUES (?, ?, ?, ?, ?, ?)";

        //SQL sin dirección para los usuarios
        String sqlSinDireccion = "INSERT INTO USUARIOS (nombre, password, email, telefono, id_tipo_usuario) VALUES (?, ?, ?, ?, ?)";

        try  {
            PreparedStatement ps;
            if (usuario.getDireccion() != null) {
                assert conn != null;
                ps = conn.prepareStatement(sqlDireccion);
                ps.setString(1, usuario.getUsuario());
                ps.setString(2, usuario.getPassword());
                ps.setString(3, usuario.getEmail());
                ps.setString(4, usuario.getDireccion());
                ps.setString(5, usuario.getTelefono());
                ps.setString(6, usuario.getPermisos());
            } else {
                assert conn != null;
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