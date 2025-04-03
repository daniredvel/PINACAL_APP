package VIEW.PUBLICACIONES;

import CONTROLLER.CRUD.PUBLICACION.EliminarPublicacion;
import MODEL.Publicacion;
import MODEL.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Publicacion_Propia_Detalle_Vista extends Publicacion_Detalle_Vista {
    public Publicacion_Propia_Detalle_Vista(Publicacion publicacion, Usuario usuario_actual, Connection conexion) {
        super(publicacion, usuario_actual, conexion);

        //setIconImage(Rutas.getImage(Rutas.ICONO));
        setTitle(publicacion.getTitulo());

        //BOTÓN DE ELIMINAR

        JButton eliminarBoton = new JButton("Eliminar");
        eliminarBoton.setBackground(new Color(174, 101, 7));
        eliminarBoton.setForeground(Color.WHITE);
        eliminarBoton.setAlignmentX(Component.LEFT_ALIGNMENT);
        getLeftPanel().add(eliminarBoton);

        eliminarBoton.setEnabled(true);
        eliminarBoton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                if (EliminarPublicacion.eliminarPublicacion(publicacion)) {
                    JOptionPane.showMessageDialog(null, "Publicación denegada y eliminada.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la publicación.");
                }
            }
        });
    }
}