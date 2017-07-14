package itonmb.mobilesd.itonmb.Utils;

/**
 * Created by Conrado_Mobiles on 05/07/2017.
 */
import android.content.Context;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_Caja;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_Tour;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_TourUpgrade;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbarcos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbrazaletes;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_tipoOperacionCaja;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_tour_barcos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_ws_brazalete;

public class WsProcesos {
    DBhelper dbs ;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date();


    public boolean  WSObtenerReservas (Context context, String fecha) {

        dbs = new DBhelper(context);

            boolean resul = true;
            ArrayList<modelo_lista_orden> datos = new ArrayList<>();

            final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
            final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
            final String METHOD_NAME = "ObtenerReservas";
            final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerReservas";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


            request.addProperty("fecha",dateFormat2.format(date));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try
            {
                transporte.call(SOAP_ACTION, envelope);

                SoapObject resSoap =(SoapObject)envelope.getResponse();

                for (int i = 0; i <  resSoap.getPropertyCount(); i++)
                {
                    SoapObject ic = (SoapObject)resSoap.getProperty(i);


                    int idOpBoat=Integer.parseInt(ic.getProperty("idOpBoat").toString());
                    int idReservaDetalle=Integer.parseInt(ic.getProperty("idReservaDetalle").toString());
                    String cupon=ic.getProperty("numCupon").toString();
                    String agencia=ic.getProperty("nombreAgencia").toString();
                    int id_prodcuto_padre=Integer.parseInt(ic.getProperty("idTour").toString());
                    String producto1=ic.getProperty("nombreTour").toString();
                    int adulto=Integer.parseInt(ic.getProperty("numAdulto").toString());
                    int menor=Integer.parseInt(ic.getProperty("numNinio").toString());
                    int infante=Integer.parseInt(ic.getProperty("numInfante").toString());
                    String nombre_cliente=ic.getProperty("nombreHuesped").toString();
                    String hotel=ic.getProperty("nombreHotel").toString();
                    String habi=ic.getProperty("habitacion").toString();
                    String obs=ic.getProperty("Observacion").toString();
                    int idioma=Integer.parseInt(ic.getProperty("idIdioma").toString());
                    int idioma_icono=Integer.parseInt(ic.getProperty("imagen").toString());
                    int status=Integer.parseInt(ic.getProperty("status").toString());

                    datos.add(new modelo_lista_orden(0,idOpBoat,idReservaDetalle,cupon,agencia,id_prodcuto_padre,producto1,adulto,menor,infante,nombre_cliente,hotel,habi,obs,idioma,idioma_icono,status));
                }

                dbs.ws_Inserta_datos_reservas(datos);
            }
            catch (Exception e)
            {

                resul = false;

            }

            return resul;




    }

    public boolean  WSObtenerBrazaletes (Context context) {

        dbs = new DBhelper(context);

        boolean resul = true;
        ArrayList<modelo_lista_dbrazaletes> datos = new ArrayList<>();

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "ObtenerBrazaletes";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerBrazaletes";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        request.addProperty("userid",Global.user_id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();

            for (int i = 0; i <  resSoap.getPropertyCount(); i++)
            {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);


                int idBrazalete=Integer.parseInt(ic.getProperty("idBrazalete").toString());
                int folio=Integer.parseInt(ic.getProperty("folio").toString());
                String tipo=ic.getProperty("tipo").toString();
                String Hex=ic.getProperty("Hex").toString();


                datos.add(new modelo_lista_dbrazaletes(idBrazalete,folio,tipo,Hex));
            }

            dbs.ws_Inserta_datos_brazaletes(datos);
        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSObtenerTour (Context context) {

        dbs = new DBhelper(context);

        boolean resul = true;
        ArrayList<modelo_lista_Tour> datos = new ArrayList<>();

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "ObtenerTour";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerTour";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();

            for (int i = 0; i <  resSoap.getPropertyCount(); i++)
            {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);


                int idTour=Integer.parseInt(ic.getProperty("idTour").toString());
                String nombreTour=ic.getProperty("nombreTour").toString();
                int idEquipoBase=Integer.parseInt(ic.getProperty("idEquipoBase").toString());
                int idBrazaleteAd=Integer.parseInt(ic.getProperty("idBrazaleteAd").toString());
                int idBrazaleteNino=Integer.parseInt(ic.getProperty("idBrazaleteNino").toString());
                int idBrazaleteComplemento=Integer.parseInt(ic.getProperty("idBrazaleteComplemento").toString());
                double precioAdulto=Double.parseDouble(ic.getProperty("precioAdulto").toString());
                double precioNino=Double.parseDouble(ic.getProperty("precioNino").toString());
                double precioInfante=Double.parseDouble(ic.getProperty("precioInfante").toString());




                datos.add(new modelo_lista_Tour(idTour,nombreTour,idEquipoBase,idBrazaleteAd,idBrazaleteNino,idBrazaleteComplemento,precioAdulto,precioNino,precioInfante));
            }

            dbs.ws_Inserta_datos_Tour(datos);
        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSObtenerTourUpgrade (Context context) {

        dbs = new DBhelper(context);

        boolean resul = true;
        ArrayList<modelo_lista_TourUpgrade> datos = new ArrayList<>();

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "ObtenerTourUpgrade";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerTourUpgrade";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();

            for (int i = 0; i <  resSoap.getPropertyCount(); i++)
            {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);


                int idTour=Integer.parseInt(ic.getProperty("idTour").toString());
                int idTourCombinacion=Integer.parseInt(ic.getProperty("idTourCombinacion").toString());





                datos.add(new modelo_lista_TourUpgrade(idTour,idTourCombinacion));
            }

            dbs.ws_Inserta_datos_TourUpgrade(datos);
        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSObtenerEquipo_Base (Context context, String fecha, int upd_param) {

        dbs = new DBhelper(context);

        boolean resul = true;
        ArrayList<modelo_lista_dbarcos> datos = new ArrayList<>();

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "ObtenerEquipo_Base";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerEquipo_Base";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("fecha",dateFormat2.format(date));

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();

            for (int i = 0; i <  resSoap.getPropertyCount(); i++)
            {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);


                int idOpBoat=Integer.parseInt(ic.getProperty("idOpBoat").toString());
                int idEquipoBase=Integer.parseInt(ic.getProperty("idEquipoBase").toString());
                String nombreTourEquipoBase=ic.getProperty("nombreEquipoBase").toString();
                int capacidad=Integer.parseInt(ic.getProperty("capacidad").toString());
                int abordados=Integer.parseInt(ic.getProperty("abordados").toString());


                datos.add(new modelo_lista_dbarcos(idOpBoat,idEquipoBase,nombreTourEquipoBase,capacidad,abordados));
            }

            if(upd_param==1) {
                dbs.ws_Update_datos_Equipo_Base(datos);
            }else{
                dbs.ws_Inserta_datos_Equipo_Base(datos);
            }
        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSObtenerTour_Equipo_Base (Context context) {

        dbs = new DBhelper(context);

        boolean resul = true;
        ArrayList<modelo_lista_tour_barcos> datos = new ArrayList<>();

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "ObtenerTour_Equipo_Base";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerTour_Equipo_Base";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();

            for (int i = 0; i <  resSoap.getPropertyCount(); i++)
            {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);


                int idEquipoBase=Integer.parseInt(ic.getProperty("idEquipoBase").toString());
                int idTour=Integer.parseInt(ic.getProperty("idTour").toString());



                datos.add(new modelo_lista_tour_barcos(idEquipoBase,idTour));
            }
            dbs.ws_Inserta_datos_Equipo_Base_Tour(datos);

        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSValida_Capacidad_Bote (String fecha, int id_barco, int pax) {



        boolean resul = false;
        ArrayList<modelo_lista_dbarcos> datos = new ArrayList<>();

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Valida_Capacidad_Bote";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Valida_Capacidad_Bote";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("fecha",dateFormat2
                .format(date));
        request.addProperty("idtourequipobase",id_barco);
        request.addProperty("pax",pax);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

             resul = new Boolean(resSoap.toString());


        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public int  WSInserta_Personalbrazalete ( int cant) {



        int resul ;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Inserta_PersonalBrazalete";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Inserta_PersonalBrazalete";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("cant",cant);
        request.addProperty("fecha",dateFormat.format(date));
        request.addProperty("idreservadetalle",Global.reservadetalle);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

            resul = new Integer(resSoap.toString());


        }
        catch (Exception e)
        {

            resul = 0;

        }

        return resul;




    }

    public boolean  WSUpdate_Brazalete (int idBrazalete, int folio, int idPersonal) {



        boolean resul = false;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Update_brazalete_abordar";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Update_brazalete_abordar";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("braza",idBrazalete);
        request.addProperty("folio",folio);
        request.addProperty("idPersonaldetalle",idPersonal);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

            resul = new Boolean(resSoap.toString());


        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSinserta_detalleOpBoat_Abordar ( int adulto , int menor , int infante  ) {



        boolean resul = false;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Inserta_Abordar_DetalleOpBoat";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Inserta_Abordar_DetalleOpBoat";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("opboat", Global.orden_de_servicio);
        request.addProperty("reservadetalle",Global.reservadetalle);
        request.addProperty("adulto",adulto);
        request.addProperty("menor",menor);
        request.addProperty("infante",infante);
        request.addProperty("fecha",dateFormat.format(date));
        request.addProperty("user",Global.user_id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

            resul = new Boolean(resSoap.toString());


        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSObtenerCajas (Context context) {

        dbs = new DBhelper(context);

        boolean resul = true;
        ArrayList<modelo_lista_Caja> datos = new ArrayList<>();

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "ObtenerCajas";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerCajas";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();

            for (int i = 0; i <  resSoap.getPropertyCount(); i++)
            {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);

                int idCaja=Integer.parseInt(ic.getProperty("idCaja").toString());
                String nombreCaja=ic.getProperty("nombreCaja").toString();
                int status=Integer.parseInt(ic.getProperty("status").toString());
                String userid=ic.getProperty("userid").toString();

                datos.add(new modelo_lista_Caja(idCaja,nombreCaja,status,userid));
            }

            dbs.ws_Inserta_Cajas(datos);
        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSObtenertipoOperacionCaja (Context context) {

        dbs = new DBhelper(context);

        boolean resul = true;
        ArrayList<modelo_lista_tipoOperacionCaja> datos = new ArrayList<>();

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "ObtenerTipoOperacion_Caja";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerTipoOperacion_Caja";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();

            for (int i = 0; i <  resSoap.getPropertyCount(); i++)
            {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);


                int idTipoOperacionCaja=Integer.parseInt(ic.getProperty("idTipoOperacionCaja").toString());
                String nomTipoOperacionCaja=ic.getProperty("nomTipoOperacionCaja").toString();
                String tipo=ic.getProperty("tipo").toString();

                datos.add(new modelo_lista_tipoOperacionCaja(idTipoOperacionCaja,nomTipoOperacionCaja,tipo));
            }

            dbs.ws_Inserta_tipoOperacionCaja(datos);
        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public int  WSAbrir_Caja () {

        int sesion = 0;


        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Abrir_Caja";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Abrir_Caja";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("idCaja",Global.id_caja);
        request.addProperty("userid",Global.user_id);
        request.addProperty("fecha",dateFormat.format(date));

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();
            sesion = new Integer(resSoap.toString());
        }
        catch (Exception e)
        {
            sesion = 0;
        }
        return sesion;

    }

    public boolean  WSCerrar_Caja () {



        boolean resul = false;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Cerrar_Caja";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Cerrar_Caja";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("idCaja",Global.id_caja);
        request.addProperty("userid",Global.user_id);
        request.addProperty("fecha",dateFormat.format(date));
        request.addProperty("idSesion",Global.id_sesion);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            /*SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

            resul = new Boolean(resSoap.toString());*/


        }
       catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSArqueo_Abrir_Caja (int[] billete, int [] moneda, int pesos, int usd) {



        boolean resul = false;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Inserta_Arqueo_abrir_caja";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Inserta_Arqueo_abrir_caja";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("idSesion",Global.id_sesion);
        request.addProperty("idTipoArqueo",1);
        request.addProperty("d1000",billete[0]);
        request.addProperty("d500",billete[1]);
        request.addProperty("d200",billete[2]);
        request.addProperty("d100",billete[3]);
        request.addProperty("d50",billete[4]);
        request.addProperty("d20",billete[5]);
        request.addProperty("d10",moneda[0]);
        request.addProperty("d5",moneda[1]);
        request.addProperty("d2",moneda[2]);
        request.addProperty("d05",moneda[3]);
        request.addProperty("d02",moneda[4]);
        request.addProperty("d01",moneda[5]);
        request.addProperty("pesos",pesos);
        request.addProperty("usd",usd);
        request.addProperty("userid",Global.user_id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
           // SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();




        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSArqueo_Cerrar_Caja (int[] billete, int [] moneda, String pesos, String usd,String venta,String cajaInicial,String cajaLibro, String diferencia) {



        boolean resul = false;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Inserta_Arqueo_cerrar_caja";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Inserta_Arqueo_cerrar_caja";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("idSesion",Global.id_sesion);
        request.addProperty("idTipoArqueo",1);
        request.addProperty("d1000",billete[0]);
        request.addProperty("d500",billete[1]);
        request.addProperty("d200",billete[2]);
        request.addProperty("d100",billete[3]);
        request.addProperty("d50",billete[4]);
        request.addProperty("d20",billete[5]);
        request.addProperty("d10",moneda[0]);
        request.addProperty("d5",moneda[1]);
        request.addProperty("d2",moneda[2]);
        request.addProperty("d05",moneda[3]);
        request.addProperty("d02",moneda[4]);
        request.addProperty("d01",moneda[5]);
        request.addProperty("pesos",pesos);
        request.addProperty("usd",usd);
        request.addProperty("venta",venta);
        request.addProperty("cajaInicial",cajaInicial);
        request.addProperty("cajaFinal",usd);
        request.addProperty("cajaLibro",cajaLibro);
        request.addProperty("diferencia",diferencia);
        request.addProperty("userid",Global.user_id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            // SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();




        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public boolean  WSInserta_detalle_caja (int OPcaja, String monto, int idmoneda,String TC,String obs) {



        boolean resul = false;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Inserta_detalle_caja";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Inserta_detalle_caja";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("idSesion",Global.id_sesion);
        request.addProperty("idTipoOperacionCaja",OPcaja);
        request.addProperty("Monto",monto);
        request.addProperty("FechaAlta",dateFormat.format(date));
        request.addProperty("idMoneda",idmoneda);
        request.addProperty("TipoCambio",TC);
        request.addProperty("observaciones",obs);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            /*SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

            resul = new Boolean(resSoap.toString());*/


        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

    public int  WSInserta_venta ( String monto, int muelle,int desc) {



        int resul;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Inserta_Venta";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Inserta_Venta";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("idReservaDetalle",Global.reservadetalle);
        request.addProperty("Monto",monto);
        request.addProperty("muelle",muelle);
        request.addProperty("descuento",desc);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

            resul = new Integer(resSoap.toString());


        }
        catch (Exception e)
        {
            resul = 0;
        }

        return resul;




    }

    public void  WSInserta_pago ( String monto, int idVenta,int idmoneda,int idfp) {



        int resul;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Inserta_Pago";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Inserta_Pago";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("fechaPago",dateFormat.format(date));
        request.addProperty("idVenta",idVenta);
        request.addProperty("Monto",monto);
        request.addProperty("idMoneda",idmoneda);
        request.addProperty("idFormaPago",idfp);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
          /*  SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

            resul = new Integer(resSoap.toString());*/


        }
        catch (Exception e)
        {
            resul = 0;
        }

    }

    public boolean  WSUpgrade (int idTour, int numAdulto, int numNinio, int numInfante) {




        boolean resul = false;

        final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
        final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
        final String METHOD_NAME = "Upgrade";
        final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/Upgrade";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("idTour",idTour);
        request.addProperty("numAdulto",numAdulto);
        request.addProperty("numNinio",numNinio);
        request.addProperty("numInfante",numInfante);
        request.addProperty("fechaAlta",dateFormat.format(date));
        request.addProperty("userId",Global.user_id);
        request.addProperty("reservaDetalle",Global.reservadetalle);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);
            /*SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

            resul = new Boolean(resSoap.toString());*/


        }
        catch (Exception e)
        {

            resul = false;

        }

        return resul;




    }

}
