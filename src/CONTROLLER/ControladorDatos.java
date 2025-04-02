package CONTROLLER;

import MODEL.Publicacion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import VIEW.ERROR.Error_INICIAR_BD;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static DB.UTIL.CrearConn.conn;

public class ControladorDatos {
    private static final Logger LOGGER = Logger.getLogger(ControladorDatos.class.getName());

    // Obtener todas las publicaciones
    /**
     * @param empresaAsociada Indica si el usuario es una empresa asociada o Administrador
     *  True -> Obtiene todas las publicaciones. False -> Solo obtiene las publicaciones de las empresas asocidas
     * */

    // Metodo para obtener todas las publicaciones
    public static List<Publicacion> obtenerPublicaciones(Connection conexion, boolean empresaAsociada) {
        Connection conn = conexion;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, Mensajes.getMensaje(Mensajes.FALLO_CONEXION));
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }

        List<Publicacion> publicaciones = new ArrayList<>();
        String sql = getSql(empresaAsociada);

        try {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql);
            try (stmt; ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Publicacion publicacion = new Publicacion(
                            rs.getInt("id_publicacion"),
                            rs.getString("titulo"),
                            rs.getString("descripcion"),
                            rs.getTimestamp("fecha"),
                            rs.getString("tipo"),
                            rs.getInt("id_usuario"),
                            rs.getString("usuario")
                    );
                    publicaciones.add(publicacion);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, Mensajes.getMensaje(Mensajes.ERROR_CARGAR_PUBLICACIONES) + "{0}, {1}", new Object[]{e.getMessage(), e});
            // Reabrir la conexión si se cierra debido a un error
        }

        return publicaciones;
    }

    //SQL en funcion de si es una empresa asociada o no
    private static String getSql(boolean empresaAsociada) {
        String sql;
        if (empresaAsociada) {
            sql = "SELECT p.*, u.nombre AS usuario FROM PUBLICACIONES p " +
                    "JOIN USUARIOS u ON p.id_usuario = u.id_usuario " +
                    "ORDER BY p.fecha DESC";
        } else {
            sql = "SELECT p.*, u.nombre AS usuario FROM PUBLICACIONES p " +
                    "JOIN USUARIOS u ON p.id_usuario = u.id_usuario " +
                    "WHERE u.id_tipo_usuario IN (0, 2) " + // 0: Administrador, 2: Empresa asociada
                    "ORDER BY p.fecha DESC";
        }
        return sql;
    }

    // Obtener publicaciones por usuario
    public static List<Publicacion> obtenerPublicaciones(Connection conexion, Usuario usuario) {
        Connection conn = conexion;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, Mensajes.getMensaje(Mensajes.FALLO_CONEXION));
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }

        List<Publicacion> publicaciones = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre AS usuario FROM publicaciones p JOIN usuarios u ON p.id_usuario = u.id_usuario WHERE p.id_usuario = ? ORDER BY p.fecha DESC";

        try {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, usuario.getId_usuario());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Publicacion publicacion = new Publicacion(
                                rs.getInt("id_publicacion"),
                                rs.getString("titulo"),
                                rs.getString("descripcion"),
                                rs.getTimestamp("fecha"),
                                rs.getString("tipo"),
                                rs.getInt("id_usuario"),
                                rs.getString("usuario")
                        );
                        publicaciones.add(publicacion);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, Mensajes.getMensaje(Mensajes.ERROR_CARGAR_PUBLICACIONES) + "{0}, {1}", new Object[]{e.getMessage(), e});
            // Reabrir la conexión si se cierra debido a un error
        }

        return publicaciones;
    }

    // METODO para obtener todos los usuarios de la base de datos
    public static List<Usuario> obtenerUsuarios(Connection conexion) {
        Connection conn = conexion;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, Mensajes.getMensaje(Mensajes.FALLO_CONEXION));
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }

        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, t.nombre_tipo FROM USUARIOS u JOIN TIPOS_USUARIOS t ON u.id_tipo_usuario = t.id_tipo_usuario;";
        try {
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            try (ps; ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Consultamos la dirección
                    String direccion = rs.getString("direccion");

                    // Si la direccion no es nula usamos el constructor de la empresa, con direccion
                    Usuario usuario;
                    if (direccion != null) {
                        usuario = new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("nombre"),
                                rs.getString("password"),
                                rs.getString("email"),
                                direccion,
                                rs.getString("telefono"),
                                rs.getInt("id_tipo_usuario"),
                                rs.getString("nombre_tipo")
                        );

                    } else {
                        // Si la direccion es nula usamos el constructor del usuario, sin direccion
                        usuario = new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("nombre"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("telefono"),
                                rs.getInt("id_tipo_usuario"),
                                rs.getString("nombre_tipo")
                        );
                    }
                    usuarios.add(usuario);
                }
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, Mensajes.getMensaje(Mensajes.ERROR_CARGAR_USUARIOS) + " {0}", e.getMessage());
        }

        return usuarios;
    }
}