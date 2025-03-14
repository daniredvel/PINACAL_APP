package VIEW.REGISTRO;

import MODEL.Usuario;
import VIEW.INICIO_SESION.InicioSesion_Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registro_Empresa extends JFrame {
    private JTextField companyNameField;
    private JTextField companyEmailField;
    private JTextField companyPhoneField;
    private JPasswordField companyPasswordField;
    private JLabel messageLabel;

    public Registro_Empresa() {
        setTitle("Registro Empresa");
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

        // Add company name label and text field
        JLabel companyNameLabel = new JLabel("Nombre de la Empresa:");
        companyNameLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(companyNameLabel, constraints);

        companyNameField = new JTextField(20);
        companyNameField.setFont(font);
        constraints.gridx = 1;
        panel.add(companyNameField, constraints);

        // Add company email label and text field
        JLabel companyEmailLabel = new JLabel("Correo Electrónico:");
        companyEmailLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(companyEmailLabel, constraints);

        companyEmailField = new JTextField(20);
        companyEmailField.setFont(font);
        constraints.gridx = 1;
        panel.add(companyEmailField, constraints);

        // Add company phone label and text field
        JLabel companyPhoneLabel = new JLabel("Teléfono:");
        companyPhoneLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(companyPhoneLabel, constraints);

        companyPhoneField = new JTextField(20);
        companyPhoneField.setFont(font);
        constraints.gridx = 1;
        panel.add(companyPhoneField, constraints);

        // Add company password label and password field
        JLabel companyPasswordLabel = new JLabel("Contraseña:");
        companyPasswordLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(companyPasswordLabel, constraints);

        companyPasswordField = new JPasswordField(20);
        companyPasswordField.setFont(font);
        constraints.gridx = 1;
        panel.add(companyPasswordField, constraints);

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
                    Usuario usuario_sin_direccion = new Usuario(companyNameLabel.getText(),companyPasswordLabel.getText(),companyEmailLabel.getText(),"",companyPhoneLabel.getText(),10,"");
                    dispose();
                    new Registro_Empresa_Direccion(usuario_sin_direccion).setVisible(true);
                }
            }
        });

        add(panel);
    }

    private boolean validateFields() {
        if (companyNameField.getText().isEmpty()) {
            messageLabel.setText("El nombre de la empresa es obligatorio");
            return false;
        }
        if (companyEmailField.getText().isEmpty()) {
            messageLabel.setText("El correo electrónico es obligatorio");
            return false;
        }
        if (!companyEmailField.getText().contains("@")) {
            messageLabel.setText("El correo electrónico no es válido");
            return false;
        }
        if (companyPhoneField.getText().isEmpty()) {
            messageLabel.setText("El teléfono es obligatorio");
            return false;
        }
        if (companyPasswordField.getPassword().length == 0) {
            messageLabel.setText("La contraseña es obligatoria");
            return false;
        }
        return true;
    }

}