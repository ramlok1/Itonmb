package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado_Mobiles on 07/06/2017.
 */

public class modelo_spinner_productos_upg {
    public String descripcion;
    public int id;
    public double precio_ad;
    public double precio_me;
    public double precio_in;



    public modelo_spinner_productos_upg(String descripcion, int id, double precio_ad,double precio_me,double precio_in) {

        this.descripcion = descripcion;
        this.id = id;
        this.precio_ad = precio_ad;
        this.precio_me = precio_me;
        this.precio_in = precio_in;

    }
}
