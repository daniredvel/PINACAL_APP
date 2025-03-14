package VIEW.PERSONAL;

import CONTROLLER.ControladorDatos;
import MODEL.Publicacion;
import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.PUBLICACIONES.Publicacion_Vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Personal_Usuario extends JFrame {
    private JButton inicioButton;
    private JButton personalButton;
    private JButton anadirButton;
    private JList<Publicacion> publicacionesList;
    private DefaultListModel<Publicacion> listModel;
    private ControladorDatos controladorDatos;
    private Usuario usuario_actual;

    public Personal_Usuario(Usuario usuario_actual) {
        this.usuario_actual = usuario_actual;
        controladorDatos = new ControladorDatos();

        setTitle("Personal Usuario");
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

        inicioButton = new JButton("Inicio");
        inicioButton.setFont(buttonFont);
        inicioButton.setBackground(new Color(174, 101, 7));
        inicioButton.setForeground(Color.WHITE);
        inicioButton.setPreferredSize(new Dimension(150, 50)); // Set button size
        inicioButton.setMargin(new Insets(10, 20, 10, 20)); // Set padding

        personalButton = new JButton("Personal");
        personalButton.setFont(buttonFont);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setPreferredSize(new Dimension(150, 50)); // Set button size
        personalButton.setMargin(new Insets(10, 20, 10, 20)); // Set padding

        anadirButton = new JButton("Añadir");
        anadirButton.setFont(buttonFont);
        anadirButton.setBackground(new Color(174, 101, 7));
        anadirButton.setForeground(Color.WHITE);
        anadirButton.setPreferredSize(new Dimension(150, 50)); // Set button size
        anadirButton.setMargin(new Insets(10, 20, 10, 20)); // Set padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set margins between buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(inicioButton, gbc);

        gbc.gridx = 1;
        topPanel.add(personalButton, gbc);

        gbc.gridx = 2;
        topPanel.add(anadirButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        // Lista de publicaciones en la parte inferior
        listModel = new DefaultListModel<>();
        publicacionesList = new JList<>(listModel);
        publicacionesList.setCellRenderer(new ListCellRenderer<Publicacion>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Publicacion> list, Publicacion value, int index, boolean isSelected, boolean cellHasFocus) {
                Publicacion_Vista publicacionVista = new Publicacion_Vista(value);
                if (isSelected) {
                    publicacionVista.setBackground(new Color(174, 101, 7));
                    publicacionVista.setForeground(Color.WHITE);
                }
                return publicacionVista;
            }
        });

        JScrollPane scrollPane = new JScrollPane(publicacionesList);
        add(scrollPane, BorderLayout.CENTER);

        // Load and display publications
        cargarPublicaciones();

        // Add action listeners to buttons
        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual).setVisible(true);
        });
        personalButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Personal button clicked"));
        anadirButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Añadir button clicked"));
    }

    private void cargarPublicaciones() {
        List<Publicacion> publicaciones = controladorDatos.obtenerPublicaciones("example");
        for (Publicacion publicacion : publicaciones) {
            listModel.addElement(publicacion);
        }
    }

    // METODO para añadir publicaciones a la lista
    public void addPublicacion(Publicacion publicacion) {
        listModel.addElement(publicacion);
    }
}