package CONTROLLER;

import MODEL.Publicacion;
import MODEL.Usuario;
import VIEW.ERROR.Error_INICIAR_BD;
import VIEW.INICIO.Inicio_Vista;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static DB.UTIL.CrearConn.conn;
import static DB.UTIL.CrearConn.crearConexion;

public class ControladorDatos {
    private static final Logger LOGGER = Logger.getLogger(Inicio_Vista.class.getName());

    // Obtener todas las publicaciones
    public static List<Publicacion> obtenerPublicaciones(Connection conexion) {
        Connection conn = conexion;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, "Conexión nula");
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }

        List<Publicacion> publicaciones = new ArrayList<>();
        String query = "SELECT p.*, u.nombre AS usuario FROM publicaciones p JOIN usuarios u ON p.id_usuario = u.id_usuario ORDER BY p.fecha DESC";

        try {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery(query)) {

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
            LOGGER.log(Level.INFO, "Error al cargar las publicaciones: {0}, {1}", new Object[]{e.getMessage(), e});
            // Reabrir la conexión si se cierra debido a un error
            conn = conn();
        }

        return publicaciones;
    }

    // Obtener publicaciones por usuario
    public static List<Publicacion> obtenerPublicaciones(Connection conexion, Usuario usuario) {
        Connection conn = conexion;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, "Conexión nula");
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
            LOGGER.log(Level.INFO, "Error al cargar las publicaciones: {0}, {1}", new Object[]{e.getMessage(), e});
            // Reabrir la conexión si se cierra debido a un error
            conn = conn();
        }

        return publicaciones;
    }

    // Método para obtener todos los usuarios de la base de datos
    public static List<Usuario> obtenerUsuarios(Connection conexion) {
        Connection conn = conexion;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, "Conexión nula");
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }

        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT id_usuario, nombre, email, direccion, telefono, tipo, permisos FROM usuarios";

        try {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getInt("tipo"),
                            rs.getString("permisos")
                    );
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar los usuarios: {0}", e.getMessage());
        }

        return usuarios;
    }
}