package itonmb.mobilesd.itonmb.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;


public class DBhelper extends SQLiteOpenHelper {

    private static DBhelper mInstance = null;

    public static DBhelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (mInstance == null) {
            mInstance = new DBhelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DBlocal";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String TABLA_USUARIOS = "create table usuarios(id_usr integer PRIMARY KEY, usuario text, password text, nombre text, tipo integer, status integer)";
    private String TABLA_ENC_CAJA = "create table encabezado_caja( id_caja integer PRIMARY KEY, fecha datetime, hora text,usuario text, monto_inicial integer,monto_final integer, status integer)";
    private String TABLA_DET_CAJA = "create table detalle_caja(id_d_caja integer PRIMARY KEY, id_caja integer, fecha datetime,hora text,tipo_movimiento integer, monto integer, forma_ingreso integer)";
    private String TABLA_RESERVAS = "create table reservas(id_rva integer PRIMARY KEY,  orden_servicio integer, cuponn integer, agencia text,prroducto text," +
            " adulto integer, menor integer, infante integer,nombre_cliente text, hotel text, habi text, observaciones text, importe integer, idioma text, status integer, abordaje integer )";
    private String TABLA_PRODUCTOS = "create table productos(id_producto integer, desc text, precio integer)";
    private String TABLA_BRAZALETES = "create table brazaletes (folio text, tipo text, colo text)";
    private String TABLA_ABORDAJE = "create table abordado(cupon integer, barco text, fecha datetime, hora text)";
    private String TABLA_ENC_UPGRADE = "create table encabezado_upgrade(id_ugr integer PRIMARY KEY, id_producto integer, desc_producto text ,cupon integer, adulto integer, menor integer," +
            "infante integer, usuario text, descuento integer, aut_descuento text, precio integer, total integer, fecha datetime, hora text, pagado integer)";
    private String TABLA_DET_UPGRADE = "create table detalle_upgrade(id_det_upgr integer PRIMARY KEY, id_upgr integer, brazalete text)";
    private String TABLA_FORMA_PAGO = "create table forma_de_pago(id_fp integer PRIMARY KEY, id_upgr integer, forma text, monto integer, descuento integer, recibido integer," +
            " cambio integer, fecha datetime, hora text, usuario text)";
    private String TABLA_BOTES = "create table botes(nombre text, capacidad integer, reservas integer, abordado integer)";



    @Override
    public void onCreate(SQLiteDatabase db) {

        //aquí creamos la tabla de usuario (dni, nombre, ciudad, numero)

        db.execSQL(TABLA_USUARIOS);
        db.execSQL(TABLA_ENC_CAJA);
        db.execSQL(TABLA_DET_CAJA);
        db.execSQL(TABLA_RESERVAS);
        db.execSQL(TABLA_PRODUCTOS);
        db.execSQL(TABLA_BRAZALETES);
        db.execSQL(TABLA_ABORDAJE);
        db.execSQL(TABLA_ENC_UPGRADE);
        db.execSQL(TABLA_DET_UPGRADE);
        db.execSQL(TABLA_FORMA_PAGO);
        db.execSQL(TABLA_BOTES);


    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        db.execSQL("drop table if exists usuarios");
        db.execSQL("drop table if exists encabezado_caja");
        db.execSQL("drop table if exists detalle_caja");
        db.execSQL("drop table if exists reservas");
        db.execSQL("drop table if exists productos");
        db.execSQL("drop table if exists brazaletes");
        db.execSQL("drop table if exists abordado");
        db.execSQL("drop table if exists encabezado_upgrade");
        db.execSQL("drop table if exists detalle_upgrade");
        db.execSQL("drop table if exists forma_de_pago");
        db.execSQL("drop table if exists botes");


        onCreate(db);


    }

    public String getLogin(String usr, String pwd){

        String shapwd = new String(Hex.encodeHex(DigestUtils.sha1(pwd)));
        SQLiteDatabase dbs = this.getWritableDatabase();
        String nombre="";
        String query = "select nombre from usuarios where usuario='"+usr+"' and password='"+shapwd+"'";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            nombre=cursor.getString(cursor.getColumnIndex("nombre"));
        }
        return nombre;
    }

}

