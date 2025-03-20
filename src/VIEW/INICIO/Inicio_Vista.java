package VIEW.INICIO;

import CONTROLLER.ControladorDatos;
import MODEL.Publicacion;
import MODEL.Usuario;
import VIEW.ADD.Add_Empresa;
import VIEW.ADMINISTRAR.Administar_Vista;
import VIEW.PERSONAL.Personal_Empresa;
import VIEW.PERSONAL.Personal_Usuario;
import VIEW.PUBLICACIONES.Publicacion_Vista;
import VIEW.RES.Rutas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class Inicio_Vista extends JFrame {
    private JButton anadirButton;
    private JButton adminButton;
    private final DefaultListModel<Publicacion> listModel;
    private final ControladorDatos controladorDatos;
    private static Usuario usuario_actual = null;

    public Inicio_Vista(Usuario usuario_actual, Connection conn) {
        controladorDatos = new ControladorDatos();
        Inicio_Vista.usuario_actual = usuario_actual;

        //Icono
        setIconImage(Rutas.getIcono());

        setTitle("Inicio Vista");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Adjust the window to the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192)); // Background color similar to Registro and InicioSesion

        // Panel superior con botones
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center buttons
        topPanel.setBackground(new Color(211, 205, 192));

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        JButton inicioButton = new JButton("Inicio");
        inicioButton.setFont(buttonFont);
        inicioButton.setBackground(new Color(174, 101, 7));
        inicioButton.setForeground(Color.WHITE);
        inicioButton.setPreferredSize(new Dimension(150, 50)); // Set button size
        inicioButton.setMargin(new Insets(10, 20, 10, 20)); // Set padding

        JButton personalButton = new JButton("Personal");
        personalButton.setFont(buttonFont);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setPreferredSize(new Dimension(150, 50)); // Set button size
        personalButton.setMargin(new Insets(10, 20, 10, 20)); // Set padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set margins between buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(inicioButton, gbc);

        gbc.gridx = 1;
        topPanel.add(personalButton, gbc);

        if (usuario_actual.getTipo().equals("EMPRESA_ASOCIADA")||usuario_actual.getTipo().equals("EMPRESA_NO_ASOCIADA")) {
            anadirButton = new JButton("Mis Publicaciones");
            anadirButton.setFont(buttonFont);
            anadirButton.setBackground(new Color(174, 101, 7));
            anadirButton.setForeground(Color.WHITE);
            anadirButton.setPreferredSize(new Dimension(150, 50)); // Set button size
            anadirButton.setMargin(new Insets(10, 20, 10, 20)); // Set padding

            gbc.gridx = 2;
            topPanel.add(anadirButton, gbc);
        }

        if (usuario_actual.getTipo().equals(Usuario.getTipos(Usuario.ADMINISTRADOR))) {
            adminButton = new JButton("Administrador");
            adminButton.setFont(buttonFont);
            adminButton.setBackground(new Color(174, 101, 7));
            adminButton.setForeground(Color.WHITE);
            adminButton.setPreferredSize(new Dimension(150, 50)); // Set button size
            adminButton.setMargin(new Insets(10, 20, 10, 20)); // Set padding

            gbc.gridx = 3;
            topPanel.add(adminButton, gbc);
        }

        add(topPanel, BorderLayout.NORTH);

        // Lista de publicaciones en la parte inferior
        listModel = new DefaultListModel<>();
        JScrollPane scrollPane = getJScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        // Load and display publications
        cargarPublicaciones();

        // Add action listeners to buttons
        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        });
        personalButton.addActionListener(e -> {
            dispose();
            if (usuario_actual.getTipo().equals(Usuario.getTipos(Usuario.EMPRESA_ASOCIADA))||usuario_actual.getTipo().equals(Usuario.getTipos(Usuario.EMPRESA_NO_ASOCIADA))) {
                new Personal_Empresa(usuario_actual,conn).setVisible(true);
            } else if (usuario_actual.getTipo().equals(Usuario.getTipos(Usuario.USUARIO))||usuario_actual.getTipo().equals(Usuario.getTipos(Usuario.ADMINISTRADOR))) {
                new Personal_Usuario(usuario_actual,conn).setVisible(true);
            } else {
                new Inicio_Vista(usuario_actual, conn).setVisible(true);}
        });
        if (usuario_actual.getTipo().equals(Usuario.getTipos(Usuario.EMPRESA_ASOCIADA))||usuario_actual.getTipo().equals(Usuario.getTipos(Usuario.EMPRESA_NO_ASOCIADA))) {
            anadirButton.addActionListener(e -> {
                dispose();
                new Add_Empresa(usuario_actual, conn).setVisible(true);
            });
        }
        if (usuario_actual.getTipo().equals(Usuario.getTipos(Usuario.ADMINISTRADOR))) {
            adminButton.addActionListener(e -> {
                dispose();
                new Administar_Vista(usuario_actual, conn).setVisible(true);
            });
        }
    }

    private JScrollPane getJScrollPane() {
        JList<Publicacion> publicacionesList = new JList<>(listModel);
        publicacionesList.setCellRenderer((list, publicacion, index, isSelected, cellHasFocus) -> {
            Publicacion_Vista publicacionVista = new Publicacion_Vista(publicacion, usuario_actual);
            if (isSelected) {
                publicacionVista.setBackground(new Color(174, 101, 7));
                publicacionVista.setForeground(Color.WHITE);
            }
            return publicacionVista;
        });

        return new JScrollPane(publicacionesList);
    }

    private void cargarPublicaciones() {
        List<Publicacion> publicaciones = controladorDatos.obtenerPublicaciones();
        for (Publicacion publicacion : publicaciones) {
            listModel.addElement(publicacion);
        }
    }

}