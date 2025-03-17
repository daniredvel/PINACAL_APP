package VIEW.REGISTRO;

import MODEL.Usuario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.Connection;

public class Registro_Empresa extends JFrame {
    private final JTextField companyNameField;
    private final JTextField companyEmailField;
    private final JTextField companyPhoneField;
    private final JPasswordField companyPasswordField;
    private final JLabel messageLabel;
    private final JProgressBar passwordStrengthBar;
    private final JCheckBox showPasswordCheckBox;

    public Registro_Empresa(Connection conn) {
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

        // Etiqueta y campo de texto del Nombre de la Empresa
        JLabel companyNameLabel = new JLabel("Nombre de la Empresa:");
        companyNameLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(companyNameLabel, constraints);

        companyNameField = new JTextField(20);
        companyNameField.setFont(font);
        constraints.gridx = 1;
        panel.add(companyNameField, constraints);

        // Etiqueta y campo de texto del Correo electrónico de la Empresa
        JLabel companyEmailLabel = new JLabel("Correo Electrónico:");
        companyEmailLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(companyEmailLabel, constraints);

        companyEmailField = new JTextField(20);
        companyEmailField.setFont(font);
        constraints.gridx = 1;
        panel.add(companyEmailField, constraints);

        // Etiqueta y campo de texto del teléfono de la Empresa
        JLabel companyPhoneLabel = new JLabel("Teléfono:");
        companyPhoneLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(companyPhoneLabel, constraints);

        companyPhoneField = new JTextField(20);
        companyPhoneField.setFont(font);
        constraints.gridx = 1;
        panel.add(companyPhoneField, constraints);

        // Etiqueta y campo de texto del contraseña de la Empresa
        JLabel companyPasswordLabel = new JLabel("Contraseña:");
        companyPasswordLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(companyPasswordLabel, constraints);

        companyPasswordField = new JPasswordField(20);
        companyPasswordField.setFont(font);
        constraints.gridx = 1;
        panel.add(companyPasswordField, constraints);

        // Barra de progreso para la fuerza de la contraseña
        passwordStrengthBar = new JProgressBar(0, 100);
        passwordStrengthBar.setStringPainted(true);
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(passwordStrengthBar, constraints);

        // Añadir un document listener al campo de contraseña para actualizar la barra de fuerza
        companyPasswordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }
        });

        // Añadir un checkbox para mostrar/ocultar la contraseña
        showPasswordCheckBox = new JCheckBox("Mostrar Contraseña");
        showPasswordCheckBox.setFont(font);
        showPasswordCheckBox.setBackground(new Color(211, 205, 192));
        showPasswordCheckBox.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(showPasswordCheckBox, constraints);

        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                companyPasswordField.setEchoChar((char) 0);
            } else {
                companyPasswordField.setEchoChar('•');
            }
        });

        // Botón de Siguiente, para pasar a la ventana de la dirección de la empresa
        JButton registerButton = new JButton("Siguiente");
        registerButton.setFont(font);
        registerButton.setBackground(new Color(174, 101, 7));
        registerButton.setForeground(new Color(255, 255, 255));
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, constraints);

        // Añadir etiqueta de mensaje
        messageLabel = new JLabel("");
        messageLabel.setFont(font);
        messageLabel.setForeground(Color.RED);
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(messageLabel, constraints);

        // «Escuchador» del botón de Siguiente
        registerButton.addActionListener(e -> {
            //Si los campos están completos, se crea un usuario sin dirección y se pasa a la ventana de dirección
            if (validateFields()) {
                // Crear un usuario sin dirección
                Usuario usuario_sin_direccion = new Usuario(companyNameField.getText(), new String(companyPasswordField.getPassword()), companyEmailField.getText(), companyPhoneField.getText(), 10, "");
                // Cerrar la ventana actual
                dispose();
                // Abrir la de dirección
                new Registro_Empresa_Direccion(usuario_sin_direccion, conn).setVisible(true);
            }
        });

        //Añadir el panel al JFrame
        add(panel);
    }

    //Validación
    private boolean validateFields() {
        if (companyNameField.getText().isEmpty()) {
            messageLabel.setText("El nombre de la empresa es obligatorio");
            return false;
        }
        if (companyEmailField.getText().isEmpty()) {
            messageLabel.setText("El correo electrónico es obligatorio");
            return false;
        }
        if (!companyEmailField.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            messageLabel.setText("Indica un correo electrónico válido");
            return false;
        }
        if (companyPhoneField.getText().isEmpty()) {
            messageLabel.setText("El teléfono es obligatorio");
            return false;
        }
        if (!companyPhoneField.getText().matches("\\d{9}")) {
            messageLabel.setText("Indica un télefono válido");
            return false;
        }
        if (companyPasswordField.getPassword().length == 0) {
            messageLabel.setText("La contraseña es obligatoria");
            return false;
        }
        String password = new String(companyPasswordField.getPassword());
        if (password.length() < 8) {
            messageLabel.setText("La contraseña debe tener al menos 8 caracteres");
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            messageLabel.setText("La contraseña debe contener al menos una letra mayúscula");
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            messageLabel.setText("La contraseña debe contener al menos una letra minúscula");
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            messageLabel.setText("La contraseña debe contener al menos un número");
            return false;
        }
        if (!password.matches(".*\\W.*")) {
            messageLabel.setText("La contraseña debe contener al menos un símbolo");
            return false;
        }
        return true;
    }

    // METODO para actualizar la barra de fuerza de la contraseña
    private void updatePasswordStrength() {
        String password = new String(companyPasswordField.getPassword());
        int strength = calculatePasswordStrength(password);
        passwordStrengthBar.setValue(strength);
        if (strength < 50) {
            passwordStrengthBar.setString("Débil");
            passwordStrengthBar.setForeground(new Color(255, 102, 102)); // Soft red
        } else if (strength < 75) {
            passwordStrengthBar.setString("Media");
            passwordStrengthBar.setForeground(new Color(255, 178, 102)); // Soft orange
        } else {
            passwordStrengthBar.setString("Fuerte");
            passwordStrengthBar.setForeground(new Color(153, 255, 153)); // Soft green
        }
    }

    // METODO para calcular la fuerza de la contraseña
    private int calculatePasswordStrength(String password) {
        int strength = 0;
        if (password.length() >= 8) strength += 25;
        if (password.matches(".*[A-Z].*")) strength += 25;
        if (password.matches(".*[a-z].*")) strength += 25;
        if (password.matches(".*\\d.*")) strength += 15;
        if (password.matches(".*\\W.*")) strength += 10;
        return strength;
    }
}