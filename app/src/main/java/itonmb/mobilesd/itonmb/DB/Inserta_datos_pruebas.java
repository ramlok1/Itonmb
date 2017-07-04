package itonmb.mobilesd.itonmb.DB;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Conrado_Mobiles on 01/06/2017.
 */

public class Inserta_datos_pruebas {

    DBhelper dbhelper ;
    private SQLiteDatabase dbs;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    public  Inserta_datos_pruebas (Context context){
        dbhelper = new DBhelper(context);
        dbs= dbhelper.getWritableDatabase();
    }

    public  void inserta_datos_pruebas(){
        Random r = new Random();
        String[] agencias ={"Best Day","Expedia","Magnnicharter","Sunwing","Funjet","GoGo","PriceTravel"};
        String[]  nombre ={"Conrado Gonzalez","Baldor Baldez","Heber Cetinar","Paul Baas","Amalfi Carolina","Laura Saldaña","Seydi Espinosa"};
        String[]  hotel ={"Azul Beach","Oasis Cancun","Princess Tulum","Barcelo Playa","Sandos Caracol","IberoStar","Riu"};

        String[]  fpax ={"adulto","menor","infante"};
        String[]  color ={"#faeba6","#b7dbf3","#f8c1ba","#ace8dc"};
        String[]  nombre_barco ={"Don Diego","Mar y Arena","The Big","Saga Boy","Just Mars"};
        int[]  importe ={279,118,124,156,258,456,124,632};
        int[]  idioma ={2,3,4};
        int[]  idioma_icono ={2130837610,2130837611,2130837589,2130837619};
        int[]  tours ={132,130,13};
        int[]  status ={10,11,14,13};



        //Inerta datos usuario.
        ContentValues cv = new ContentValues();
        cv.put("id_usr",153);
        cv.put("usuario","conrado");
        cv.put("password", new String(Hex.encodeHex(DigestUtils.sha1("musica"))));
        cv.put("nombre", "Conrado Gonzalez");
        cv.put("tipo", 1);
        cv.put("status", 0);
        dbs.insert("usuarios", null, cv);

        //Inerta datos tipo operacion caja.
        ContentValues cop = new ContentValues();
        cop.put("id_op",153);
        cop.put("desc","Entrada por Venta");
        cop.put("tipo", "E");
        dbs.insert("tipo_operacion_caja", null, cop);

        cop = new ContentValues();
        cop.put("id_op",153);
        cop.put("desc","Retiro por Venta");
        cop.put("tipo", "S");
        dbs.insert("tipo_operacion_caja", null, cop);

        // Datos de reservas
        for(int i=1;i<=20;i++) {

                int numero = (int) (Math.random() * 7);
                int nidi = (int) (Math.random() * 3);
                int nst = (int) (Math.random() * 4);
                int cupon = 100000 + (int) (r.nextFloat() * 899900);
                int room = 1000 + (int) (r.nextFloat() * 9000);
                ContentValues cv1 = new ContentValues();
                cv1.put("orden_servicio", 261548);
                cv1.put("cupon", cupon);
                cv1.put("agencia", agencias[numero]);
                cv1.put("id_producto_padre", 13);
                cv1.put("id_producto", 13);
                cv1.put("producto", "Isla M.");
                cv1.put("adulto", 2);
                cv1.put("menor", 1);
                cv1.put("infante", 0);
                cv1.put("nombre_cliente", nombre[numero].toString());
                cv1.put("hotel", hotel[numero]);
                cv1.put("habi", room);
                cv1.put("observaciones", "Obsercación de cupon: " + Integer.toString(cupon));
                cv1.put("importe", importe[numero]);
                cv1.put("idioma", idioma[nidi]);
                cv1.put("idioma_icono", idioma_icono[nst]);
                cv1.put("fecha", dateFormat.format(date).toString());
                cv1.put("status", status[nst]);
                dbs.insert("reservas", null, cv1);

        }

        //Datos de productos
        ContentValues cv2 = new ContentValues();
        cv2.put("id_producto",132);
        cv2.put("id_producto_padre",13);
        cv2.put("desc","Isla M. Plus");
        cv2.put("importe", 180);
        dbs.insert("productos", null, cv2);

        cv2 = new ContentValues();
        cv2.put("id_producto",130);
        cv2.put("id_producto_padre",13);
        cv2.put("desc","Isla M. VIP");
        cv2.put("importe", 247);
        dbs.insert("productos", null, cv2);

        cv2 = new ContentValues();
        cv2.put("id_producto",13);
        cv2.put("id_producto_padre",13);
        cv2.put("desc","Isla M.");
        cv2.put("importe", 247);
        dbs.insert("productos", null, cv2);

        cv2 = new ContentValues();
        cv2.put("id_caja",13);
        cv2.put("caja","Caja1");
        cv2.put("status",0);
        dbs.insert("encabezado_caja", null, cv2);

        // Inserta Brazaletes
        for(int i=1;i<=100;i++) {
            int numero = (int) (Math.random() * 3);
            int numero2 = (int) (Math.random() * 3);
            int ncolor = (int) (Math.random() * 4);
            int folio = 100000 + (int) (r.nextFloat() * 899900);
            ContentValues cv1 = new ContentValues();
            cv1.put("folio", Integer.toString(folio));
            cv1.put("tipo",fpax[numero] );
            cv1.put("color", color[ncolor]);
            cv1.put("id_tour", tours[numero2]);
            cv1.put("id_usr", 153);
            cv1.put("status", 0);
            dbs.insert("brazaletes", null, cv1);

        }

        // Inserta Barcos
        for(int i=0;i<=4;i++) {

            int numero2 = (int) (Math.random() * 3);

            ContentValues cv1 = new ContentValues();
            cv1.put("id_bote", i);
            cv1.put("nombre",nombre_barco[i] );
            cv1.put("capacidad", 50);
            cv1.put("reservas", 50);
            cv1.put("abordado", 0);
            cv1.put("id_producto", tours[numero2]);
            dbs.insert("botes", null, cv1);

        }
    }
}
