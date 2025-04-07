package VIEW.PERFILES;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import MODEL.Publicacion;
import MODEL.Usuario;
import VIEW.PUBLICACIONES.Publicacion_Vista;

public class Perfil_Usuario_Vista extends JPanel {
    private Usuario usuario;
    private List<Publicacion> publicaciones;

    public Perfil_Usuario_Vista(Connection conn, Usuario usuario, List<Publicacion> publicaciones) {
        this.usuario = usuario;
        this.publicaciones = publicaciones;
        initUI(conn);
    }

    private void initUI(Connection conn) {
        setLayout(new BorderLayout());
        setBackground(new Color(211, 205, 192));

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(new Color(211, 205, 192));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nombreLabel = new JLabel("Nombre: " + usuario.getUsuario());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(nombreLabel, gbc);

        if (usuario.getDireccion() != null) {
            JLabel direccionLabel = new JLabel("Dirección: " + usuario.getDireccion());
            direccionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            gbc.gridy = 1;
            infoPanel.add(direccionLabel, gbc);
        }

        JLabel telefonoLabel = new JLabel("Teléfono: " + usuario.getTelefono());
        telefonoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        infoPanel.add(telefonoLabel, gbc);

        JLabel emailLabel = new JLabel("Correo Electrónico: " + usuario.getEmail());
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 3;
        infoPanel.add(emailLabel, gbc);

        add(infoPanel, BorderLayout.NORTH);

        JPanel publicacionesPanel = new JPanel();
        publicacionesPanel.setLayout(new BoxLayout(publicacionesPanel, BoxLayout.Y_AXIS));
        publicacionesPanel.setBackground(new Color(211, 205, 192));

        for (Publicacion publicacion : publicaciones) {
            Publicacion_Vista publicacionVista = new Publicacion_Vista(publicacion);
            publicacionesPanel.add(publicacionVista);
        }

        add(new JScrollPane(publicacionesPanel), BorderLayout.CENTER);
    }
}