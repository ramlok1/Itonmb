package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_formas_de_pago {

    public String forma_papgo;
    public String moneda;
    public double monto_moneda;
    public double monto_mn;



    public modelo_lista_formas_de_pago(String forma_papgo, String moneda, double monto_moneda, double monto_mn ) {

        this.forma_papgo = forma_papgo;
        this.moneda = moneda;
        this.monto_moneda = monto_moneda;
        this.monto_mn = monto_mn;


    }


}