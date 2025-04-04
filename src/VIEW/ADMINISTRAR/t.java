/*
package VIEW.ADMINISTRAR;

import CONTROLLER.CRUD.PUBLICACION.EliminarPublicacion;
import CONTROLLER.CRUD.USER.ActualizarUsuario;
import CONTROLLER.CRUD.USER.EliminarUsuario;
import CONTROLLER.CRUD.USER.LeerUsuario;
import CONTROLLER.ControladorDatos;
import MODEL.Publicacion;
import MODEL.UTIL.Mensajes;
import MODEL.Usuario;
import VIEW.ADD.Add_Empresa;
import VIEW.ERROR.Error_INICIAR_BD;
import VIEW.INICIO.Inicio_Vista;
import VIEW.PERSONAL.Personal_Empresa;
import VIEW.RES.Rutas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static VIEW.INICIO.Inicio_Vista.LOGGER;

public class Administar_Vista extends JFrame {
    private List<Publicacion> publicaciones;
    private int currentIndex = 0;
    private final JTextArea publicacionArea;
    private final JTextField justificacionField;
    private final JRadioButton denegadaButton;
    private final ButtonGroup group;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private static Usuario usuario_actual = null;
    public static Connection conn = null;

    public Administar_Vista(Usuario usuario_actual, Connection conexion) {
        Administar_Vista.usuario_actual = usuario_actual;

        LOGGER.log(Level.INFO, "Iniciando vista de administrar");
        Administar_Vista.conn = conexion;

        if (conn == null) {
            LOGGER.log(Level.SEVERE, "Conexión nula");
            SwingUtilities.invokeLater(() -> new Error_INICIAR_BD().setVisible(true));
        }

        assert conn != null;

        setIconImage(Rutas.getImage(Rutas.ICONO));
        setTitle("Administrar Publicaciones y Usuarios");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(211, 205, 192));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(new Color(211, 205, 192));

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        JButton inicioButton = new JButton("Inicio");
        inicioButton.setFont(buttonFont);
        inicioButton.setBackground(new Color(174, 101, 7));
        inicioButton.setForeground(Color.WHITE);
        inicioButton.setPreferredSize(new Dimension(150, 50));
        inicioButton.setMargin(new Insets(10, 20, 10, 20));

        JButton personalButton = new JButton("Personal");
        personalButton.setFont(buttonFont);
        personalButton.setBackground(new Color(174, 101, 7));
        personalButton.setForeground(Color.WHITE);
        personalButton.setPreferredSize(new Dimension(150, 50));
        personalButton.setMargin(new Insets(10, 20, 10, 20));

        JButton anadirButton = new JButton("Mis Publicaciones");
        anadirButton.setFont(buttonFont);
        anadirButton.setBackground(new Color(174, 101, 7));
        anadirButton.setForeground(Color.WHITE);
        anadirButton.setMargin(new Insets(10, 20, 10, 20));
        anadirButton.setPreferredSize(null);

        JButton adminButton = new JButton("Administrador");
        adminButton.setFont(buttonFont);
        adminButton.setBackground(new Color(174, 101, 7));
        adminButton.setForeground(Color.WHITE);
        adminButton.setMargin(new Insets(10, 20, 10, 20));
        adminButton.setPreferredSize(null);

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
        topPanel.add(adminButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(211, 205, 192));

        publicacionArea = new JTextArea(10, 50);
        publicacionArea.setFont(new Font("Arial", Font.PLAIN, 18));
        publicacionArea.setEditable(false);
        publicacionArea.setPreferredSize(new Dimension(600, 200));

        justificacionField = new JTextField(50);
        justificacionField.setFont(new Font("Arial", Font.PLAIN, 18));
        justificacionField.setEnabled(false);
        justificacionField.setPreferredSize(new Dimension(600, 50));

        JRadioButton aceptadaButton = new JRadioButton("Aceptada");
        aceptadaButton.setFont(new Font("Arial", Font.PLAIN, 18));
        aceptadaButton.setBackground(new Color(211, 205, 192));

        denegadaButton = new JRadioButton("Denegada");
        denegadaButton.setFont(new Font("Arial", Font.PLAIN, 18));
        denegadaButton.setBackground(new Color(211, 205, 192));

        group = new ButtonGroup();
        group.add(aceptadaButton);
        group.add(denegadaButton);

        aceptadaButton.addActionListener(e -> justificacionField.setEnabled(false));
        denegadaButton.addActionListener(e -> justificacionField.setEnabled(true));

        justificacionField.setText("Escribe la justificación aquí...");
        justificacionField.setForeground(Color.GRAY);

        justificacionField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (justificacionField.getText().equals("Escribe la justificación aquí...")) {
                    justificacionField.setText("");
                    justificacionField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (justificacionField.getText().isEmpty()) {
                    justificacionField.setText("Escribe la justificación aquí...");
                    justificacionField.setForeground(Color.GRAY);
                }
            }
        });

        JButton siguienteButton = new JButton("Siguiente");
        siguienteButton.setFont(new Font("Arial", Font.PLAIN, 18));
        siguienteButton.setBackground(new Color(174, 101, 7));
        siguienteButton.setForeground(Color.WHITE);
        siguienteButton.addActionListener(e -> {
            gestionarPublicacion();
            currentIndex++;
            if (currentIndex < publicaciones.size()) {
                mostrarPublicacion();
            } else {
                JOptionPane.showMessageDialog(this, "No hay más publicaciones.");
                dispose();
            }
        });

        JButton anteriorButton = new JButton("Anterior");
        anteriorButton.setFont(new Font("Arial", Font.PLAIN, 18));
        anteriorButton.setBackground(new Color(174, 101, 7));
        anteriorButton.setForeground(Color.WHITE);
        anteriorButton.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                mostrarPublicacion();
            } else {
                JOptionPane.showMessageDialog(this, "No hay publicaciones anteriores.");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(publicacionArea, gbc);

        gbc.gridy = 1;
        centerPanel.add(justificacionField, gbc);

        gbc.gridy = 2;
        centerPanel.add(aceptadaButton, gbc);

        gbc.gridy = 3;
        centerPanel.add(denegadaButton, gbc);

        gbc.gridy = 4;
        centerPanel.add(anteriorButton, gbc);

        gbc.gridy = 5;
        centerPanel.add(siguienteButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Usuario", "Email", "Dirección", "Teléfono", "Tipo", "Permisos", "Acciones"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(table);
        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton modificarPermisosButton = new JButton("Modificar Permisos");
        JButton eliminarUsuarioButton = new JButton("Eliminar Usuario");
        buttonPanel.add(modificarPermisosButton);
        buttonPanel.add(eliminarUsuarioButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        cargarDatosUsuarios();

        modificarPermisosButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String usuario = (String) tableModel.getValueAt(selectedRow, 0);
                modificarPermisos(Objects.requireNonNull(LeerUsuario.leerUsuarioPorNombre(usuario)));
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un usuario para modificar los permisos.");
            }
        });

        eliminarUsuarioButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String usuario = (String) tableModel.getValueAt(selectedRow, 0);
                eliminarUsuario(Objects.requireNonNull(LeerUsuario.leerUsuarioPorNombre(usuario)));
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un usuario para eliminar.");
            }
        });

        inicioButton.addActionListener(e -> {
            dispose();
            new Inicio_Vista(usuario_actual, conn).setVisible(true);
        });
        personalButton.addActionListener(e -> {
            dispose();
            new Personal_Empresa(usuario_actual, conn).setVisible(true);
        });
        anadirButton.addActionListener(e -> {
            dispose();
            new Add_Empresa(usuario_actual, conn).setVisible(true);
        });
        adminButton.addActionListener(e -> {
            dispose();
            new Administar_Vista(usuario_actual, conn).setVisible(true);
        });

        cargarPublicaciones();
    }

    private void cargarPublicaciones() {
        publicaciones = ControladorDatos.obtenerPublicaciones(conn, true);
        if (!publicaciones.isEmpty()) {
            mostrarPublicacion();
        } else {
            JOptionPane.showMessageDialog(this, "No hay publicaciones para administrar.");
        }
    }

    private void mostrarPublicacion() {
        Publicacion publicacion = publicaciones.get(currentIndex);
        publicacionArea.setText(publicacion.getDescripcion());
        justificacionField.setText("");
        group.clearSelection();
    }

    private void gestionarPublicacion() {
        Publicacion publicacion = publicaciones.get(currentIndex);
        if (denegadaButton.isSelected()) {
            if (EliminarPublicacion.eliminarPublicacion(publicacion)) {
                JOptionPane.showMessageDialog(this, "Publicación denegada y eliminada.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la publicación.");
            }
        }
    }

    private void cargarDatosUsuarios() {
        tableModel.setRowCount(0);
        List<Usuario> usuarios = ControladorDatos.obtenerUsuarios(conn);
        for (Usuario usuario : usuarios) {
            tableModel.addRow(new Object[]{
                    usuario.getUsuario(),
                    usuario.getEmail(),
                    usuario.getDireccion(),
                    usuario.getTelefono(),
                    usuario.getIndice_tipo_usuario(),
                    usuario.getPermisos()
            });
        }
    }

    private void modificarPermisos(Usuario usuario) {
        final String[] opciones = new String[4];
        opciones[Usuario.ADMINISTRADOR] = Usuario.getTipos(Usuario.ADMINISTRADOR);
        opciones[Usuario.EMPRESA_ASOCIADA] = Usuario.getTipos(Usuario.EMPRESA_ASOCIADA);
        opciones[Usuario.EMPRESA_NO_ASOCIADA] = Usuario.getTipos(Usuario.EMPRESA_NO_ASOCIADA);
        opciones[Usuario.USUARIO] = Usuario.getTipos(Usuario.USUARIO);

        String nuevoPermiso = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione el nuevo permiso para el usuario: " + usuario.getUsuario(),
                "Modificar Permisos",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                usuario.getPermisos()
        );
        if (nuevoPermiso != null && !nuevoPermiso.equals(usuario.getPermisos())) {
            usuario.setPermisos(nuevoPermiso);
            if (ActualizarUsuario.actualizarUsuario(usuario).equalsIgnoreCase(Mensajes.getMensaje(Mensajes.USUARIO_ACTUALIZADO))) {
                if (nuevoPermiso.equalsIgnoreCase(opciones[Usuario.ADMINISTRADOR])) usuario.setIndice_tipo_usuario(Usuario.ADMINISTRADOR);
                else if (nuevoPermiso.equalsIgnoreCase(opciones[Usuario.EMPRESA_ASOCIADA])) usuario.setIndice_tipo_usuario(Usuario.EMPRESA_ASOCIADA);
                else if (nuevoPermiso.equalsIgnoreCase(opciones[Usuario.EMPRESA_NO_ASOCIADA])) usuario.setIndice_tipo_usuario(Usuario.EMPRESA_NO_ASOCIADA);
                else if (nuevoPermiso.equalsIgnoreCase(opciones[Usuario.USUARIO])) usuario.setIndice_tipo_usuario(Usuario.USUARIO);
                else usuario.setIndice_tipo_usuario(usuario.getIndice_tipo_usuario());

                JOptionPane.showMessageDialog(this, ActualizarUsuario.actualizarUsuario(usuario));
            } else {
                JOptionPane.showMessageDialog(this, Mensajes.getMensaje(Mensajes.ERROR_ACTUALIZAR_USUARIO));
            }
            cargarDatosUsuarios();
        }
    }

    private void eliminarUsuario(Usuario usuario) {
        int response = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar el usuario: " + usuario.getUsuario() + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );
        if (response == JOptionPane.YES_OPTION) {
            EliminarUsuario.eliminarUsuario(conn, usuario);
            JOptionPane.showMessageDialog(this, "Usuario: " + usuario.getUsuario() + " eliminado.");
            cargarDatosUsuarios();
        }
    }
}
*/