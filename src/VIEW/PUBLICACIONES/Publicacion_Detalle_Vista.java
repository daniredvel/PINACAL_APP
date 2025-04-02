package VIEW.PUBLICACIONES;

import CONTROLLER.CRUD.PUBLICACION.GuardarPublicacion;
import MODEL.Publicacion;
import VIEW.INICIO.Inicio_Vista;
import VIEW.RES.Rutas;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class Publicacion_Detalle_Vista extends JFrame {
    private boolean isOriginalIcon = true;
    private final JLabel messageLabel;
    private final JButton saveButton;
    private final JPanel leftPanel;

    public Publicacion_Detalle_Vista(Publicacion publicacion) {
        setTitle("Detalle de Publicación");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setIconImage(Rutas.getImage(Rutas.ICONO));
        setTitle(publicacion.getTitulo());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(211, 205, 192));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        leftPanel = new JPanel();
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
        textViewFecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        datePanel.setBackground(new Color(211, 205, 192));
        datePanel.add(textViewFecha);

        contentPanel.add(datePanel, BorderLayout.EAST);

        ImageIcon originalIcon = new ImageIcon(Rutas.getImage(Rutas.GUARDAR));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        ImageIcon newIcon = new ImageIcon(Rutas.getImage(Rutas.GUARDAR_RELLENO));
        Image newImage = newIcon.getImage();
        Image newScaledImage = newImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon newScaledIcon = new ImageIcon(newScaledImage);

        saveButton = new JButton(scaledIcon);
        saveButton.setBackground(null);
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(saveButton);

        JPanel messagePanel = new JPanel();
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

            if (isOriginalIcon) {
                saveButton.setIcon(scaledIcon);
                System.out.println("Publicación retirada");
                GuardarPublicacion.retirarGuardadoPublicacion(publicacion, Inicio_Vista.getUsuario_actual());
            } else {
                saveButton.setIcon(newScaledIcon);
                System.out.println("Publicación guardada");
                GuardarPublicacion.guardarPublicacion(publicacion, Inicio_Vista.getUsuario_actual());

            }
            isOriginalIcon = !isOriginalIcon;


            Timer timer = new Timer(3000, event -> messageLabel.setVisible(false));
            timer.setRepeats(false);
            timer.start();
        });


        add(contentPanel);
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    /*
    public void addSaveButtonListener(java.awt.event.ActionListener listener) {
        saveButton.addActionListener(listener);
    }
     */
}