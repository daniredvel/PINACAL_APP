package MODEL.ELIMINADO;

import MODEL.Publicacion;

import java.sql.Timestamp;

public class Publicacion_eliminada extends Eliminado {
    Publicacion publicacion;
    public Publicacion_eliminada(String mensaje, Publicacion publicacion) {
        this.setFecha(new Timestamp(System.currentTimeMillis()));
        this.setTipo(Eliminado.getTipos()[1]);
        this.setMensaje(mensaje);
        this.publicacion=publicacion;
    }
}
