package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_orden {
    public String cupon;
    public String agencia;
    public String producto;
    public int adulto;
    public int menor;
    public int infante;
    public String nombre;
    public String hotel;
    public String habi;
    public double importe;

    public modelo_lista_orden(String cupon,String agencia,String producto, int adulto, int menor, int infante, String nombre, String hotel, String habi, double importe) {
        this.cupon = cupon;
        this.agencia = agencia;
        this.producto = producto;
        this.adulto = adulto;
        this.menor = menor;
        this.infante = infante;
        this.nombre = nombre;
        this.hotel = hotel;
        this.habi = habi;
        this.importe = importe;

    }


}
