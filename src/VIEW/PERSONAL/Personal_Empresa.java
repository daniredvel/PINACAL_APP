package VIEW.PERSONAL;

import CONTROLLER.CRUD.USER.ActualizarUsuario;
import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.RES.Rutas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Personal_Empresa extends JFrame {
    protected final JButton modificarButton;
    protected final JButton aceptarButton;
    protected final JTextField nombreField;
    protected final JTextField direccionField;
    protected final JTextField telefonoField;

    public Personal_Empresa(Usuario usuario_actual, Connection conn) {
        setTitle("Personal Empresa");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Icono
        setIconImage(Rutas.getImage(Rutas.ICONO));

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
        inicioButton.setMargin(new Insets(10, 20, 10, 20));
        inicioButton.setPreferredSize(null);

        JButton personalButton = new JButton("Personal");
        personalButton.setFont(fuenteButton);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setMargin(new Insets(10, 20, 10, 20));
        personalButton.setPreferredSize(null);

        modificarButton = new JButton("Modificar");
        modificarButton.setFont(fuenteButton);
        modificarButton.setBackground(new Color(174, 101, 7));
        modificarButton.setForeground(Color.WHITE);
        modificarButton.setMargin(new Insets(10, 20, 10, 20));
        modificarButton.setPreferredSize(null);
        modificarButton.setEnabled(false);

        aceptarButton = new JButton("Aceptar");
        aceptarButton.setFont(fuenteButton);
        aceptarButton.setBackground(new Color(174, 101, 7));
        aceptarButton.setForeground(Color.WHITE);
        aceptarButton.setMargin(new Insets(10, 20, 10, 20));
        aceptarButton.setPreferredSize(null);
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

        JPanel empresaPanel = new JPanel();
        empresaPanel.setLayout(new GridBagLayout());
        empresaPanel.setBackground(new Color(211, 205, 192));
        empresaPanel.setBorder(BorderFactory.createTitledBorder("Datos de la Empresa"));

        Font fuenteLabel = new Font("Arial", Font.PLAIN, 14);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(fuenteLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        empresaPanel.add(nombreLabel, gbc);

        nombreField = new JTextField(usuario_actual.getUsuario());
        nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
        nombreField.setEnabled(false);
        nombreField.setColumns(usuario_actual.getUsuario().length());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        empresaPanel.add(nombreField, gbc);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setFont(fuenteLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        empresaPanel.add(direccionLabel, gbc);

        direccionField = new JTextField(usuario_actual.getDireccion());
        direccionField.setFont(new Font("Arial", Font.PLAIN, 14));
        direccionField.setEnabled(false);
        direccionField.setColumns(usuario_actual.getDireccion().length());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        empresaPanel.add(direccionField, gbc);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setFont(fuenteLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        empresaPanel.add(telefonoLabel, gbc);

        telefonoField = new JTextField(usuario_actual.getTelefono());
        telefonoField.setFont(new Font("Arial", Font.PLAIN, 14));
        telefonoField.setEnabled(false);
        telefonoField.setColumns(usuario_actual.getTelefono().length());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        empresaPanel.add(telefonoField, gbc);

        add(empresaPanel, BorderLayout.CENTER);

        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        });
        personalButton.addActionListener(e -> {
            dispose();
            new Personal_Empresa(usuario_actual, conn).setVisible(true);
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

            String resultado = ActualizarUsuario.actualizarUsuario(usuario_actual);
            JOptionPane.showMessageDialog(null, resultado);

            nombreField.setEnabled(false);
            direccionField.setEnabled(false);
            telefonoField.setEnabled(false);
            aceptarButton.setEnabled(false);
            modificarButton.setEnabled(true);
        });
    }
}