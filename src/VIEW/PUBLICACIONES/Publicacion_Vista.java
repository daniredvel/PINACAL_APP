// src/VIEW/PUBLICACIONES/Publicacion_Vista.java
package VIEW.PUBLICACIONES;

import MODEL.Publicacion;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class Publicacion_Vista extends JPanel {
    private boolean isOriginalIcon = true;
    private JLabel messageLabel;
    private JPanel messagePanel;

    public Publicacion_Vista(Publicacion publicacion) {
        // Set panel properties
        setLayout(null);
        setBackground(new Color(211, 205, 192));

        // Create and add components
        JLabel textViewNombre = new JLabel(publicacion.getTitulo());
        textViewNombre.setFont(new Font("Arial", Font.PLAIN, 30));
        textViewNombre.setBounds(20, 20, 200, 40);
        add(textViewNombre);

        JLabel textViewTipo = new JLabel(String.valueOf(publicacion.getTipo()));
        textViewTipo.setFont(new Font("Arial", Font.PLAIN, 30));
        textViewTipo.setForeground(new Color(174, 101, 7)); // Orange color
        textViewTipo.setBounds(20, 90, 200, 40);
        add(textViewTipo);

        JLabel textViewDescripcion = new JLabel("<html>" + publicacion.getDescripcion() + "</html>");
        textViewDescripcion.setFont(new Font("Arial", Font.PLAIN, 20));
        textViewDescripcion.setBounds(20, 160, 760, 209); // Set width to 760 to occupy the full width
        add(textViewDescripcion);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        JLabel textViewFecha = new JLabel(dateFormat.format(publicacion.getFecha_publicacion()));
        textViewFecha.setFont(new Font("Arial", Font.BOLD, 20));
        textViewFecha.setBounds(450, 370, 200, 40);
        add(textViewFecha);

        // Load and rescale the initial icon
        ImageIcon originalIcon = new ImageIcon("src/VIEW/RES/guardar.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Load and rescale the new icon
        ImageIcon newIcon = new ImageIcon("src/VIEW/RES/guardarrrelleno.png");
        Image newImage = newIcon.getImage();
        Image newScaledImage = newImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon newScaledIcon = new ImageIcon(newScaledImage);

        // Create and add save button with rescaled icon
        JButton saveButton = new JButton(scaledIcon);
        saveButton.setBackground(null);
        saveButton.setBounds(20, 370, 42, 42); // Adjust size to fit the icon
        saveButton.setHorizontalAlignment(SwingConstants.CENTER);
        saveButton.setVerticalAlignment(SwingConstants.CENTER);
        add(saveButton);

        // Create message panel
        messagePanel = new JPanel();
        messagePanel.setBackground(new Color(174, 101, 7)); // Updated color
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setVisible(false);
        add(messagePanel);

        // Add message label with padding
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        // Add ActionListener to toggle the icon on button click
        saveButton.addActionListener(e -> {
            String mensaje;
            if (isOriginalIcon) {
                saveButton.setIcon(scaledIcon);
                mensaje = "Publicación retirada"; // Example message
            } else {
                saveButton.setIcon(newScaledIcon);
                mensaje = "Publicación guardada"; // Example message
            }
            isOriginalIcon = !isOriginalIcon;

            // Display the message in the label
            messageLabel.setText(mensaje);
            messagePanel.setSize(messageLabel.getPreferredSize().width + 20, messageLabel.getPreferredSize().height + 10);
            messagePanel.setLocation((getWidth() - messagePanel.getWidth()) / 2, textViewFecha.getY() + textViewFecha.getHeight() + 10);
            messagePanel.setVisible(true);

            // Set a timer to remove the message after 3 seconds
            Timer timer = new Timer(3000, event -> messagePanel.setVisible(false));
            timer.setRepeats(false);
            timer.start();
        });
    }
}