package VIEW.ADD;

import MODEL.Publicacion;
import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.RES.Rutas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static CONTROLLER.CRUD.PUBLICACION.AddPublicacion.crearPublicacion;

public class Add_Publicacion extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(Add_Publicacion.class.getName());
    private JTextField tituloField;
    private JTextArea descripcionArea;
    private JComboBox<String> tipoComboBox;
    private Usuario usuario_actual;
    private Connection conn;

    public Add_Publicacion(Usuario usuario_actual, Connection conn) {
        this.usuario_actual = usuario_actual;
        this.conn = conn;

        // Icono
        setIconImage(Rutas.getIcono());

        setTitle("Añadir Publicación");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192));

        // Panel central con los campos de entrada
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(211, 205, 192));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(tituloLabel, gbc);

        gbc.gridx = 1;
        tituloField = new JTextField(20);
        tituloField.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(tituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(descripcionLabel, gbc);

        gbc.gridx = 1;
        descripcionArea = new JTextArea(5, 20);
        descripcionArea.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(descripcionArea);
        centerPanel.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel tipoLabel = new JLabel("Tipo:");
        tipoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(tipoLabel, gbc);

        gbc.gridx = 1;
        tipoComboBox = new JComboBox<>(new String[]{"OFERTA", "DEMANDA"});
        tipoComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(tipoComboBox, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con el botón de aceptar
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(211, 205, 192));
        JButton aceptarButton = new JButton("Aceptar");
        aceptarButton.setFont(new Font("Arial", Font.PLAIN, 18));
        aceptarButton.setBackground(new Color(174, 101, 7));
        aceptarButton.setForeground(Color.WHITE);
        aceptarButton.addActionListener(e -> crearPublicacion_Vista());
        bottomPanel.add(aceptarButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void crearPublicacion_Vista() {
        String titulo = tituloField.getText();
        String descripcion = descripcionArea.getText();
        String tipo = (String) tipoComboBox.getSelectedItem();
        Date fecha_publicacion = new Date();

        // Validación de los campos
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Publicacion publicacion = new Publicacion(0, titulo, descripcion, new java.sql.Timestamp(fecha_publicacion.getTime()), tipo, usuario_actual.getId_usuario(), usuario_actual.getUsuario());

        try {

            crearPublicacion(publicacion);

            LOGGER.log(Level.INFO, "Publicación creada: {0}", publicacion);
            JOptionPane.showMessageDialog(this, "Publicación creada con éxito.");
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al crear la publicación: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al crear la publicación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}