package VIEW.UTIL;

import CONTROLLER.ControladorDatos;
import CONTROLLER.VALIDATION.ControladorInicioSesion;
import MODEL.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Cambiar_Pass_Vista extends JDialog {
    private final JPasswordField originalPasswordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField confirmPasswordField;
    private final JLabel messageLabel;

    // Colores del esquema de la aplicación
    private static final Color BACKGROUND_COLOR = new Color(211, 205, 192);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color ERROR_COLOR = Color.RED;
    private static final Color BUTTON_COLOR = new Color(174, 101, 7);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    public Cambiar_Pass_Vista(JFrame parent, Usuario usuario_actual, Connection conn) {
        super(parent, "Cambiar Contraseña", true);
        setSize(700, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR); // Fondo del panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Contraseña original
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel originalPasswordLabel = new JLabel("Contraseña actual:");
        originalPasswordLabel.setForeground(TEXT_COLOR);
        formPanel.add(originalPasswordLabel, gbc);
        originalPasswordField = new JPasswordField(20);
        originalPasswordField.setEchoChar('•');
        originalPasswordField.setToolTipText("Introduce tu contraseña actual");
        gbc.gridx = 1;
        formPanel.add(originalPasswordField, gbc);

        // Nueva contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel newPasswordLabel = new JLabel("Nueva contraseña:");
        newPasswordLabel.setForeground(TEXT_COLOR);
        formPanel.add(newPasswordLabel, gbc);
        newPasswordField = new JPasswordField(20);
        newPasswordField.setEchoChar('•');
        newPasswordField.setToolTipText("Introduce tu nueva contraseña");
        gbc.gridx = 1;
        formPanel.add(newPasswordField, gbc);

        // Confirmar nueva contraseña
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel confirmPasswordLabel = new JLabel("Confirmar nueva contraseña:");
        confirmPasswordLabel.setForeground(TEXT_COLOR);
        formPanel.add(confirmPasswordLabel, gbc);
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setEchoChar('•');
        confirmPasswordField.setToolTipText("Confirma tu nueva contraseña");
        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);

        // Checkbox para mostrar/ocultar contraseñas
        JCheckBox showPasswordCheckBox = new JCheckBox("Mostrar contraseñas");
        showPasswordCheckBox.setBackground(BACKGROUND_COLOR);
        showPasswordCheckBox.setForeground(TEXT_COLOR);
        showPasswordCheckBox.addActionListener(e -> {
            char echoChar = showPasswordCheckBox.isSelected() ? '\0' : '•';
            originalPasswordField.setEchoChar(echoChar);
            newPasswordField.setEchoChar(echoChar);
            confirmPasswordField.setEchoChar(echoChar);
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(showPasswordCheckBox, gbc);

        // Mensaje de error
        messageLabel = new JLabel("");
        messageLabel.setForeground(ERROR_COLOR);
        gbc.gridy = 4;
        formPanel.add(messageLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Botones de aceptar y cancelar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR); // Fondo del panel de botones
        JButton acceptButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");

        // Estilo de los botones
        acceptButton.setBackground(BUTTON_COLOR);
        acceptButton.setForeground(BUTTON_TEXT_COLOR);
        cancelButton.setBackground(BUTTON_COLOR);
        cancelButton.setForeground(BUTTON_TEXT_COLOR);

        acceptButton.addActionListener(e -> {
            try {
                cambiarContrasena(usuario_actual, conn);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void cambiarContrasena(Usuario usuario_actual, Connection conn) throws Exception {
        String originalPassword = new String(originalPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validar contraseñas
        if (!newPassword.equals(confirmPassword)) {
            messageLabel.setText("Las contraseñas no coinciden.");
            return;
        }

        if (!validateFields(newPassword)) {
            return;
        }

        if ((ControladorInicioSesion.comprobarPass(usuario_actual.getUsuario(), originalPassword) != 1)) {
            messageLabel.setText("La contraseña actual es incorrecta.");
            return;
        }

        // Cambiar contraseña
        if (ControladorDatos.cambiarPass(usuario_actual, newPassword, conn)) {
            JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            messageLabel.setText("Error al cambiar la contraseña.");
        }
    }

    private boolean validateFields(String pass) {
        
        if (pass.isEmpty()) {
            messageLabel.setText("La contraseña es obligatoria");
            return false;
        }
        if (pass.length() < 8) {
            messageLabel.setText("La contraseña debe tener al menos 8 caracteres");
            return false;
        }
        if (!pass.matches(".*[A-Z].*")) {
            messageLabel.setText("La contraseña debe contener al menos una letra mayúscula");
            return false;
        }
        if (!pass.matches(".*[a-z].*")) {
            messageLabel.setText("La contraseña debe contener al menos una letra minúscula");
            return false;
        }
        if (!pass.matches(".*\\d.*")) {
            messageLabel.setText("La contraseña debe contener al menos un número");
            return false;
        }
        if (!pass.matches(".*\\W.*")) {
            messageLabel.setText("La contraseña debe contener al menos un símbolo");
            return false;
        }
        return true;
    }

}