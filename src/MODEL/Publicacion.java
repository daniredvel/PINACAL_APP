package MODEL;

import java.sql.Timestamp;
import java.util.Date;

public class Publicacion {
    //Tipo de publicaciones
    //Ofertas de trabajo, maquinaria...
    //Demandas de trabajo, maquinaria...
    private static final String [] tipos = {"OFERTA", "DEMANDA"};

    private int id_publicacion;
    private String titulo;
    private String descripcion;
    private Date fecha_publicacion;
    private String tipo;
    private int id_usuario;
    private String usuario;


    //CONSTRUCTORES

    //Constructor por defecto
    public Publicacion(){}

    //Constructor para crear la publicacion por el usuario
    public Publicacion(
            String  titulo,
            String  descripcion,
            String  tipo,
            int     id_usuario){
        this.titulo             = titulo;
        this.descripcion        = descripcion;
        this.fecha_publicacion  = new Timestamp(System.currentTimeMillis());
        this.tipo               = tipo.toUpperCase();
        this.id_usuario         = id_usuario;
    }

    //Constructor para leer publicaciones de la base de datos
    public Publicacion(
            int     id_publicacion,
            String  titulo,
            String  descripcion,
            Timestamp fecha_publicacion,
            String     tipo,
            int     id_usuario,
            String usuario
    ){
        this.id_publicacion     = id_publicacion;
        this.titulo             = titulo;
        this.descripcion        = descripcion;
        this.fecha_publicacion  = fecha_publicacion;
        this.tipo               = tipo.toUpperCase();
        this.id_usuario         = id_usuario;
        this.usuario            = usuario;
    }
    //GETTER

    public int getId_publicacion(){
        return id_publicacion;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public Date getFecha_publicacion(){
        return fecha_publicacion;
    }

    public String getTipo(){
        return tipo;
    }

    public int getId_usuario(){
        return id_usuario;
    }

    public String getUsuario(){
        return usuario;
    }

    //SETTER

    public void setId_publicacion(int id_publicacion){
        this.id_publicacion = id_publicacion;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setFecha_publicacion(Date fecha_publicacion){
        this.fecha_publicacion = fecha_publicacion;
    }

    public void setId_usuario(int id_usuario){
        this.id_usuario = id_usuario;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    //METODOS

    //METODO QUE INDICA EL TIPO DE PUBLICACION A PARTIR DE UN INDICE

    public static String indicarTipo(int indice){
        return tipos[indice];
    }

}