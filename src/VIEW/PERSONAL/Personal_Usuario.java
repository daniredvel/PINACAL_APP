package VIEW.PERSONAL;

import CONTROLLER.CRUD.USER.ActualizarUsuario;
import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.RES.Rutas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Personal_Usuario extends JFrame {
    private final JTextField nombreField;
    private final JTextField direccionField;
    private final JTextField telefonoField;

    public Personal_Usuario(Usuario usuario_actual, Connection conn) {

        setTitle("Personal Usuario");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the window to full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Icono
        setIconImage(Rutas.getIcono());

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridBagLayout());
        panelSuperior.setBackground(new Color(211, 205, 192));

        Font fuenteButton = new Font("Arial", Font.PLAIN, 14);

        JButton inicioButton = new JButton("Inicio");
        inicioButton.setFont(fuenteButton);
        inicioButton.setBackground(new Color(174, 101, 7));
        inicioButton.setForeground(Color.WHITE);
        inicioButton.setPreferredSize(new Dimension(120, 40));
        inicioButton.setMargin(new Insets(5, 10, 5, 10));

        JButton personalButton = new JButton("Personal");
        personalButton.setFont(fuenteButton);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setPreferredSize(new Dimension(120, 40));
        personalButton.setMargin(new Insets(5, 10, 5, 10));

        JButton modificarButton = new JButton("Modificar");
        modificarButton.setFont(fuenteButton);
        modificarButton.setBackground(new Color(174, 101, 7));
        modificarButton.setForeground(Color.WHITE);
        modificarButton.setPreferredSize(new Dimension(120, 40));
        modificarButton.setMargin(new Insets(5, 10, 5, 10));

        JButton aceptarButton = new JButton("Aceptar");
        aceptarButton.setFont(fuenteButton);
        aceptarButton.setBackground(new Color(174, 101, 7));
        aceptarButton.setForeground(Color.WHITE);
        aceptarButton.setPreferredSize(new Dimension(120, 40));
        aceptarButton.setMargin(new Insets(5, 10, 5, 10));
        aceptarButton.setEnabled(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(inicioButton, gbc);

        gbc.gridx = 1;
        panelSuperior.add(personalButton, gbc);

        gbc.gridx = 2;
        panelSuperior.add(modificarButton, gbc);

        gbc.gridx = 3;
        panelSuperior.add(aceptarButton, gbc);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBackground(new Color(211, 205, 192));
        userPanel.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        Font fuenteLabel = new Font("Arial", Font.PLAIN, 14);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(fuenteLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        userPanel.add(nombreLabel, gbc);

        nombreField = new JTextField(usuario_actual.getUsuario(), 20);
        nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
        nombreField.setEnabled(false);
        gbc.gridx = 1;
        userPanel.add(nombreField, gbc);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setFont(fuenteLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        userPanel.add(direccionLabel, gbc);

        direccionField = new JTextField(usuario_actual.getDireccion(), 20);
        direccionField.setFont(new Font("Arial", Font.PLAIN, 14));
        direccionField.setEnabled(false);
        gbc.gridx = 1;
        userPanel.add(direccionField, gbc);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setFont(fuenteLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        userPanel.add(telefonoLabel, gbc);

        telefonoField = new JTextField(usuario_actual.getTelefono(), 20);
        telefonoField.setFont(new Font("Arial", Font.PLAIN, 14));
        telefonoField.setEnabled(false);
        gbc.gridx = 1;
        userPanel.add(telefonoField, gbc);

        add(userPanel, BorderLayout.CENTER);

        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        });
        personalButton.addActionListener(e -> {
            dispose();
            new Personal_Usuario(usuario_actual, conn).setVisible(true);
        });
        modificarButton.setEnabled(true);
        modificarButton.addActionListener(e -> {
            nombreField.setEnabled(true);
            direccionField.setEnabled(true);
            telefonoField.setEnabled(true);
            aceptarButton.setEnabled(true);
            modificarButton.setEnabled(false);

        });

        aceptarButton.addActionListener(e -> {

                usuario_actual.setUsuario(nombreField.getText());
                usuario_actual.setDireccion(direccionField.getText());
                usuario_actual.setTelefono(telefonoField.getText());

                String resultado = ActualizarUsuario.actualizarUsuario(usuario_actual, conn);
                JOptionPane.showMessageDialog(null, resultado);

                nombreField.setEnabled(false);
                direccionField.setEnabled(false);
                telefonoField.setEnabled(false);
                aceptarButton.setEnabled(false);

            modificarButton.setEnabled(true);
        });
    }}