package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_upgrade_productos {

    public String descripcion;
    public int adulto;
    public int nino;
    public int infante;
    public int importe;
    public int pago;


    public modelo_lista_upgrade_productos(String descripcion, int adulto, int nino, int infante, int importe, int pago ) {

        this.descripcion = descripcion;
        this.adulto = adulto;
        this.nino = nino;
        this.infante = infante;
        this.importe = importe;
        this.pago = pago;

    }


}