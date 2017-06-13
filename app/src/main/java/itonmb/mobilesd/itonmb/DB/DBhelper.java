package itonmb.mobilesd.itonmb.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_agregar_brazalete;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;
import itonmb.mobilesd.itonmb.modelo.modelo_spinner_productos_upg;


public class DBhelper extends SQLiteOpenHelper {

    private static DBhelper mInstance = null;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    String tipo_brazalete_ban;

    public static DBhelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (mInstance == null) {
            mInstance = new DBhelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "DBlocal";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String TABLA_USUARIOS = "create table usuarios(id_usr integer, usuario text, password text, nombre text, tipo integer, status integer)";
    private String TABLA_ENC_CAJA = "create table encabezado_caja( id_caja integer PRIMARY KEY, fecha datetime, hora text,usuario text, monto_inicial integer," +
            "monto_final integer, status integer)";
    private String TABLA_DET_CAJA = "create table detalle_caja(id_d_caja integer PRIMARY KEY, id_caja integer, fecha datetime,hora text,tipo_movimiento integer," +
            " monto integer, forma_ingreso integer)";
    private String TABLA_RESERVAS = "create table reservas(id_rva integer PRIMARY KEY,  orden_servicio integer, cupon text, agencia text,id_producto_padre integer,producto text," +
            " adulto integer, menor integer, infante integer,nombre_cliente text, hotel text, habi text, observaciones text, importe integer, idioma text, fecha datetime," +
            " status integer )";
    private String TABLA_PRODUCTOS = "create table productos(id_producto integer,id_producto_padre integer, desc text, importe integer)";
    private String TABLA_BRAZALETES = "create table brazaletes (folio text, tipo text, color text, id_tour integer,id_usr integer, status integer)";
    private String TABLA_ABORDAJE = "create table abordado(cupon text, barco text, fecha datetime, hora text)";

    private String TABLA_UPGRADE = "create table upgrade(id_ugr integer PRIMARY KEY,orden_servicio integer ,cupon text,usuario text,total double, fecha datetime)";

    private String TABLA_BRAZALETE_ASIGNACION = "create table brazalete_asignacion(id_asignacion integer PRIMARY KEY, cupon integer,folio text,producto_desc text, color text)";

    private String TABLA_FORMA_PAGO = "create table forma_de_pago(id_fp integer PRIMARY KEY,id_upg integer, cupon text, forma text, monto double, descuento integer, recibido integer," +
            " cambio double, fecha datetime, usuario text)";
    private String TABLA_BOTES = "create table botes(id_bote integer, nombre text, capacidad integer, reservas integer, abordado integer, id_producto integer)";

    private String TABLA_CHECKIN = "create table checkin(orden_servicio integer, cupon text, folio_brazalete text, adulto integer, menor integer, infante integer, " +
            "id_bote integer, fecha datetime, usuario text, abordado integer)";

    private String TABLA_UPG_TEMPORAL   = "create table temporal_upg(id_tmp integer PRIMARY KEY, cupon text, id_producto integer, producto_desc text," +
            " adulto integer,menor integer,infante integer, importe integer)";

    private String TABLA_UPG_DETALLE   = "create table upgrade_detalle(id_tmp integer PRIMARY KEY,id_upg integer, cupon text, id_producto integer, producto_desc text," +
            " adulto integer,menor integer,infante integer, importe integer)";



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
        db.execSQL(TABLA_UPGRADE);
        db.execSQL(TABLA_BRAZALETE_ASIGNACION);
        db.execSQL(TABLA_FORMA_PAGO);
        db.execSQL(TABLA_BOTES);
        db.execSQL(TABLA_UPG_TEMPORAL);
        db.execSQL(TABLA_CHECKIN);
        db.execSQL(TABLA_UPG_DETALLE);




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
        db.execSQL("drop table if exists upgrade");
        db.execSQL("drop table if exists brazalete_asignacion");
        db.execSQL("drop table if exists forma_de_pago");
        db.execSQL("drop table if exists botes");
        db.execSQL("drop table if exists temporal_upg");
        db.execSQL("drop table if exists checkin");
        db.execSQL("drop table if exists upgrade_detalle");


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
        dbs.close();
        return nombre;
    }

    public ArrayList<modelo_lista_orden> getSearch_Cupones( String name, String date, String producto, String operacion){
        ArrayList<modelo_lista_orden> datos = new ArrayList<>();
        String consulta_where=" where 0=0";
        SQLiteDatabase dbs = this.getWritableDatabase();

        // Armado de where dinamico
        if(name.length()!=0){consulta_where=consulta_where+" and nombre_cliente like '%"+name+"%'";}
        if(date.length()!=0){consulta_where=consulta_where+" and fecha = '"+date+"'";}
        if(producto.length()!=0){consulta_where=consulta_where+" and producto like '%"+producto+"%'";}
        if(operacion.length()!=0){consulta_where=consulta_where+" and orden_servicio="+operacion;}

        String consulta = "select cupon,agencia,id_producto_padre,producto,adulto,menor,infante,nombre_cliente,hotel,habi,importe,status from reservas "+ consulta_where;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                String cupon=cursor.getString(cursor.getColumnIndex("cupon"));
                String agencia=cursor.getString(cursor.getColumnIndex("agencia"));
                int id_prodcuto_padre=cursor.getInt(cursor.getColumnIndex("id_producto_padre"));
                String producto1=cursor.getString(cursor.getColumnIndex("producto"));
                int adulto=cursor.getInt(cursor.getColumnIndex("adulto"));
                int menor=cursor.getInt(cursor.getColumnIndex("menor"));
                int infante=cursor.getInt(cursor.getColumnIndex("infante"));
                String nombre_cliente=cursor.getString(cursor.getColumnIndex("nombre_cliente"));
                String hotel=cursor.getString(cursor.getColumnIndex("hotel"));
                String habi=cursor.getString(cursor.getColumnIndex("habi"));
                int importe=cursor.getInt(cursor.getColumnIndex("importe"));
                int status=cursor.getInt(cursor.getColumnIndex("status"));

                datos.add(new modelo_lista_orden(cupon,agencia,id_prodcuto_padre,producto1,adulto,menor,infante,nombre_cliente,hotel,habi,importe,status));
             }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public ArrayList<modelo_spinner_productos_upg> getProductos_upgrade( int producto_padre){
        ArrayList<modelo_spinner_productos_upg> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_producto,desc,importe from productos where id_producto_padre="+producto_padre;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                datos.add(new modelo_spinner_productos_upg(cursor.getString(cursor.getColumnIndex("desc"))
                        ,cursor.getInt(cursor.getColumnIndex("id_producto")),cursor.getInt(cursor.getColumnIndex("importe"))));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public void inserta_upgrade_temporal(String cupon,int id_producto, String producto,int adulto, int menor, int infante, int importe_producto){
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("cupon",cupon);
        cv.put("id_producto",id_producto);
        cv.put("producto_desc",producto);
        cv.put("adulto", adulto);
        cv.put("menor", menor);
        cv.put("infante", infante);
        cv.put("importe", importe_producto);
        dbs.insert("temporal_upg", null, cv);


    }

    public ArrayList<modelo_lista_upgrade_productos> getUpgrade_seleccionados(String cupon){

        ArrayList<modelo_lista_upgrade_productos> datos = new ArrayList<>();
        SQLiteDatabase dbs = this.getWritableDatabase();

        String consulta = "select id_tmp,producto_desc,adulto,menor,infante,importe from temporal_upg where cupon="+cupon;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{

                String producto_desc = cursor.getString(cursor.getColumnIndex("producto_desc"));
                int adulto = cursor.getInt(cursor.getColumnIndex("adulto"));
                int menor = cursor.getInt(cursor.getColumnIndex("menor"));
                int infante = cursor.getInt(cursor.getColumnIndex("infante"));
                int importe = cursor.getInt(cursor.getColumnIndex("importe"));
                int id_tmp = cursor.getInt(cursor.getColumnIndex("id_tmp"));
                // Se cargan datos de la bd en el arraylist
                datos.add(new modelo_lista_upgrade_productos(id_tmp,producto_desc,adulto,menor,infante,importe));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public void borra_elemento_upg(int id_tmp){
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.delete("temporal_upg", "id_tmp=" + id_tmp, null);
        dbs.close();
    }
    public void borra_elemento_br(int id_br, String folio){
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.delete("brazalete_asignacion", "id_asignacion=" + id_br, null);

        ContentValues up = new ContentValues();
        up.put("status",0);
        dbs.update("brazaletes",up,"folio="+folio,null);

        dbs.close();


    }

    public void cancela_upgrade(String cupon){
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.delete("temporal_upg", "cupon='" + cupon+"'", null);
        dbs.close();
    }

    public int getTotal_upgrade(String cupon){
        int total=0;
        SQLiteDatabase dbs = this.getWritableDatabase();

        String query = "select sum(importe) as total from temporal_upg where cupon="+cupon;
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            total=cursor.getInt(cursor.getColumnIndex("total"));
        }
        dbs.close();
        return total;
    }

    public void inserta_forma_pago(int id_upg,String cupon, String forma,double monto, int descuento, int recibido, double cambio ){
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_upg",id_upg);
        cv.put("cupon",cupon);
        cv.put("forma",forma);
        cv.put("monto",monto);
        cv.put("descuento", descuento);
        cv.put("recibido", recibido);
        cv.put("cambio", cambio);
        cv.put("fecha", dateFormat.format(date));
        cv.put("usuario", Global.usuario);
        dbs.insert("forma_de_pago", null, cv);
        dbs.close();

    }

    public int inserta_upgrade (String cupon, double total){

        int id_upg=0;

        SQLiteDatabase dbs = this.getWritableDatabase();

        // inserta encabezado upgrade
        ContentValues cv = new ContentValues();
        cv.put("orden_servicio",Global.orden_de_servicio);
        cv.put("cupon",cupon);
        cv.put("usuario",Global.usuario);
        cv.put("total",total);
        cv.put("fecha", dateFormat.format(date));
        dbs.insert("upgrade", null, cv);


        String sql_max_id = "Select max(id_ugr) as id_upg from upgrade";
        Cursor cursor = dbs.rawQuery(sql_max_id, null);
        if (cursor.moveToFirst()) {
            do{
                id_upg = cursor.getInt(cursor.getColumnIndex("id_upg"));
            }while(cursor.moveToNext());
            }
        cursor.close();

        //Inserta detalle de upgrade
        String consulta = "select id_producto,producto_desc,adulto,menor,infante,importe from temporal_upg where cupon="+cupon;
        cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{

                int id_producto = cursor.getInt(cursor.getColumnIndex("id_producto"));
                String producto_desc = cursor.getString(cursor.getColumnIndex("producto_desc"));
                int adulto = cursor.getInt(cursor.getColumnIndex("adulto"));
                int menor = cursor.getInt(cursor.getColumnIndex("menor"));
                int infante = cursor.getInt(cursor.getColumnIndex("infante"));
                int importe = cursor.getInt(cursor.getColumnIndex("importe"));


                cv = new ContentValues();
                cv.put("id_upg",id_upg);
                cv.put("cupon",cupon);
                cv.put("id_producto",id_producto);
                cv.put("producto_desc",producto_desc);
                cv.put("adulto", adulto);
                cv.put("menor", menor);
                cv.put("infante", infante);
                cv.put("importe", importe);
                dbs.insert("upgrade_detalle", null, cv);


            }while(cursor.moveToNext());
        }



        cancela_upgrade(cupon);
        cursor.close();
        dbs.close();

                return id_upg;

    }

    public String busca_brazalete (String folio, int id_tour,String producto_desc, String cupon, int adulto,int menor, int infante){

        String encontrado = "nada";


        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select a.tipo as tipo, count(b.id_asignacion) as cant from brazaletes a,brazalete_asignacion b where a.folio=b.folio group by a.tipo";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String tipo_1=cursor.getString(cursor.getColumnIndex("tipo"));
            if(tipo_1.equals("adulto")) {
                adulto = adulto-cursor.getInt(cursor.getColumnIndex("cant"));
            }
            else if(tipo_1.equals("menor")) {
                menor = menor-cursor.getInt(cursor.getColumnIndex("cant"));
            }
            else if(tipo_1.equals("infante")) {
                infante = infante-cursor.getInt(cursor.getColumnIndex("cant"));
            }
        }
        cursor.close();

        String sql_brazalete = "Select tipo,color from brazaletes where folio='"+folio+"' and id_tour="+id_tour+" and status=0 and id_usr="+Global.id_usr;
         cursor = dbs.rawQuery(sql_brazalete, null);
        if (cursor.moveToFirst()) {
            do{
                String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                boolean continua = validaPax_brazalete(adulto,menor,infante,tipo);

                if(continua) {
                    // inserta datos de brazalete
                    ContentValues cv = new ContentValues();
                    cv.put("cupon", cupon);
                    cv.put("folio", folio);
                    cv.put("producto_desc", producto_desc);
                    cv.put("color", cursor.getString(cursor.getColumnIndex("color")));
                    dbs.insert("brazalete_asignacion", null, cv);

                    ContentValues up = new ContentValues();
                    up.put("status",1);
                    dbs.update("brazaletes",up,"folio="+folio,null);
                    encontrado=tipo;
                }else
                {return tipo_brazalete_ban;}

            }while(cursor.moveToNext());
        }

        cursor.close();
        dbs.close();

        return encontrado;

    }

    public ArrayList<modelo_lista_agregar_brazalete> getBrazaletes_asignados(String cupon, String producto_desc){
        ArrayList<modelo_lista_agregar_brazalete> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_asignacion,folio,color from brazalete_asignacion where cupon='"+cupon+"' and producto_desc='"+producto_desc+"'";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{

                int id_br=cursor.getInt(cursor.getColumnIndex("id_asignacion"));
                String folio =cursor.getString(cursor.getColumnIndex("folio"));
                String color =cursor.getString(cursor.getColumnIndex("color"));
                datos.add(new modelo_lista_agregar_brazalete(folio,producto_desc,color,id_br));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    private boolean validaPax_brazalete(int adulto, int menor, int infante, String tipo){
        boolean ban = true;
        tipo_brazalete_ban="N";

        switch (tipo){
            case "adulto":
                if(adulto==0){ban=false;tipo_brazalete_ban="adultox";}
                else{tipo_brazalete_ban=tipo;}
                break;
            case "menor":
                if(menor==0){ban=false;tipo_brazalete_ban="menorx";}
                else{tipo_brazalete_ban=tipo;}
                break;
            case "infante":
                if(infante==0){ban=false;tipo_brazalete_ban="infantex";}
                else{tipo_brazalete_ban=tipo;}
                break;
        }





        return ban;
    }

}

