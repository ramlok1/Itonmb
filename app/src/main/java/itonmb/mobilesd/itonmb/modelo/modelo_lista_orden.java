package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */
import java.io.Serializable;
public class modelo_lista_orden implements Serializable {
    public String cupon;
    public String agencia;
    public int producto_padre;
    public String producto;
    public int adulto;
    public int menor;
    public int infante;
    public String nombre;
    public String hotel;
    public String habi;
    public double importe;
    public int status;

    public modelo_lista_orden(String cupon,String agencia,int producto_padre,String producto, int adulto, int menor, int infante, String nombre, String hotel, String habi, double importe, int status) {
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
        this.producto_padre = producto_padre;
        this.status = status;

    }


}
