package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_dbarcos {
    public int equipobase;
    public String nombre;
    public int capacidad;
    public int abordar;
    public int idOpBoat;


    public modelo_lista_dbarcos(int idOpBoat, int equipobase, String nombre,int capacidad,int abordar) {
        this.equipobase = equipobase;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.idOpBoat = idOpBoat;
        this.abordar = abordar;

    }


}