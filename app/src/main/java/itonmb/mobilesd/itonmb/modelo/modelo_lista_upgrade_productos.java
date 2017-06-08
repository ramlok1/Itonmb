package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_upgrade_productos {

    public int id_tmp;
    public String descripcion;
    public int adulto;
    public int nino;
    public int infante;
    public int importe;



    public modelo_lista_upgrade_productos(int id_tmp,String descripcion, int adulto, int nino, int infante, int importe ) {

        this.id_tmp = id_tmp;
        this.descripcion = descripcion;
        this.adulto = adulto;
        this.nino = nino;
        this.infante = infante;
        this.importe = importe;


    }


}