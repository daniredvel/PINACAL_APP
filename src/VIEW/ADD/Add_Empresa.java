package VIEW.ADD;

import CONTROLLER.CRUD.PUBLICACION.AddPublicacion;
import CONTROLLER.ControladorDatos;
import MODEL.Publicacion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.PERSONAL.Personal_Empresa;
import VIEW.PUBLICACIONES.Publicacion_Vista;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class Add_Empresa extends JFrame {
    private final AddPublicacion addPublicacion;
    private final JPanel publicacionesPanel;
    private final CardLayout cardLayout;
    private final List<Publicacion> publicaciones;
    private int currentPage = 0;
    private final int publicationsPerPage = 5;

    public Add_Empresa(Usuario usuario_actual, Connection conn) {
        ControladorDatos controladorDatos = new ControladorDatos();
        addPublicacion = new AddPublicacion();

        setTitle("Publicaciones del Usuario");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(new Color(211, 205, 192));

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        JButton inicioButton = new JButton("Inicio");
        inicioButton.setFont(buttonFont);
        inicioButton.setBackground(new Color(174, 101, 7));
        inicioButton.setForeground(Color.WHITE);
        inicioButton.setPreferredSize(new Dimension(150, 50));
        inicioButton.setMargin(new Insets(10, 20, 10, 20));

        JButton personalButton = new JButton("Personal");
        personalButton.setFont(buttonFont);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setPreferredSize(new Dimension(150, 50));
        personalButton.setMargin(new Insets(10, 20, 10, 20));

        JButton anadirButton = new JButton("Añadir");
        anadirButton.setFont(buttonFont);
        anadirButton.setBackground(new Color(174, 101, 7));
        anadirButton.setForeground(Color.WHITE);
        anadirButton.setPreferredSize(new Dimension(150, 50));
        anadirButton.setMargin(new Insets(10, 20, 10, 20));

        JButton nuevaPublicacionButton = new JButton("Nueva Publicación");
        nuevaPublicacionButton.setFont(buttonFont);
        nuevaPublicacionButton.setBackground(new Color(174, 101, 7));
        nuevaPublicacionButton.setForeground(Color.WHITE);
        nuevaPublicacionButton.setPreferredSize(new Dimension(200, 50));
        nuevaPublicacionButton.setMargin(new Insets(10, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(inicioButton, gbc);

        gbc.gridx = 1;
        topPanel.add(personalButton, gbc);

        gbc.gridx = 2;
        topPanel.add(anadirButton, gbc);

        gbc.gridx = 3;
        topPanel.add(nuevaPublicacionButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        publicacionesPanel = new JPanel();
        cardLayout = new CardLayout();
        publicacionesPanel.setLayout(cardLayout);
        publicacionesPanel.setBackground(new Color(211, 205, 192));

        publicaciones = controladorDatos.obtenerPublicaciones(usuario_actual.getUsuario());
        updatePublicationsPanel();

        add(new JScrollPane(publicacionesPanel), BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(211, 205, 192));

        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                cardLayout.show(publicacionesPanel, String.valueOf(currentPage));
            }
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if ((currentPage + 1) * publicationsPerPage < publicaciones.size()) {
                currentPage++;
                cardLayout.show(publicacionesPanel, String.valueOf(currentPage));
            }
        });

        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);

        add(navigationPanel, BorderLayout.SOUTH);

        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        });
        personalButton.addActionListener(e -> {
            dispose();
            new Personal_Empresa(usuario_actual, conn).setVisible(true);
        });
        anadirButton.addActionListener(e -> {
            dispose();
            new Add_Empresa(usuario_actual, conn).setVisible(true);
        });

        nuevaPublicacionButton.addActionListener(e -> {
            String titulo = JOptionPane.showInputDialog("Ingrese el título de la publicación:");
            String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la publicación:");
            if (titulo != null && descripcion != null) {
                Publicacion nuevaPublicacion = new Publicacion(titulo, descripcion, 0, usuario_actual.getId_usuario(), usuario_actual.getUsuario());
                if (addPublicacion.crearPublicacion(nuevaPublicacion)) {
                    JOptionPane.showMessageDialog(null, Mensajes.getMensaje(Mensajes.PUBLICACION_ANADIDO));
                    publicaciones.add(nuevaPublicacion);
                    updatePublicationsPanel();
                } else {
                    JOptionPane.showMessageDialog(null, Mensajes.getMensaje(Mensajes.ERROR_GUARDAR_PUBLICACION));
                }
            }
        });
    }

    private void updatePublicationsPanel() {
        publicacionesPanel.removeAll();
        int totalPages = (int) Math.ceil((double) publicaciones.size() / publicationsPerPage);
        for (int i = 0; i < totalPages; i++) {
            JPanel pagePanel = new JPanel();
            pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));
            pagePanel.setBackground(new Color(211, 205, 192));
            int start = i * publicationsPerPage;
            int end = Math.min(start + publicationsPerPage, publicaciones.size());
            for (int j = start; j < end; j++) {
                Publicacion_Vista publicacionVista = new Publicacion_Vista(publicaciones.get(j));
                pagePanel.add(publicacionVista);
            }
            publicacionesPanel.add(pagePanel, String.valueOf(i));
        }
        cardLayout.show(publicacionesPanel, String.valueOf(currentPage));
        publicacionesPanel.revalidate();
        publicacionesPanel.repaint();
    }
}