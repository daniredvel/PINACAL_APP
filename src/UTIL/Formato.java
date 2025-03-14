package UTIL;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

//METODO para formatear la fecha de publicación
public class Formato {
    public static String toStringDate(Timestamp fecha_publicacion){
        String pattern = "kk'h':mm'm' dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(fecha_publicacion);
    }
}
