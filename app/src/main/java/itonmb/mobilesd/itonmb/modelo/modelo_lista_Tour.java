package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_Tour {

    public int idTour ;
    public String nombreTour ;
    public int idEquipoBase ;
    public int idBrazaleteAd ;
    public int idBrazaleteNino ;
    public int idBrazaleteComplemento;
    public double precioAdulto;
    public double precioNino;
    public double precioInfante;


    public modelo_lista_Tour(int idTour, String nombreTour, int idEquipoBase, int idBrazaleteAd, int idBrazaleteNino, int idBrazaleteComplemento, double precioAdulto, double precioNino, double precioInfante)
    {
        this.idTour = idTour;
        this.nombreTour = nombreTour;
        this.idEquipoBase = idEquipoBase;
        this.idBrazaleteAd = idBrazaleteAd;
        this.idBrazaleteNino = idBrazaleteNino;
        this.idBrazaleteComplemento = idBrazaleteComplemento;
        this.precioAdulto = precioAdulto;
        this.precioNino = precioNino;
        this.precioInfante = precioInfante;
    }




}