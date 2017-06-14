package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_dbarcos {
    public int id;
    public String nombre;
    public int capacidad;
    public int booking;
    public int abordar;


    public modelo_lista_dbarcos(int id,String nombre,int capacidad,int booking, int abordar) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.booking = booking;
        this.abordar = abordar;

    }


}