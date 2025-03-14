package VIEW.UTIL;

import VIEW.INICIO.Inicio_Vista;

import javax.swing.*;
import java.awt.*;

public class CustomListCellRenderer implements ListCellRenderer<Inicio_Vista> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Inicio_Vista> list, Inicio_Vista value, int index, boolean isSelected, boolean cellHasFocus) {
        // Set the preferred size of the cell based on the component's preferred size
        value.setPreferredSize(new Dimension(list.getWidth(), value.getPreferredSize().height));
        return value;
    }
}