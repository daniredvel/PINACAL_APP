package PRUEBAS;

import VIEW.INICIO.Inicio_Vista;
import VIEW.UTIL.CustomListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class p1 extends JFrame {

    public p1() {
        // Create a list of Inicio_Vista instances
        ArrayList<Inicio_Vista> items = new ArrayList<>();

        // Create a DefaultListModel to store the instances
        DefaultListModel<Inicio_Vista> listModel = new DefaultListModel<>();
        for (Inicio_Vista item : items) {
            listModel.addElement(item);
        }

        // Create a JList and set its model to the DefaultListModel
        JList<Inicio_Vista> jList = new JList<>(listModel);
        jList.setCellRenderer(new CustomListCellRenderer());

        // Add the JList to a JScrollPane to allow for scrolling
        JScrollPane scrollPane = new JScrollPane(jList);

        // Add the JScrollPane to a container such as a JFrame or JPanel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);

        // Set the JFrame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Example");
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new p1().setVisible(true);
            }
        });
    }
}