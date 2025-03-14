package CONTROLLER;

import MODEL.Publicacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorDatos {
    private Connection connection;


    public List<Publicacion> obtenerPublicaciones() {
        List<Publicacion> publicaciones = new ArrayList<>();
        String query = "SELECT * FROM publicaciones";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Publicacion publicacion = new Publicacion(
                        rs.getInt("id_publicacion"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("tipo"),
                        rs.getInt("id_usuario"),
                        rs.getString("usuario")
                );
                publicaciones.add(publicacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publicaciones;
    }

    public List<Publicacion> obtenerPublicaciones(String op) {
        List<Publicacion> publicaciones = new ArrayList<>();

        // Generate example publications based on the provided option
        if ("example".equals(op)) {
            publicaciones.add(new Publicacion(1, "Example Title 1", "Example Description 1", new Date(), 0, 1, "User1"));
            publicaciones.add(new Publicacion(2, "Example Title 2", "Example Description 2", new Date(), 1, 2, "User2"));
            publicaciones.add(new Publicacion(3, "Example Title 3", "Example Description 3", new Date(), 0, 3, "User3"));
        }

        return publicaciones;
    }

}