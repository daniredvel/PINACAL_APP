package VIEW.REGISTRO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

//Ventana basica de registro, donde se escoge si es usuario o empresa
public class Registro_Vista extends JFrame {
    private static Connection conn;
    public Registro_Vista(Connection conn) {
        this.conn = conn;
        // Set frame properties
        setTitle("REGISTRO");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear Panel y Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(211, 205, 192));

        // Constraints para el layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.anchor = GridBagConstraints.CENTER;

        // Fuente para los elementos
        Font font = new Font("Arial", Font.PLAIN, 24);

        // Añade una etiqueta
        JLabel label = new JLabel("Escoge una opción para registrarte");
        label.setFont(font);
        label.setForeground(new Color(0, 0, 0));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(label, constraints);

        // Añade el botón de la opción de EMPRESA
        JButton empresaButton = new JButton("EMPRESA");
        empresaButton.setFont(font);
        empresaButton.setBackground(new Color(174, 101, 7));
        empresaButton.setForeground(new Color(255, 255, 255));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(empresaButton, constraints);

        // Añade el botón de la opción de USUARIO
        JButton usuarioButton = new JButton("USUARIO");
        usuarioButton.setFont(font);
        usuarioButton.setBackground(new Color(174, 101, 7));
        usuarioButton.setForeground(new Color(255, 255, 255));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(usuarioButton, constraints);

        // «Escuchadores» de los botones
        empresaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Cierra la ventana actual y abre la de registro de empresa
                dispose();
                new Registro_Empresa(conn).setVisible(true);
            }
        });

        usuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Cierra la ventana actual y abre la de registro de usuario
                dispose();
                new Registro_Usuario(conn).setVisible(true);
            }
        });

        // Añade el panel al marco
        add(panel);
    }
}