package VIEW.PERSONAL;

import CONTROLLER.CRUD.USER.ActualizarUsuario;
import MODEL.Usuario;
import VIEW.ADD.Add_Empresa;
import VIEW.INICIO.Inicio_Vista;
import VIEW.RES.Rutas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Personal_Empresa extends JFrame {
    private final JButton modificarButton;
    private final ActualizarUsuario actualizarUsuario;
    private final JTextField nombreField;
    private final JTextField direccionField;
    private final JTextField telefonoField;

    public Personal_Empresa(Usuario usuario_actual, Connection conn) {
        actualizarUsuario = new ActualizarUsuario();

        setTitle("Personal Empresa");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Icono
        setIconImage(Rutas.getIcono());

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

        JButton anadirButton = new JButton("Mis Publicaciones");
        anadirButton.setFont(fuenteButton);
        anadirButton.setBackground(new Color(174, 101, 7));
        anadirButton.setForeground(Color.WHITE);
        anadirButton.setPreferredSize(new Dimension(150, 50));
        anadirButton.setMargin(new Insets(10, 20, 10, 20));

        modificarButton = new JButton("Modificar");
        modificarButton.setFont(fuenteButton);
        modificarButton.setBackground(new Color(174, 101, 7));
        modificarButton.setForeground(Color.WHITE);
        modificarButton.setPreferredSize(new Dimension(150, 50));
        modificarButton.setMargin(new Insets(10, 20, 10, 20));
        modificarButton.setEnabled(false);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(inicioButton, gbc);

        gbc.gridx = 1;
        panelSuperior.add(personalButton, gbc);

        gbc.gridx = 2;
        panelSuperior.add(anadirButton, gbc);

        gbc.gridx = 3;
        panelSuperior.add(modificarButton, gbc);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel empresaPanel = new JPanel();
        empresaPanel.setLayout(new GridLayout(3, 2));
        empresaPanel.setBackground(new Color(211, 205, 192));
        empresaPanel.setBorder(BorderFactory.createTitledBorder("Datos de la Empresa"));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        empresaPanel.add(nombreLabel);

        nombreField = new JTextField(usuario_actual.getUsuario());
        nombreField.setFont(new Font("Arial", Font.PLAIN, 18));
        nombreField.setEnabled(false);
        empresaPanel.add(nombreField);

        JLabel direccionLabel = new JLabel("Dirección:");
        direccionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        direccionField = new JTextField(usuario_actual.getDireccion());
        direccionField.setFont(new Font("Arial", Font.PLAIN, 18));
        direccionField.setEnabled(false);
        empresaPanel.add(direccionField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        telefonoField = new JTextField(usuario_actual.getTelefono());
        telefonoField.setFont(new Font("Arial", Font.PLAIN, 18));
        telefonoField.setEnabled(false);
        empresaPanel.add(telefonoField);

        add(empresaPanel, BorderLayout.CENTER);

        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        });
        personalButton.addActionListener(e ->{
                dispose();
        new Personal_Empresa(usuario_actual, conn).setVisible(true);
        });
        anadirButton.addActionListener(e -> {

            dispose();
            if (usuario_actual.getTipo().equals("EMPRESA_ASOCIADA")||usuario_actual.getTipo().equals("EMPRESA_NO_ASOCIADA")) {
                new Add_Empresa(usuario_actual, conn).setVisible(true);
            }
        });

        modificarButton.addActionListener(e -> {
            usuario_actual.setUsuario(nombreField.getText());
            usuario_actual.setDireccion(direccionField.getText());
            usuario_actual.setTelefono(telefonoField.getText());

            JOptionPane.showMessageDialog(null, ActualizarUsuario.actualizarUsuario(usuario_actual, conn));

            nombreField.setEnabled(false);
            direccionField.setEnabled(false);
            telefonoField.setEnabled(false);
            modificarButton.setEnabled(false);
        });
    }
}