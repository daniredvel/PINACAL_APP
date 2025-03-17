package VIEW.INICIO_SESION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.REGISTRO.Registro_Vista;

import static CONTROLLER.CRUD.USER.LeerUsuario.leerUsuarioPorNombre;
import static CONTROLLER.VALIDATION.ControladorInicioSesion.comprobarPass;

public class InicioSesion_Vista extends JFrame {
    public static Usuario usuario_actual = null;
    private final JTextField userField;
    private final JPasswordField passField;
    private final JLabel messageLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JCheckBox showPasswordCheckBox;

    public InicioSesion_Vista() {
        // Propiedades del frame (Marco)
        setTitle("Inicio de Sesión");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel y layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255)); // Background color

        // Constraints del Layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20, 20); // Increase padding
        constraints.anchor = GridBagConstraints.WEST;

        // Fuente de los elementos
        Font font = new Font("Arial", Font.PLAIN, 24);

        // Etiqueta de mensaje
        messageLabel = new JLabel("");
        messageLabel.setFont(font);
        messageLabel.setForeground(Color.RED);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(messageLabel, constraints);

        // Etiqueta y Campo de texto del usuario
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

        // Etiqueta y Campo de texto de la contraseña
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setFont(font);
        passLabel.setForeground(new Color(0, 0, 0)); // Text color
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passLabel, constraints);
        panel.setBackground(new Color(211, 205, 192));

        passField = new JPasswordField(20);
        passField.setFont(font);
        passField.setEchoChar('\u2022'); // Set echo character to bullet point
        constraints.gridx = 1;
        panel.add(passField, constraints);

        // Checkbox para mostrar la contraseña
        showPasswordCheckBox = new JCheckBox("Mostrar Contraseña");
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
        showPasswordCheckBox.setBackground(new Color(211, 205, 192));
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(showPasswordCheckBox, constraints);

        // Botón de inicio de sesión
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(font);
        loginButton.setBackground(new Color(174, 101, 7)); // Button background color
        loginButton.setForeground(new Color(255, 255, 255)); // Button text color
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        // Botón de registro
        registerButton = new JButton("REGÍSTRATE");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 18));
        registerButton.setForeground(Color.BLUE);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(registerButton, constraints);

        // «Escuchador» del botón de inicio de sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login action
                String username = userField.getText();
                String password = new String(passField.getPassword());

                try {
                    // Comprueba si el usuario y la contraseña son correctos
                    switch (comprobarPass(username, password, "2")) {
                        case 1:
                            // Si el usuario y la contraseña son correctos, crea un objeto Usuario con los datos del usuario
                            //usuario_actual = leerUsuarioPorNombre(username);
                            // TODO: Cambiar la línea siguiente por la anterior
                            usuario_actual = new Usuario(username,password,"aaaaa","nnnnn","000",0,"");

                            // Cierra la ventana actual
                            dispose();
                            // Abre la ventana de inicio
                            new Inicio_Vista(usuario_actual).setVisible(true);
                            break;
                        case -1: case 0:
                            // Indica en un mensaje que la contraseña es incorrecta
                            messageLabel.setText("Contraseña o usuario incorrectos");
                            messageLabel.setForeground(new Color(233, 30, 99));
                            break;
                    }
                } catch (Exception ignored) {
                }
            }
        });

        // «Escuchador» del botón de registro
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cierra la ventana de inicio de sesión
                dispose();
                // Abre la ventana de registro
                new Registro_Vista().setVisible(true);
            }
        });

        // «Escuchador» del checkbox para mostrar la contraseña
        showPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passField.setEchoChar((char) 0); // Muestra la contraseña
                } else {
                    passField.setEchoChar('•'); // Oculta la contraseña
                }
            }
        });

        // Añade el panel al marco
        add(panel);
    }
}