package itonmb.mobilesd.itonmb.Utils;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;

/**
 * Created by Conrado_Mobiles on 08/06/2017.
 */

public  class Global {

    public static ArrayList<modelo_lista_orden> data;
    public static String cupon;
    public static String usuario="Conrado";
    public static String user_id="E1385056-E74F-4EBA-A596-6FC7697A2254";
    public static String usuario_nombre;
    public static String consulta_where=" where status not in (14,2) and (adulto+menor+infante)!=0";
    public static int orden_de_servicio;
    public static int reservadetalle;
    public static int importe_muelle=12;
    public static int id_caja;
    public static int id_sesion;
    public static int status_caja=0;
    public static double TC=17.81;
    public static String nombre_caja="";

}
