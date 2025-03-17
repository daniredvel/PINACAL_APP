package VIEW.PERSONAL;

import CONTROLLER.CRUD.USER.ActualizarUsuario;
import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;

import javax.swing.*;

import java.awt.*;
import java.sql.Connection;

public class Personal_Usuario extends JFrame {
    private final JTextField nombreField;
    private final JTextField direccionField;
    private final JTextField telefonoField;

    public Personal_Usuario(Usuario usuario_actual, Connection conn) {
        ActualizarUsuario actualizarUsuario = new ActualizarUsuario();

        setTitle("Personal Usuario");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridBagLayout());
        panelSuperior.setBackground(new Color(211, 205, 192));

        Font fuenteButton = new Font("Arial", Font.PLAIN, 18);

        JButton inicioButton = new JButton("Inicio");
        inicioButton.setFont(fuenteButton);
        inicioButton.setBackground(new Color(174, 101, 7));
        inicioButton.setForeground(Color.WHITE);
        inicioButton.setPreferredSize(new Dimension(150, 50));
        inicioButton.setMargin(new Insets(10, 20, 10, 20));

        JButton personalButton = new JButton("Personal");
        personalButton.setFont(fuenteButton);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setPreferredSize(new Dimension(150, 50));
        personalButton.setMargin(new Insets(10, 20, 10, 20));

        JButton modificarButton = new JButton("Modificar");
        modificarButton.setFont(fuenteButton);
        modificarButton.setBackground(new Color(174, 101, 7));
        modificarButton.setForeground(Color.WHITE);
        modificarButton.setPreferredSize(new Dimension(150, 50));
        modificarButton.setMargin(new Insets(10, 20, 10, 20));

        JButton aceptarButton;
        aceptarButton = new JButton("Aceptar");
        aceptarButton.setFont(fuenteButton);
        aceptarButton.setBackground(new Color(174, 101, 7));
        aceptarButton.setForeground(Color.WHITE);
        aceptarButton.setPreferredSize(new Dimension(150, 50));
        aceptarButton.setMargin(new Insets(10, 20, 10, 20));
        aceptarButton.setEnabled(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
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
        userPanel.setLayout(new GridLayout(3, 2));
        userPanel.setBackground(new Color(211, 205, 192));
        userPanel.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        userPanel.add(nombreLabel);

        nombreField = new JTextField(usuario_actual.getUsuario());
        nombreField.setFont(new Font("Arial", Font.PLAIN, 18));
        nombreField.setEnabled(false);
        userPanel.add(nombreField);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        direccionField = new JTextField(usuario_actual.getDireccion());
        direccionField.setFont(new Font("Arial", Font.PLAIN, 18));
        direccionField.setEnabled(false);
        userPanel.add(direccionField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        telefonoField = new JTextField(usuario_actual.getTelefono());
        telefonoField.setFont(new Font("Arial", Font.PLAIN, 18));
        telefonoField.setEnabled(false);
        userPanel.add(telefonoField);

        add(userPanel, BorderLayout.CENTER);

        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        });
        personalButton.addActionListener(e -> {
            dispose();
            new Personal_Usuario(usuario_actual, conn).setVisible(true);
        });

        modificarButton.addActionListener(e -> {
            nombreField.setEnabled(true);
            direccionField.setEnabled(true);
            telefonoField.setEnabled(true);
            aceptarButton.setEnabled(true);
        });

        aceptarButton.addActionListener(e -> {
            usuario_actual.setUsuario(nombreField.getText());
            usuario_actual.setDireccion(direccionField.getText());
            usuario_actual.setTelefono(telefonoField.getText());

            JOptionPane.showMessageDialog(null, actualizarUsuario.actualizarUsuario(usuario_actual));

            nombreField.setEnabled(false);
            direccionField.setEnabled(false);
            telefonoField.setEnabled(false);
            aceptarButton.setEnabled(false);
        });
    }
}