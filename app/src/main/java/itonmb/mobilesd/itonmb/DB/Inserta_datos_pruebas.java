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
        cv.put("usuario","admin");
        cv.put("password", new String(Hex.encodeHex(DigestUtils.sha1("admin"))));
        cv.put("nombre", "Administrador");
        cv.put("tipo", 1);
        cv.put("status", 0);
        dbs.insert("usuarios", null, cv);
    }
}
