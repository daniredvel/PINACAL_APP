package VIEW.PUBLICACIONES;

import MODEL.Publicacion;
import MODEL.Usuario;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class Publicacion_Vista extends JPanel {
    private boolean isOriginalIcon = true;
    private final JLabel messageLabel;
    private final JPanel messagePanel;

    public Publicacion_Vista(Publicacion publicacion, Usuario usuario_actual) {
        setLayout(new BorderLayout());
        setBackground(new Color(211, 205, 192));
        setBorder(BorderFactory.createLineBorder(new Color(174, 101, 7), 2)); // Orange border
        setPreferredSize(new Dimension(800, 300)); // Set preferred size

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(211, 205, 192));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(211, 205, 192));

        JLabel textViewTipo = new JLabel(publicacion.getTipo());
        textViewTipo.setFont(new Font("Arial", Font.PLAIN, 30));
        textViewTipo.setForeground(new Color(174, 101, 7));
        textViewTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(textViewTipo);

        JLabel textViewNombre = new JLabel(publicacion.getTitulo());
        textViewNombre.setFont(new Font("Arial", Font.PLAIN, 30));
        textViewNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(textViewNombre);

        JLabel textViewUsuario = new JLabel("Autor: " + publicacion.getUsuario());
        textViewUsuario.setFont(new Font("Arial", Font.PLAIN, 20));
        textViewUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(textViewUsuario);



        JLabel textViewDescripcion = new JLabel("<html>" + publicacion.getDescripcion() + "</html>");
        textViewDescripcion.setFont(new Font("Arial", Font.PLAIN, 20));
        textViewDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(textViewDescripcion);

        contentPanel.add(leftPanel, BorderLayout.CENTER);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        JLabel textViewFecha = new JLabel(dateFormat.format(publicacion.getFecha_publicacion()));
        textViewFecha.setFont(new Font("Arial", Font.BOLD, 20));
        textViewFecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Add margin to the right

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        datePanel.setBackground(new Color(211, 205, 192));
        datePanel.add(textViewFecha);

        contentPanel.add(datePanel, BorderLayout.EAST);

        ImageIcon originalIcon = new ImageIcon("src/VIEW/RES/guardar.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        ImageIcon newIcon = new ImageIcon("src/VIEW/RES/guardarrrelleno.png");
        Image newImage = newIcon.getImage();
        Image newScaledImage = newImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon newScaledIcon = new ImageIcon(newScaledImage);

        JButton saveButton = new JButton(scaledIcon);
        saveButton.setBackground(null);
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(saveButton);

        messagePanel = new JPanel();
        messagePanel.setBackground(new Color(174, 101, 7));
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setVisible(false);
        leftPanel.add(messagePanel);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        saveButton.addActionListener(e -> {
            String mensaje;
            if (isOriginalIcon) {
                saveButton.setIcon(scaledIcon);
                mensaje = "Publicación retirada";
            } else {
                saveButton.setIcon(newScaledIcon);
                mensaje = "Publicación guardada";
            }
            isOriginalIcon = !isOriginalIcon;

            messageLabel.setText(mensaje);
            messagePanel.setSize(messageLabel.getPreferredSize().width + 20, messageLabel.getPreferredSize().height + 10);
            messagePanel.setLocation((getWidth() - messagePanel.getWidth()) / 2, textViewFecha.getY() + textViewFecha.getHeight() + 10);
            messagePanel.setVisible(true);

            Timer timer = new Timer(3000, event -> messagePanel.setVisible(false));
            timer.setRepeats(false);
            timer.start();
        });

        add(contentPanel, BorderLayout.CENTER);
    }
}