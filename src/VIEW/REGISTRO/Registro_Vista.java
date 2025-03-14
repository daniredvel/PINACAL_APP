package VIEW.REGISTRO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Ventana basica de registro, donde se escoge si es usuario o empresa
public class Registro_Vista extends JFrame {
    public Registro_Vista() {
        // Set frame properties
        setTitle("REGISTRO");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel and set layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(211, 205, 192));

        // Create constraints for layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.anchor = GridBagConstraints.CENTER;

        // Set font for components
        Font font = new Font("Arial", Font.PLAIN, 24);

        // Add label
        JLabel label = new JLabel("Escoge una opción para registrarte");
        label.setFont(font);
        label.setForeground(new Color(0, 0, 0));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(label, constraints);

        // Add EMPRESA button
        JButton empresaButton = new JButton("EMPRESA");
        empresaButton.setFont(font);
        empresaButton.setBackground(new Color(174, 101, 7));
        empresaButton.setForeground(new Color(255, 255, 255));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(empresaButton, constraints);

        // Add USUARIO button
        JButton usuarioButton = new JButton("USUARIO");
        usuarioButton.setFont(font);
        usuarioButton.setBackground(new Color(174, 101, 7));
        usuarioButton.setForeground(new Color(255, 255, 255));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(usuarioButton, constraints);

        // Add action listeners to buttons
        empresaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Registro_Empresa().setVisible(true);
            }
        });

        usuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Registro_Usuario().setVisible(true);
            }
        });

        // Add panel to frame
        add(panel);
    }
}