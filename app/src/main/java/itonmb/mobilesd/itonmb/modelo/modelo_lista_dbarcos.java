package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_dbarcos {
    public int idtourequipobase;
    public String nombre;
    public int capacidad;
    public int abordar;
    public int idTour;


    public modelo_lista_dbarcos(int idtourequipobase, String nombre,int capacidad,int abordar, int idTour) {
        this.idtourequipobase = idtourequipobase;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.idTour = idTour;
        this.abordar = abordar;

    }


}