package MODEL.ELIMINADO;

import MODEL.Usuario;

import java.sql.Timestamp;

public class Usuario_eliminado extends Eliminado {
    Usuario usuario;
    public Usuario_eliminado(String mensaje, Usuario usuario) {
        this.setFecha(new Timestamp(System.currentTimeMillis()));
        this.setTipo(Eliminado.getTipos()[1]);
        this.setMensaje(mensaje);
        this.usuario=usuario;
    }
}
