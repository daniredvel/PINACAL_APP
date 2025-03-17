package PRUEBAS;

import javax.swing.*;

public class p1 extends JFrame {

    public p1() {}
    public static void main(String[] args) {
        System.out.println(formatoDireccion("Jeronimo Muñoz","7 2","Parque Tecnologico", "Boecillo", "Valladolid", "47151", "España"));
    }
        public static String formatoDireccion(String calle, String numero, String localidad, String municipio, String provincia, String codigoPostal, String pais){
            return "C/"+ formato(calle) + ", Nº " + numero.replaceAll("\\s+", "") + ", " + formato(localidad) + ", " + formato(municipio) + ", " + formato(provincia) + ", C.P.: " + codigoPostal + ", " + formato(pais);

        }
    private static String formato(String palabra){
        return palabra.substring(0,1).toUpperCase()+palabra.substring(1).toLowerCase();
    }
}