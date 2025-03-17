package VIEW.ADMINISTRAR;

import CONTROLLER.CRUD.Eliminado_Controlador;
import CONTROLLER.ControladorDatos;
import MODEL.Publicacion;
import MODEL.Usuario;
import VIEW.ADD.Add_Empresa;
import VIEW.INICIO.Inicio_Vista;
import VIEW.PERSONAL.Personal_Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Administar_Vista extends JFrame {
    private Usuario usuario_actual;
    private List<Publicacion> publicaciones;
    private int currentIndex = 0;
    private JTextArea publicacionArea;
    private JTextField justificacionField;
    private JRadioButton aceptadaButton;
    private JRadioButton denegadaButton;
    private JButton siguienteButton;
    private ControladorDatos controladorDatos;
    private ButtonGroup group; // Declare group as a class member

    public Administar_Vista(Usuario usuario_actual) {
        this.usuario_actual = usuario_actual;
        controladorDatos = new ControladorDatos();
        publicaciones = controladorDatos.obtenerPublicaciones("example");

        setTitle("Administrar Publicaciones");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192));

        // Panel superior con botones
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

        JButton adminButton = new JButton("Administrador");
        adminButton.setFont(buttonFont);
        adminButton.setBackground(new Color(174, 101, 7));
        adminButton.setForeground(Color.WHITE);
        adminButton.setPreferredSize(new Dimension(150, 50));
        adminButton.setMargin(new Insets(10, 20, 10, 20));

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
        topPanel.add(adminButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        // Panel central con la publicación y los controles
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(211, 205, 192));

        publicacionArea = new JTextArea(10, 50);
        publicacionArea.setFont(new Font("Arial", Font.PLAIN, 18));
        publicacionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(publicacionArea);

        justificacionField = new JTextField(50);
        justificacionField.setFont(new Font("Arial", Font.PLAIN, 18));
        justificacionField.setEnabled(false);

        aceptadaButton = new JRadioButton("Aceptada");
        aceptadaButton.setFont(new Font("Arial", Font.PLAIN, 18));
        aceptadaButton.setBackground(new Color(211, 205, 192));

        denegadaButton = new JRadioButton("Denegada");
        denegadaButton.setFont(new Font("Arial", Font.PLAIN, 18));
        denegadaButton.setBackground(new Color(211, 205, 192));

        group = new ButtonGroup(); // Initialize group
        group.add(aceptadaButton);
        group.add(denegadaButton);

        aceptadaButton.addActionListener(e -> justificacionField.setEnabled(false));
        denegadaButton.addActionListener(e -> justificacionField.setEnabled(true));

        siguienteButton = new JButton("Siguiente");
        siguienteButton.setFont(new Font("Arial", Font.PLAIN, 18));
        siguienteButton.setBackground(new Color(174, 101, 7));
        siguienteButton.setForeground(Color.WHITE);

        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarPublicacion();
                currentIndex++;
                if (currentIndex < publicaciones.size()) {
                    mostrarPublicacion();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay más publicaciones.");
                    dispose();
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(scrollPane, gbc);

        gbc.gridy = 1;
        centerPanel.add(justificacionField, gbc);

        gbc.gridy = 2;
        centerPanel.add(aceptadaButton, gbc);

        gbc.gridy = 3;
        centerPanel.add(denegadaButton, gbc);

        gbc.gridy = 4;
        centerPanel.add(siguienteButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        if (!publicaciones.isEmpty()) {
            mostrarPublicacion();
        } else {
            JOptionPane.showMessageDialog(null, "No hay publicaciones para revisar.");
            dispose();
        }

        // Add action listeners to buttons
        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual).setVisible(true);
        });
        personalButton.addActionListener(e -> {
            dispose();
            new Personal_Usuario(usuario_actual).setVisible(true);
        });
        anadirButton.addActionListener(e -> {
            dispose();
            new Add_Empresa(usuario_actual).setVisible(true);
        });
        adminButton.addActionListener(e -> {
            dispose();
            new Administar_Vista(usuario_actual).setVisible(true);
        });
    }

    private void mostrarPublicacion() {
        Publicacion publicacion = publicaciones.get(currentIndex);
        publicacionArea.setText(publicacion.getDescripcion());
        justificacionField.setText("");
        group.clearSelection(); // Use group here
    }

    private void gestionarPublicacion() {
        Publicacion publicacion = publicaciones.get(currentIndex);
        if (denegadaButton.isSelected()) {
            String justificacion = justificacionField.getText();
            JOptionPane.showMessageDialog(null, Eliminado_Controlador.eliminar(justificacion, "PUBLICACION", publicacion, usuario_actual));
        }
    }
}