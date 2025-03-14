package VIEW.REGISTRO;

import CONTROLLER.CRUD.USER.AddUsuario;
import MODEL.Usuario;
import VIEW.INICIO_SESION.InicioSesion_Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registro_Usuario extends JFrame {
    private JTextField userNameField;
    private JTextField userEmailField;
    private JTextField userPhoneField;
    private JPasswordField userPasswordField;
    private JLabel messageLabel;

    public Registro_Usuario() {
        setTitle("Registro Usuario");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(211, 205, 192));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        Font font = new Font("Arial", Font.PLAIN, 18);

        // Add user name label and text field
        JLabel userNameLabel = new JLabel("Nombre de Usuario:");
        userNameLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(userNameLabel, constraints);

        userNameField = new JTextField(20);
        userNameField.setFont(font);
        constraints.gridx = 1;
        panel.add(userNameField, constraints);

        // Add user email label and text field
        JLabel userEmailLabel = new JLabel("Correo Electrónico:");
        userEmailLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(userEmailLabel, constraints);

        userEmailField = new JTextField(20);
        userEmailField.setFont(font);
        constraints.gridx = 1;
        panel.add(userEmailField, constraints);

        // Add user phone label and text field
        JLabel userPhoneLabel = new JLabel("Teléfono:");
        userPhoneLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(userPhoneLabel, constraints);

        userPhoneField = new JTextField(20);
        userPhoneField.setFont(font);
        constraints.gridx = 1;
        panel.add(userPhoneField, constraints);

        // Add user password label and password field
        JLabel userPasswordLabel = new JLabel("Contraseña:");
        userPasswordLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(userPasswordLabel, constraints);

        userPasswordField = new JPasswordField(20);
        userPasswordField.setFont(font);
        constraints.gridx = 1;
        panel.add(userPasswordField, constraints);

        // Add register button
        JButton registerButton = new JButton("Registrar");
        registerButton.setFont(font);
        registerButton.setBackground(new Color(174, 101, 7));
        registerButton.setForeground(new Color(255, 255, 255));
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, constraints);

        // Add message label
        messageLabel = new JLabel("");
        messageLabel.setFont(font);
        messageLabel.setForeground(Color.RED);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(messageLabel, constraints);

        // Add action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {

                    Usuario usuario = new Usuario(userNameField.getText(), new String(userPasswordField.getPassword()), userEmailField.getText(), userPhoneField.getText(),10,"" );
                    String mensaje = AddUsuario.addUsuario(usuario);
                    JOptionPane.showMessageDialog(null, mensaje, "Registro Usuario", JOptionPane.INFORMATION_MESSAGE);

                    dispose();
                    new InicioSesion_Vista().setVisible(true);
                }
            }
        });

        add(panel);
    }

    private boolean validateFields() {
        if (userNameField.getText().isEmpty()) {
            messageLabel.setText("El nombre de usuario es obligatorio");
            return false;
        }
        if (userEmailField.getText().isEmpty()) {
            messageLabel.setText("El correo electrónico es obligatorio");
            return false;
        }
        if (!userEmailField.getText().contains("@")) {
            messageLabel.setText("El correo electrónico no es válido");
            return false;
        }
        if (userPhoneField.getText().isEmpty()) {
            messageLabel.setText("El teléfono es obligatorio");
            return false;
        }
        if (userPasswordField.getPassword().length == 0) {
            messageLabel.setText("La contraseña es obligatoria");
            return false;
        }
        return true;
    }

}