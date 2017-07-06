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

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_Tour;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbrazaletes;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;

public class WsProcesos {
    DBhelper dbs ;

    public boolean  WSObtenerReservas (Context context, String fecha) {

        dbs = new DBhelper(context);

            boolean resul = true;
            ArrayList<modelo_lista_orden> datos = new ArrayList<>();

            final String NAMESPACE = "http://sql2mobilesd.cloudapp.net/";
            final String URL="http://sql2mobilesd.cloudapp.net/WSAlbatros/WSAlbatros.asmx";
            final String METHOD_NAME = "ObtenerReservas";
            final String SOAP_ACTION = "http://sql2mobilesd.cloudapp.net/ObtenerReservas";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        String dd = "2017/07/15";
            request.addProperty("fecha",dd);

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

}
