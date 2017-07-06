package itonmb.mobilesd.itonmb.modelo;

/**
 * Created by Conrado on 09/05/2017.
 */

public class modelo_lista_dbrazaletes {


    public int idBrazalete;
    public int folio;
    public String tipo;
    public String color;



    public modelo_lista_dbrazaletes( int idBrazalete,int folio, String tipo,String color) {

        this.folio = folio;
        this.tipo = tipo;
        this.color = color;
        this.idBrazalete = idBrazalete;

    }

}