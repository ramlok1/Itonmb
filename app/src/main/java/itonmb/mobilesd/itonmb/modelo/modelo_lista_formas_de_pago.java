package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_formas_de_pago {

    public int id_fp;
    public String forma_pago;
    public String moneda;
    public double monto_moneda;
    public double monto_mn;



    public modelo_lista_formas_de_pago(int id_fp,String forma_pago, String moneda, double monto_moneda, double monto_mn ) {

        this.id_fp=id_fp;
        this.forma_pago = forma_pago;
        this.moneda = moneda;
        this.monto_moneda = monto_moneda;
        this.monto_mn = monto_mn;


    }


}