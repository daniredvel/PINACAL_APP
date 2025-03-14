package PRUEBAS;

import VIEW.UTIL.CustomListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class p1 extends JFrame {

    public p1() {}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new p1().setVisible(true);
            }
        });
    }
}