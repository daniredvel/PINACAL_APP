package CONTROLLER;

import MODEL.Publicacion;
import MODEL.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorDatos {
    private Connection connection;


    //obtenerPublicaciones genera una lista de publicaciones a partir de la base de datos
    //Consultando todas las publicaciones para mostrarlas en la vista de Inicio
    public List<Publicacion> obtenerPublicaciones() {
        List<Publicacion> publicaciones = new ArrayList<>();
        String query = "SELECT * FROM publicaciones ORDER BY fecha DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Publicacion publicacion = new Publicacion(
                        rs.getInt("id_publicacion"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_publicacion"),
                        rs.getInt("tipo"),
                        rs.getInt("id_usuario"),
                        rs.getString("usuario")
                );
                publicaciones.add(publicacion);
            }
        } catch (SQLException ignored) {
        // TODO: Control de excepciones al leer publicaciones
        }

        return publicaciones;
    }

    //obtenerPublicaciones genera una lista de publicaciones a partir de la base de datos
    //Indicandole el usuario para que solo devuelva las publicaciones de ese usuario
    public List<Publicacion> obtenerPublicaciones(Usuario usuario) {
        List<Publicacion> publicaciones = new ArrayList<>();
        String sql = "SELECT * FROM publicaciones WHERE nombre = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
             ps.setString(1, usuario.getUsuario());

             ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                Publicacion publicacion = new Publicacion(
                        rs.getInt("id_publicacion"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_publicacion"),
                        rs.getInt("tipo"),
                        rs.getInt("id_usuario"),
                        rs.getString("usuario")
                );
                publicaciones.add(publicacion);
            }
        } catch (SQLException ignored) {
        // TODO: Control de excepciones al leer publicaciones
        }

        return publicaciones;
    }



}