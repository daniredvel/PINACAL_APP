package MAIN;

import MODEL.Usuario;
import VIEW.INICIO.Inicio_Vista;
import VIEW.INICIO_SESION.InicioSesion_Vista;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Llamamos a la vista de inicio de sesion para iniciar el programa
        SwingUtilities.invokeLater(() -> new InicioSesion_Vista().setVisible(true));
    }
}