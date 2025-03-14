package VIEW.INICIO_SESION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.REGISTRO.Registro_Vista;

import static CONTROLLER.CRUD.USER.LeerUsuario.leerUsuarioPorNombre;
import static CONTROLLER.VALIDATION.ControladorInicioSesion.comprobarPass;

public class InicioSesion_Vista extends JFrame {
    public static Usuario usuario_actual=null;
    private final JTextField userField;
    private final JPasswordField passField;
    private final JLabel messageLabel;
    private JButton loginButton;
    private JButton registerButton;

    public InicioSesion_Vista() {
        // Set frame properties
        setTitle("Inicio de Sesión");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel and set layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255)); // Background color

        // Create constraints for layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20, 20); // Increase padding
        constraints.anchor = GridBagConstraints.WEST;

        // Set font for components
        Font font = new Font("Arial", Font.PLAIN, 24);

        // Add message label
        messageLabel = new JLabel("");
        messageLabel.setFont(font);
        messageLabel.setForeground(Color.RED);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(messageLabel, constraints);

        // Add user label and text field
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setFont(font);
        userLabel.setForeground(new Color(0, 0, 0)); // Text color
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(userLabel, constraints);

        userField = new JTextField(20);
        userField.setFont(font);
        constraints.gridx = 1;
        panel.add(userField, constraints);

        // Add password label and password field
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setFont(font);
        passLabel.setForeground(new Color(0, 0, 0)); // Text color
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passLabel, constraints);
        panel.setBackground(new Color(211, 205, 192));

        passField = new JPasswordField(20);
        passField.setFont(font);
        constraints.gridx = 1;
        panel.add(passField, constraints);

        // Add login button
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(font);
        loginButton.setBackground(new Color(174, 101, 7)); // Button background color
        loginButton.setForeground(new Color(255, 255, 255)); // Button text color
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        // Add register button
        registerButton = new JButton("REGÍSTRATE");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 18));
        registerButton.setForeground(Color.BLUE);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(registerButton, constraints);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login action
                String username = userField.getText();
                String password = new String(passField.getPassword());

                try {
                    switch (comprobarPass(username, password, "2")) {
                        case 1:
                            usuario_actual = leerUsuarioPorNombre(username);
                            // Close current window and open Inicio_Vista
                            dispose();
                            new Inicio_Vista(usuario_actual).setVisible(true);
                            break;
                        case -1:
                            messageLabel.setText("Contraseña incorrecta");
                            messageLabel.setForeground(new Color(233, 30, 99));
                            break;
                        case 0:
                            messageLabel.setText("Usuario no encontrado");
                            messageLabel.setForeground(new Color(233, 30, 99));
                            break;
                    }
                } catch (Exception ignored) {
                }
            }
        });

        // Add action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Registro_Vista
                dispose();
                new Registro_Vista().setVisible(true);
            }
        });

        // Add panel to frame
        add(panel);
    }
}