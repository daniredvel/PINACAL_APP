package VIEW.ERROR;

import javax.swing.*;
import java.awt.*;

public class Error_INICIAR_BD extends JFrame {

    public Error_INICIAR_BD() {
        // Set frame properties
        setTitle("Error de Conexión");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));

        // Create a label for the error message
        JLabel errorMessage = new JLabel("No se puede acceder a la aplicación.", SwingConstants.CENTER);
        errorMessage.setFont(new Font("Arial", Font.BOLD, 16));
        errorMessage.setForeground(Color.RED);

        // Create a label for the apology message
        JLabel apologyMessage = new JLabel("Lo sentimos por los inconvenientes.", SwingConstants.CENTER);
        apologyMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        apologyMessage.setForeground(Color.BLACK);

        // Add the labels to the panel
        panel.add(errorMessage, BorderLayout.CENTER);
        panel.add(apologyMessage, BorderLayout.SOUTH);

        // Add the panel to the frame
        add(panel);
    }
}