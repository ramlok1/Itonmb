package itonmb.mobilesd.itonmb.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Conrado_Mobiles on 01/06/2017.
 */

public class Inserta_datos_pruebas {

    private DBhelper dbhelper ;
    private SQLiteDatabase dbs;

    public  Inserta_datos_pruebas (Context contexto){
        dbhelper = new DBhelper(contexto);
        dbs= dbhelper.getWritableDatabase();
    }

    public  void inserta_datos_pruebas(){
        //Inerta datos usuario.
        ContentValues cv = new ContentValues();
        cv.put("usuario","conrado");
        cv.put("password", new String(Hex.encodeHex(DigestUtils.sha1("musica"))));
        cv.put("nombre", "Conrado Gonzalez");
        cv.put("tipo", 1);
        cv.put("status", 0);
        dbs.insert("usuarios", null, cv);

    }
}
