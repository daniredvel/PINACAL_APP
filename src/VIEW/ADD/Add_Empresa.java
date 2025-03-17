package VIEW.ADD;

import CONTROLLER.CRUD.PUBLICACION.AddPublicacion;
import CONTROLLER.ControladorDatos;
import MODEL.Publicacion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.PERSONAL.Personal_Empresa;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Add_Empresa extends JFrame {
    private JButton inicioButton;
    private JButton personalButton;
    private JButton anadirButton;
    private JButton nuevaPublicacionButton;
    private Usuario usuario_actual;
    private ControladorDatos controladorDatos;
    private AddPublicacion addPublicacion;

    public Add_Empresa(Usuario usuario_actual) {
        this.usuario_actual = usuario_actual;
        controladorDatos = new ControladorDatos();
        addPublicacion = new AddPublicacion();

        setTitle("Publicaciones del Usuario");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(new Color(211, 205, 192));

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        inicioButton = new JButton("Inicio");
        inicioButton.setFont(buttonFont);
        inicioButton.setBackground(new Color(174, 101, 7));
        inicioButton.setForeground(Color.WHITE);
        inicioButton.setPreferredSize(new Dimension(150, 50));
        inicioButton.setMargin(new Insets(10, 20, 10, 20));

        personalButton = new JButton("Personal");
        personalButton.setFont(buttonFont);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setPreferredSize(new Dimension(150, 50));
        personalButton.setMargin(new Insets(10, 20, 10, 20));

        anadirButton = new JButton("Añadir");
        anadirButton.setFont(buttonFont);
        anadirButton.setBackground(new Color(174, 101, 7));
        anadirButton.setForeground(Color.WHITE);
        anadirButton.setPreferredSize(new Dimension(150, 50));
        anadirButton.setMargin(new Insets(10, 20, 10, 20));

        nuevaPublicacionButton = new JButton("Nueva Publicación");
        nuevaPublicacionButton.setFont(buttonFont);
        nuevaPublicacionButton.setBackground(new Color(174, 101, 7));
        nuevaPublicacionButton.setForeground(Color.WHITE);
        nuevaPublicacionButton.setPreferredSize(new Dimension(200, 50));
        nuevaPublicacionButton.setMargin(new Insets(10, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(inicioButton, gbc);

        gbc.gridx = 1;
        topPanel.add(personalButton, gbc);

        gbc.gridx = 2;
        topPanel.add(anadirButton, gbc);

        gbc.gridx = 3;
        topPanel.add(nuevaPublicacionButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        JPanel publicacionesPanel = new JPanel();
        publicacionesPanel.setLayout(new BoxLayout(publicacionesPanel, BoxLayout.Y_AXIS));
        publicacionesPanel.setBackground(new Color(211, 205, 192));

        List<Publicacion> publicaciones = controladorDatos.obtenerPublicaciones(usuario_actual.getUsuario());
        for (Publicacion publicacion : publicaciones) {
            JPanel publicacionPanel = new JPanel();
            publicacionPanel.setLayout(new BorderLayout());
            publicacionPanel.setBorder(BorderFactory.createTitledBorder(publicacion.getTitulo()));
            publicacionPanel.setBackground(new Color(211, 205, 192));

            JTextArea descripcionArea = new JTextArea(publicacion.getDescripcion());
            descripcionArea.setFont(new Font("Arial", Font.PLAIN, 18));
            descripcionArea.setLineWrap(true);
            descripcionArea.setWrapStyleWord(true);
            descripcionArea.setEditable(false);
            publicacionPanel.add(new JScrollPane(descripcionArea), BorderLayout.CENTER);

            JButton eliminarButton = new JButton("Eliminar");
            eliminarButton.setFont(new Font("Arial", Font.PLAIN, 18));
            eliminarButton.setBackground(new Color(174, 101, 7));
            eliminarButton.setForeground(Color.WHITE);
            eliminarButton.setPreferredSize(new Dimension(150, 50));
            eliminarButton.setMargin(new Insets(10, 20, 10, 20));
            eliminarButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    if (addPublicacion.eliminarPublicacion(publicacion)) {
                        JOptionPane.showMessageDialog(null, "Publicación eliminada correctamente");
                        publicacionesPanel.remove(publicacionPanel);
                        publicacionesPanel.revalidate();
                        publicacionesPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar la publicación");
                    }
                }
            });
            publicacionPanel.add(eliminarButton, BorderLayout.SOUTH);

            publicacionesPanel.add(publicacionPanel);
        }

        add(new JScrollPane(publicacionesPanel), BorderLayout.CENTER);

        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual).setVisible(true);
        });
        personalButton.addActionListener(e -> {

            dispose();
            new Personal_Empresa(usuario_actual).setVisible(true);

        });
        anadirButton.addActionListener(e -> {
            dispose();
            new Add_Empresa(usuario_actual).setVisible(true);
                });

        nuevaPublicacionButton.addActionListener(e -> {
            String titulo = JOptionPane.showInputDialog("Ingrese el título de la publicación:");
            String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la publicación:");
            if (titulo != null && descripcion != null) {
                Publicacion nuevaPublicacion = new Publicacion(titulo, descripcion, 0, usuario_actual.getId_usuario(), usuario_actual.getUsuario());
                if (addPublicacion.crearPublicacion(nuevaPublicacion)) {
                    JOptionPane.showMessageDialog(null, Mensajes.getMensaje(Mensajes.PUBLICACION_ANADIDO));
                    publicacionesPanel.add(new JPanel() {{
                        setLayout(new BorderLayout());
                        setBorder(BorderFactory.createTitledBorder(nuevaPublicacion.getTitulo()));
                        setBackground(new Color(211, 205, 192));
                        add(new JScrollPane(new JTextArea(nuevaPublicacion.getDescripcion()) {{
                            setFont(new Font("Arial", Font.PLAIN, 18));
                            setLineWrap(true);
                            setWrapStyleWord(true);
                            setEditable(false);
                        }}), BorderLayout.CENTER);
                        add(new JButton("Eliminar") {{
                            setFont(new Font("Arial", Font.PLAIN, 18));
                            setBackground(new Color(174, 101, 7));
                            setForeground(Color.WHITE);
                            setPreferredSize(new Dimension(150, 50));
                            setMargin(new Insets(10, 20, 10, 20));
                            addActionListener(e -> {
                                int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                                if (response == JOptionPane.YES_OPTION) {
                                    if (addPublicacion.eliminarPublicacion(nuevaPublicacion)) {
                                        JOptionPane.showMessageDialog(null, Mensajes.getMensaje(Mensajes.PUBLICACION_RETIRADA));
                                        publicacionesPanel.remove(this.getParent());
                                        publicacionesPanel.revalidate();
                                        publicacionesPanel.repaint();
                                    } else {
                                        JOptionPane.showMessageDialog(null, Mensajes.getMensaje(Mensajes.ERROR_ELIMINAR_PUBLICACION));
                                    }
                                }
                            });
                        }}, BorderLayout.SOUTH);
                    }});
                    publicacionesPanel.revalidate();
                    publicacionesPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, Mensajes.getMensaje(Mensajes.ERROR_GUARDAR_PUBLICACION));
                }
            }
        });
    }
}