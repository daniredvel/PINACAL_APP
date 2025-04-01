package VIEW.INICIO;

import CONTROLLER.ControladorDatos;
import MODEL.Publicacion;
import MODEL.Usuario;
import VIEW.ADD.Add_Empresa;
import VIEW.ADMINISTRAR.Administar_Vista;
import VIEW.ERROR.Error_INICIAR_BD;
import VIEW.PERSONAL.Personal_Empresa;
import VIEW.PERSONAL.Personal_Usuario;
import VIEW.PUBLICACIONES.Publicacion_Vista;
import VIEW.REGISTRO.Registro_Empresa;
import VIEW.RES.Rutas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static DB.UTIL.CrearConn.conn;

public class Inicio_Vista extends JFrame {
    public static final Logger LOGGER = Logger.getLogger(Inicio_Vista.class.getName());
    private JButton adminButton;
    protected final DefaultListModel<Publicacion> listModel;
    private static Usuario usuario_actual = null;
    public static Connection conn = null;

    public Inicio_Vista(Usuario usuario_actual, Connection conexion) {
        LOGGER.log(Level.INFO, "Iniciando vista de inicio");
        Inicio_Vista.usuario_actual = usuario_actual;

        // Si la conexión es nula, se crea una nueva
        if (conn == null) conn = conn();
        else Inicio_Vista.conn = conexion;

        // Nos aseguramos de que la conexión no sea nula
        // Si la conexión es nula, se muestra la ventana de error de la aplicación
        if (conn == null) {
            LOGGER.log(Level.SEVERE, "Conexión nula");
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }

        // Icono
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

        JButton anadirButton = new JButton("Mis Publicaciones");
            anadirButton.setFont(buttonFont);
            anadirButton.setBackground(new Color(174, 101, 7));
            anadirButton.setForeground(Color.WHITE);
            anadirButton.setPreferredSize(new Dimension(150, 50)); // Set button size
            anadirButton.setMargin(new Insets(10, 20, 10, 20)); // Set padding

            gbc.gridx = 2;

            //Los usuarios particulares no pueden añadir publicaciones
            if (!usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.USUARIO)))  topPanel.add(anadirButton, gbc);


        if (usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.ADMINISTRADOR))) {
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

        // Cargar publicaciones
        cargarPublicaciones();

        // «Escuchadores» de los botones
        inicioButton.addActionListener(e -> {
            LOGGER.log(Level.INFO, "Inicio button clicked");
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        });
        personalButton.addActionListener(e -> {
            LOGGER.log(Level.INFO, "Personal button clicked");
            dispose();
            System.out.println(usuario_actual.getTipo());
            if (usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.EMPRESA_ASOCIADA)) || usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.EMPRESA_NO_ASOCIADA))) {
                new Personal_Empresa(usuario_actual, conn).setVisible(true);
            } else if (usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.USUARIO)) || usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.ADMINISTRADOR))) {
                new Personal_Usuario(usuario_actual, conn).setVisible(true);
            } else {
                new Inicio_Vista(usuario_actual, conn).setVisible(true);
            }
        });
        if (usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.EMPRESA_ASOCIADA)) || usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.ADMINISTRADOR))) {
            anadirButton.addActionListener(e -> {
                LOGGER.log(Level.INFO, "Mis Publicaciones button clicked");
                dispose();
                new Add_Empresa(usuario_actual, conn).setVisible(true);
            });
        }
        if (usuario_actual.getTipo().equalsIgnoreCase(Usuario.getTipos(Usuario.ADMINISTRADOR))) {
            adminButton.addActionListener(e -> {
                LOGGER.log(Level.INFO, "Administrador button clicked");
                dispose();
                new Administar_Vista(usuario_actual, conn).setVisible(true);
            });
        }
    }

    protected JScrollPane getJScrollPane() {
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

    protected void cargarPublicaciones() {
        LOGGER.log(Level.INFO, "Cargando publicaciones");
        listModel.clear(); // Limpiar la lista antes de recargar

        List<Publicacion> publicaciones = ControladorDatos.obtenerPublicaciones(conn, false);

        for (Publicacion publicacion : publicaciones) {
            listModel.addElement(publicacion);
        }
        LOGGER.log(Level.INFO, "Publicaciones cargadas: {0}", listModel.size());
    }

}