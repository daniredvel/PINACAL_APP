package VIEW.RES;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Rutas {
    //Icono
    private static Image icono = new ImageIcon(Objects.requireNonNull(Rutas.class.getResource("/VIEW/RES/icon.png"))).getImage();

    public static Image getIcono(){
        return icono;
    }
}
