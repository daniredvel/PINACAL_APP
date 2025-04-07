package VIEW.PUBLICACIONES;

import CONTROLLER.CRUD.PUBLICACION.GuardarPublicacion;
import CONTROLLER.ControladorDatos;
import DB.UTIL.GestorConexion;
import MODEL.Publicacion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import VIEW.ERROR.Error_INICIAR_BD;
import VIEW.PERFILES.Perfil_Usuario_Vista;
import VIEW.RES.Rutas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Publicacion_Detalle_Vista extends JFrame {
    private boolean isOriginalIcon = true;
    private final JButton saveButton;
    private final JPanel leftPanel;
    private static final Logger LOGGER = Logger.getLogger(Publicacion_Detalle_Vista.class.getName());

    public Publicacion_Detalle_Vista(Publicacion publicacion, Usuario usuario_actual, Connection conexion) {

        //VISTA
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

        textViewUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        textViewUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Connection conn = GestorConexion.getConexion();
                Usuario autor = ControladorDatos.obtenerUsuarioPorNombre(conn, publicacion.getUsuario());
                if (autor != null) {

                    SwingUtilities.invokeLater(() -> new Perfil_Usuario_Vista(conn, autor, usuario_actual).setVisible(true));

                } else {
                    LOGGER.log(Level.SEVERE, "No se pudo encontrar el autor: " + publicacion.getUsuario());
                }
            }
        });

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

        //ICONOS DE GUARDADO

        ImageIcon originalIcon = new ImageIcon(Rutas.getImage(Rutas.GUARDAR));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        ImageIcon newIcon = new ImageIcon(Rutas.getImage(Rutas.GUARDAR_RELLENO));
        Image newImage = newIcon.getImage();
        Image newScaledImage = newImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon newScaledIcon = new ImageIcon(newScaledImage);

        //BOTÓN DE GUARDADO
        saveButton = new JButton(scaledIcon);
        saveButton.setBackground(null);
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(saveButton);


        //ACCIÓN DEL BOTÓN DE GUARDADO
        saveButton.addActionListener(e -> {

            if (isOriginalIcon) {
                saveButton.setIcon(scaledIcon);
                System.out.printf("Publicación retirada: %s, autor: %s%n", publicacion.getTitulo(), publicacion.getUsuario());
                LOGGER.log(Level.INFO, Mensajes.getMensaje(Mensajes.PUBLICACION_RETIRADA));
                System.out.println(GuardarPublicacion.retirarGuardadoPublicacion(publicacion, usuario_actual, conexion));
            } else {
                saveButton.setIcon(newScaledIcon);
                LOGGER.log(Level.INFO, Mensajes.getMensaje(Mensajes.PUBLICACION_GUARDADA));
                System.out.printf("Publicación guardada: %s, autor: %s%n", publicacion.getTitulo(), publicacion.getUsuario());
                System.out.println(GuardarPublicacion.guardarPublicacion(publicacion, usuario_actual, conexion));

            }
            isOriginalIcon = !isOriginalIcon;

        });


        add(contentPanel);
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }
}