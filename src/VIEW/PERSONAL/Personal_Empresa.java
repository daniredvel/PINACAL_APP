package VIEW.PERSONAL;

import CONTROLLER.CRUD.USER.ActualizarUsuario;
import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;

import javax.swing.*;
import java.awt.*;

public class Personal_Empresa extends JFrame {
    private JButton inicioButton;
    private JButton personalButton;
    private JButton anadirButton;
    private JButton modificarButton;
    private ActualizarUsuario actualizarUsuario;
    private Usuario usuario_actual;
    private JTextField nombreField;
    private JTextField direccionField;
    private JTextField telefonoField;

    public Personal_Empresa(Usuario usuario_actual) {
        this.usuario_actual = usuario_actual;
        actualizarUsuario = new ActualizarUsuario();

        setTitle("Personal Empresa");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(new Color(211, 205, 192));

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        inicioButton = new JButton("Inicio");
        inicioButton.setFont(buttonFont);
        inicioButton.setBackground(new Color(174, 101, 7));
        inicioButton.setForeground(Color.WHITE);
        inicioButton.setPreferredSize(new Dimension(150, 50));
        inicioButton.setMargin(new Insets(10, 20, 10, 20));

        personalButton = new JButton("Personal");
        personalButton.setFont(buttonFont);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setPreferredSize(new Dimension(150, 50));
        personalButton.setMargin(new Insets(10, 20, 10, 20));

        anadirButton = new JButton("Añadir");
        anadirButton.setFont(buttonFont);
        anadirButton.setBackground(new Color(174, 101, 7));
        anadirButton.setForeground(Color.WHITE);
        anadirButton.setPreferredSize(new Dimension(150, 50));
        anadirButton.setMargin(new Insets(10, 20, 10, 20));

        modificarButton = new JButton("Modificar");
        modificarButton.setFont(buttonFont);
        modificarButton.setBackground(new Color(174, 101, 7));
        modificarButton.setForeground(Color.WHITE);
        modificarButton.setPreferredSize(new Dimension(150, 50));
        modificarButton.setMargin(new Insets(10, 20, 10, 20));
        modificarButton.setEnabled(false);


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
        topPanel.add(modificarButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        JPanel companyPanel = new JPanel();
        companyPanel.setLayout(new GridLayout(3, 2));
        companyPanel.setBackground(new Color(211, 205, 192));
        companyPanel.setBorder(BorderFactory.createTitledBorder("Datos de la Empresa"));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        companyPanel.add(nombreLabel);

        nombreField = new JTextField(usuario_actual.getUsuario());
        nombreField.setFont(new Font("Arial", Font.PLAIN, 18));
        nombreField.setEnabled(false);
        companyPanel.add(nombreField);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        direccionField = new JTextField(usuario_actual.getDireccion());
        direccionField.setFont(new Font("Arial", Font.PLAIN, 18));
        direccionField.setEnabled(false);
        companyPanel.add(direccionField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        telefonoField = new JTextField(usuario_actual.getTelefono());
        telefonoField.setFont(new Font("Arial", Font.PLAIN, 18));
        telefonoField.setEnabled(false);
        companyPanel.add(telefonoField);

        add(companyPanel, BorderLayout.CENTER);

        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual).setVisible(true);
        });
        personalButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Personal button clicked"));
        anadirButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Añadir button clicked"));

        modificarButton.addActionListener(e -> {
            usuario_actual.setUsuario(nombreField.getText());
            usuario_actual.setDireccion(direccionField.getText());
            usuario_actual.setTelefono(telefonoField.getText());

            JOptionPane.showMessageDialog(null, actualizarUsuario.actualizarUsuario(usuario_actual));

            nombreField.setEnabled(false);
            direccionField.setEnabled(false);
            telefonoField.setEnabled(false);
            modificarButton.setEnabled(false);
        });
    }
}