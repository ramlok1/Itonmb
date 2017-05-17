package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_orden {
    public String cupon;
    public String agencia;
    public String tour;
    public int adulto;
    public int nino;
    public int infante;
    public String nombre;
    public String hotel;
    public String habi;
    public double importe;

    public modelo_lista_orden(String cupon,String agencia,String tour, int adulto, int nino, int infante, String nombre, String hotel, String habi, double importe) {
        this.cupon = cupon;
        this.agencia = agencia;
        this.tour = tour;
        this.adulto = adulto;
        this.nino = nino;
        this.infante = infante;
        this.nombre = nombre;
        this.hotel = hotel;
        this.habi = habi;
        this.importe = importe;

    }


}
