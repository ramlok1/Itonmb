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
import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;

public class WsProcesos {
    DBhelper dbs ;

    public boolean  WSObtenerReservas (Context context, String fecha) {

        dbs = new DBhelper(context);

            boolean resul = true;

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
                    ArrayList<modelo_lista_orden> datos = new ArrayList<>();

                    int idOpBoat=Integer.parseInt((ic.getProperty(0).toString()));
                    int idReservaDetalle=Integer.parseInt((ic.getProperty(1).toString()));
                    String cupon=ic.getProperty(2).toString();
                    String agencia=ic.getProperty(3).toString();
                    int id_prodcuto_padre=Integer.parseInt((ic.getProperty(4).toString()));
                    String producto1=ic.getProperty(5).toString();
                    int adulto=Integer.parseInt((ic.getProperty(6).toString()));
                    int menor=Integer.parseInt((ic.getProperty(7).toString()));
                    int infante=Integer.parseInt((ic.getProperty(8).toString()));
                    String nombre_cliente=ic.getProperty(9).toString();
                    String hotel=ic.getProperty(10).toString();
                    String habi=ic.getProperty(11).toString();
                    String obs=ic.getProperty(12).toString();
                    int idioma=Integer.parseInt((ic.getProperty(13).toString()));
                    int idioma_icono=Integer.parseInt((ic.getProperty(14).toString()));
                    int status=Integer.parseInt((ic.getProperty(15).toString()));

                    datos.add(new modelo_lista_orden(0,idOpBoat,idReservaDetalle,cupon,agencia,id_prodcuto_padre,producto1,adulto,menor,infante,nombre_cliente,hotel,habi,obs,idioma,idioma_icono,status));
                }
            }
            catch (Exception e)
            {

                resul = false;

            }

            return resul;




    }

}
