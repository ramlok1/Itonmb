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
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbarcos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbrazaletes;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_formas_de_pago;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;
import itonmb.mobilesd.itonmb.modelo.modelo_spinner_productos_upg;


public class DBhelper extends SQLiteOpenHelper {

    private static DBhelper mInstance = null;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    DateFormat dateFormat_reserva = new SimpleDateFormat("dd/MM/yyyy");
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
    private String TABLA_RESERVAS = "create table reservas(id_rva integer PRIMARY KEY,  orden_servicio integer, cupon text, agencia text,id_producto_padre integer, id_producto integer,producto text," +
            " adulto integer, menor integer, infante integer,nombre_cliente text, hotel text, habi text, observaciones text, importe integer, idioma integer,idioma_icono integer, fecha datetime," +
            " status integer )";
    private String TABLA_PRODUCTOS = "create table productos(id_producto integer,id_producto_padre integer, desc text, importe integer)";
    private String TABLA_BRAZALETES = "create table brazaletes (folio text, tipo text, color text, id_tour integer,id_usr integer, status integer)";
    private String TABLA_ABORDAJE = "create table abordado(cupon text, barco text, fecha datetime, hora text)";

    private String TABLA_UPGRADE = "create table upgrade(id_ugr integer PRIMARY KEY,orden_servicio integer ,cupon text,usuario text,subtotal double,descuento double,total double, fecha datetime, firma blob, autoriza_descuento text)";

    private String TABLA_BRAZALETE_ASIGNACION = "create table brazalete_asignacion(id_asignacion integer PRIMARY KEY, cupon text,folio text,id_producto integer ,producto_desc text, color text, usuario text,abordado integer)";

    private String TABLA_FORMA_PAGO = "create table forma_de_pago(id_fp integer PRIMARY KEY,id_upg integer, cupon text, forma text,moneda text,monto_moneda double,tc double, monto_mn double,recibido double," +
            " cambio double, fecha datetime, usuario text)";
    private String TABLA_BOTES = "create table botes(id_bote integer, nombre text, capacidad integer, reservas integer, abordado integer, id_producto integer,seleccion integer)";

    private String TABLA_CHECKIN = "create table checkin(orden_servicio integer, cupon text, folio_brazalete text, " +
            "id_bote integer, fecha datetime, usuario text )";

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

    public void clean_database(){
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.execSQL("delete from usuarios");
        dbs.execSQL("delete from encabezado_caja");
        dbs.execSQL("delete from detalle_caja");
        dbs.execSQL("delete from reservas");
        dbs.execSQL("delete from productos");
        dbs.execSQL("delete from brazaletes");
        dbs.execSQL("delete from abordado");
        dbs.execSQL("delete from upgrade");
        dbs.execSQL("delete from brazalete_asignacion");
        dbs.execSQL("delete from forma_de_pago");
        dbs.execSQL("delete from botes");
        dbs.execSQL("delete from checkin");
        dbs.execSQL("delete from temporal_upg");
        dbs.execSQL("delete from upgrade_detalle");
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

    public ArrayList<modelo_lista_orden> getSearch_Cupones( String consulta_where){

        ArrayList<modelo_lista_orden> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_rva,cupon,agencia,id_producto,producto,adulto,menor,infante,nombre_cliente,hotel,habi,importe,status,observaciones,idioma,idioma_icono from reservas "+ consulta_where+ " order by cupon";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                int id_rva=cursor.getInt(cursor.getColumnIndex("id_rva"));
                String cupon=cursor.getString(cursor.getColumnIndex("cupon"));
                String agencia=cursor.getString(cursor.getColumnIndex("agencia"));
                int id_prodcuto_padre=cursor.getInt(cursor.getColumnIndex("id_producto"));
                String producto1=cursor.getString(cursor.getColumnIndex("producto"));
                int adulto=cursor.getInt(cursor.getColumnIndex("adulto"));
                int menor=cursor.getInt(cursor.getColumnIndex("menor"));
                int infante=cursor.getInt(cursor.getColumnIndex("infante"));
                String nombre_cliente=cursor.getString(cursor.getColumnIndex("nombre_cliente"));
                String hotel=cursor.getString(cursor.getColumnIndex("hotel"));
                String habi=cursor.getString(cursor.getColumnIndex("habi"));
                int importe=cursor.getInt(cursor.getColumnIndex("importe"));
                int status=cursor.getInt(cursor.getColumnIndex("status"));
                String obs=cursor.getString(cursor.getColumnIndex("observaciones"));
                int idioma=cursor.getInt(cursor.getColumnIndex("idioma"));
                int idioma_icono=cursor.getInt(cursor.getColumnIndex("idioma_icono"));

                datos.add(new modelo_lista_orden(id_rva,cupon,agencia,id_prodcuto_padre,producto1,adulto,menor,infante,nombre_cliente,hotel,habi,importe,status,obs,idioma,idioma_icono));
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

    public ArrayList<modelo_spinner_productos_upg> getProductos(){
        ArrayList<modelo_spinner_productos_upg> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_producto,desc,importe from productos";
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

    public String inserta_upgrade_temporal(String cupon,int id_producto, String producto,int adulto, int menor, int infante, int importe_producto, int ad_cupon,int me_cupon, int in_cupon){

        String response = "ok";
        SQLiteDatabase dbs = this.getWritableDatabase();

        // Busca cuantos adultos, menores e infantes ya estan seleccionados para upgrade (se hace en busquedas diferentes porque no permite sqlite un full outer join)
        String query_tmp = "select ifnull(sum(adulto),0) adultos, ifnull(sum(menor),0) menores, ifnull(sum(infante),0) infantes " +
                "     from temporal_upg  " +
                "where cupon='"+cupon+"'";
        Cursor cursor = dbs.rawQuery(query_tmp, null);

        if (cursor.moveToFirst()) {
                ad_cupon = ad_cupon-cursor.getInt(cursor.getColumnIndex("adultos"));
                me_cupon = me_cupon-cursor.getInt(cursor.getColumnIndex("menores"));
                in_cupon = in_cupon-cursor.getInt(cursor.getColumnIndex("infantes"));
        }
        cursor.close();

        String query_aplicados = "select ifnull(sum(adulto),0) adultos, ifnull(sum(menor),0) menores, ifnull(sum(infante),0) infantes " +
                "     from upgrade_detalle  " +
                "where cupon='"+cupon+"'";
        cursor = dbs.rawQuery(query_aplicados, null);

        if (cursor.moveToFirst()) {
            ad_cupon = ad_cupon-cursor.getInt(cursor.getColumnIndex("adultos"));
            me_cupon = me_cupon-cursor.getInt(cursor.getColumnIndex("menores"));
            in_cupon = in_cupon-cursor.getInt(cursor.getColumnIndex("infantes"));
        }
        cursor.close();

        // Valida numeros de pax restantes con los elegidos para upgrade

        if((ad_cupon-adulto)<0){return "adultox";}
        if((me_cupon-menor)<0){return "menorx";}
        if((in_cupon-infante)<0){return "infantex";}


        ContentValues cv = new ContentValues();
        cv.put("cupon",cupon);
        cv.put("id_producto",id_producto);
        cv.put("producto_desc",producto);
        cv.put("adulto", adulto);
        cv.put("menor", menor);
        cv.put("infante", infante);
        cv.put("importe", importe_producto);
        dbs.insert("temporal_upg", null, cv);

    return response;
    }

    public ArrayList<modelo_lista_upgrade_productos> getUpgrade_seleccionados(String cupon){

        ArrayList<modelo_lista_upgrade_productos> datos = new ArrayList<>();
        SQLiteDatabase dbs = this.getWritableDatabase();

        String consulta = "select id_tmp,producto_desc,adulto,menor,infante,importe from temporal_upg where cupon='"+cupon+"'";
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
                datos.add(new modelo_lista_upgrade_productos(id_tmp,producto_desc,adulto,menor,infante,importe,cupon));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public ArrayList<modelo_lista_formas_de_pago> getPagos(String cupon){

        ArrayList<modelo_lista_formas_de_pago> datos = new ArrayList<>();
        SQLiteDatabase dbs = this.getWritableDatabase();


        String consulta = "select forma,moneda,monto_moneda,monto_mn from forma_de_pago where cupon='"+cupon+"' and id_upg=999991";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{

                String forma_pago = cursor.getString(cursor.getColumnIndex("forma"));
                String moneda = cursor.getString(cursor.getColumnIndex("moneda"));
                double monto_moneda = cursor.getDouble(cursor.getColumnIndex("monto_moneda"));
                double monto_mn = cursor.getDouble(cursor.getColumnIndex("monto_mn"));

                // Se cargan datos de la bd en el arraylist
                datos.add(new modelo_lista_formas_de_pago(forma_pago,moneda,monto_moneda,monto_mn));
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

        String query = "select sum(importe) as total from temporal_upg where cupon='"+cupon+"'";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            total=cursor.getInt(cursor.getColumnIndex("total"));
        }
        dbs.close();
        return total;
    }

    public double getTotal_pagado(String cupon){
        double total=0;
        SQLiteDatabase dbs = this.getWritableDatabase();

        String query = "select sum(monto_mn) as total from forma_de_pago where cupon='"+cupon+"' and id_upg=999991";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            total=cursor.getDouble(cursor.getColumnIndex("total"));
        }
        dbs.close();
        return total;
    }

    public void inserta_forma_pago(int id_upg,String cupon, String forma,double monto_moneda,double monto_mn,double tc, String moneda, double recibido, double cambio ){
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_upg",id_upg);
        cv.put("cupon",cupon);
        cv.put("forma",forma);
        cv.put("moneda", moneda);
        cv.put("monto_moneda",monto_moneda);
        cv.put("tc",tc);
        cv.put("monto_mn",monto_mn);
        cv.put("recibido", recibido);
        cv.put("cambio", cambio);
        cv.put("fecha", dateFormat.format(date));
        cv.put("usuario", Global.usuario);
        dbs.insert("forma_de_pago", null, cv);
        dbs.close();

    }

    public void update_forma_pago(int id_upg,String cupon ){
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_upg",id_upg);
        dbs.update("forma_de_pago",cv,"cupon='"+cupon+"'",null);
        dbs.close();

    }

    public boolean valida_muelle_pagado(String cupon){
        boolean ban=false;
        SQLiteDatabase dbs = this.getWritableDatabase();

        String query = "select id_fp from forma_de_pago where cupon='"+cupon+"' and id_upg=9999999";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            ban=true;
        }
        dbs.close();
        return ban;
    }

    public int[] inserta_upgrade (String cupon, double total,double subtotal, int id_rva,double descuento, String autoriza_desc, byte[] firma){

        int id_upg=0;
        int[] upg_datos = new int[4];

        SQLiteDatabase dbs = this.getWritableDatabase();

        // inserta encabezado upgrade
        ContentValues cv = new ContentValues();
        cv.put("orden_servicio",Global.orden_de_servicio);
        cv.put("cupon",cupon);
        cv.put("usuario",Global.usuario);
        cv.put("total",total);
        cv.put("subtotal",subtotal);
        cv.put("fecha", dateFormat.format(date));
        cv.put("descuento",descuento);
        cv.put("firma",firma);
        cv.put("autoriza_descuento",autoriza_desc);
        dbs.insert("upgrade", null, cv);


        String sql_max_id = "Select max(id_ugr) as id_upg from upgrade";
        Cursor cursor = dbs.rawQuery(sql_max_id, null);
        if (cursor.moveToFirst()) {
            do{
                id_upg = cursor.getInt(cursor.getColumnIndex("id_upg"));
                upg_datos[0] = id_upg;
            }while(cursor.moveToNext());
            }
        cursor.close();

        //Inserta detalle de upgrade
        String consulta = "select id_producto,producto_desc,sum(adulto) adulto,sum(menor) menor,sum(infante) infante,importe from temporal_upg where cupon='"+cupon+"' group by id_producto";
        cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                int id_producto = cursor.getInt(cursor.getColumnIndex("id_producto"));
                String producto_desc = cursor.getString(cursor.getColumnIndex("producto_desc"));
                int adulto = cursor.getInt(cursor.getColumnIndex("adulto"));
                int menor = cursor.getInt(cursor.getColumnIndex("menor"));
                int infante = cursor.getInt(cursor.getColumnIndex("infante"));
                int importe = cursor.getInt(cursor.getColumnIndex("importe"));

                //Datos upgrade para brazalete
                upg_datos[1] = adulto;
                upg_datos[2] = menor;
                upg_datos[3] = infante;

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


                String query = "select adulto,menor,infante,agencia,id_producto_padre,nombre_cliente,hotel,habi,observaciones,idioma,idioma_icono,status from reservas where id_rva="+id_rva;
                Cursor cur = dbs.rawQuery(query, null);


                if (cur.moveToFirst()) {

                    String agencia = cur.getString(cur.getColumnIndex("agencia"));
                    String nombre_cliente = cur.getString(cur.getColumnIndex("nombre_cliente"));
                    String hotel = cur.getString(cur.getColumnIndex("hotel"));
                    String habi = cur.getString(cur.getColumnIndex("habi"));
                    String observaciones = cur.getString(cur.getColumnIndex("observaciones"));
                    String idioma = cur.getString(cur.getColumnIndex("idioma"));
                    int id_producto_padre = cur.getInt(cur.getColumnIndex("id_producto_padre"));
                    int adulto_o = cur.getInt(cur.getColumnIndex("adulto"));
                    int menor_o = cur.getInt(cur.getColumnIndex("menor"));
                    int infante_o = cur.getInt(cur.getColumnIndex("infante"));
                    int idioma_icono = cur.getInt(cur.getColumnIndex("idioma_icono"));
                    int status = cur.getInt(cur.getColumnIndex("status"));


                    cv = new ContentValues();
                    cv.put("orden_servicio", Global.orden_de_servicio);
                    cv.put("cupon", cupon);
                    cv.put("agencia",agencia );
                    cv.put("id_producto_padre",id_producto_padre );
                    cv.put("id_producto", id_producto);
                    cv.put("producto", producto_desc);
                    cv.put("adulto", adulto);
                    cv.put("menor", menor);
                    cv.put("infante", infante);
                    cv.put("nombre_cliente",nombre_cliente );
                    cv.put("hotel",hotel );
                    cv.put("habi",habi );
                    cv.put("observaciones",observaciones );
                    cv.put("importe", importe);
                    cv.put("idioma",idioma );
                    cv.put("idioma_icono",idioma_icono );
                    cv.put("fecha", dateFormat_reserva.format(date));
                    cv.put("status", status);
                    dbs.insert("reservas", null, cv);

                    cv = new ContentValues();
                    cv.put("adulto",adulto_o-adulto);
                    cv.put("menor",menor_o-menor);
                    cv.put("infante",infante_o-infante);
                    dbs.update("reservas",cv,"id_rva="+id_rva,null);
                }

            }while(cursor.moveToNext());
        }







        cancela_upgrade(cupon);
        cursor.close();
        dbs.close();

                return upg_datos;

    }

    public int getUpgrade_total_pax (String cupon){


        int pax_datos=0;

        SQLiteDatabase dbs = this.getWritableDatabase();

        //Inserta detalle de upgrade
        String consulta = "select id_producto,sum(adulto)+sum(menor)+sum(infante) pax from temporal_upg where cupon='"+cupon+"' group by id_producto";
       Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                 pax_datos = cursor.getInt(cursor.getColumnIndex("pax"));

            }while(cursor.moveToNext());
        }
        cursor.close();
        dbs.close();

        return pax_datos;

    }

    public String busca_brazalete (String folio, int id_tour,String producto_desc, String cupon, int adulto,int menor, int infante){

        String encontrado = "nada";


        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select a.tipo as tipo, count(b.id_asignacion) as cant from brazaletes a,brazalete_asignacion b where b.folio='"+folio+"' and a.folio=b.folio group by a.tipo";
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
                    cv.put("id_producto", id_tour);
                    cv.put("producto_desc", producto_desc);
                    cv.put("color", cursor.getString(cursor.getColumnIndex("color")));
                    cv.put("usuario", Global.usuario);
                    cv.put("abordado", 0);
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


    public ArrayList<modelo_lista_agregar_brazalete> getBrazaletes_asignados(String cupon, int id_tour, String producto_desc){
        ArrayList<modelo_lista_agregar_brazalete> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_asignacion,folio,color from brazalete_asignacion where cupon='"+cupon+"' and id_producto="+id_tour+"";
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

    public ArrayList<modelo_lista_dbarcos> getBarcos_disponibles(int id_producto){
        ArrayList<modelo_lista_dbarcos> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_bote,nombre,capacidad,reservas,abordado  from botes where id_producto="+id_producto;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                String nombre_barco=cursor.getString(cursor.getColumnIndex("nombre"));
                int capac =cursor.getInt(cursor.getColumnIndex("capacidad"));
                int id_bote =cursor.getInt(cursor.getColumnIndex("id_bote"));
                int booking =cursor.getInt(cursor.getColumnIndex("reservas"));
                int aborda =cursor.getInt(cursor.getColumnIndex("abordado"));
                datos.add(new modelo_lista_dbarcos(id_bote,nombre_barco,capac,booking,aborda));
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

    public int getTotal_pax_abordar(String cupon){
        int total_pax=0;
        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select count(folio) as cant from brazalete_asignacion  where cupon='"+cupon+"' and abordado=0";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            total_pax = cursor.getInt(cursor.getColumnIndex("cant"));
        }
        cursor.close();

        return total_pax;
    }

    public void update_barcos_check_clean(){
        SQLiteDatabase dbs = this.getWritableDatabase();


        ContentValues up = new ContentValues();
        up.put("seleccion",0);
        dbs.update("botes",up,null,null);

        dbs.close();


    }

    public void update_barcos_check_yes(int id_bote){
        SQLiteDatabase dbs = this.getWritableDatabase();


        ContentValues up = new ContentValues();
        up.put("seleccion",1);
        dbs.update("botes",up,"id_bote="+id_bote,null);

        dbs.close();


    }

    public boolean valida_barco_seleccion(){
        boolean ban = false;

        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select count(id_bote) as cant from botes  where seleccion=1";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int total = cursor.getInt(cursor.getColumnIndex("cant"));
            if (total==1){ban=true;}
        }
        cursor.close();

        return ban;
    }

    public void inserta_abordaje_in(String cupon, int total_pax,int id_tour, int total_pax_cupon){

        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues up = new ContentValues();
        int id_bote=0, abordados=0;


        // Obtener id de bote seleccionado
        String consulta_id_bote = "select id_bote,abordado from botes where seleccion=1 and id_producto="+id_tour;
        Cursor cb = dbs.rawQuery(consulta_id_bote, null);

        if (cb.moveToFirst()) {
                id_bote = cb.getInt(cb.getColumnIndex("id_bote"));
                abordados = cb.getInt(cb.getColumnIndex("abordado"));
        }
        // obtener folios de brazaletes seleccionados
        String consulta = "select folio from brazalete_asignacion where cupon='"+cupon+"' and id_producto="+id_tour;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                String folio =cursor.getString(cursor.getColumnIndex("folio"));

                     /// Inserta check-in
                        ContentValues in = new ContentValues();
                        in.put("orden_servicio",Global.orden_de_servicio);
                        in.put("cupon",cupon);
                        in.put("folio_brazalete",folio);
                        in.put("id_bote",id_bote);
                        in.put("fecha",dateFormat.format(date));
                        in.put("usuario",Global.usuario);
                        dbs.insert("checkin",null,in);
            }while(cursor.moveToNext());
        }


        // Marcar brazaletes como abordados
        up.put("abordado",1);
        dbs.update("brazalete_asignacion",up,"cupon='"+cupon+"'",null);

        // Marcar status de cupon en tabla de reservas
        up = new ContentValues();
        if(total_pax==total_pax_cupon){
            up.put("status",14);
        }else{up.put("status",13);}
        dbs.update("reservas",up,"cupon='"+cupon+"' and id_producto="+id_tour,null);

        // Aumenta numero de abordados
        up = new ContentValues();
        up.put("abordado",abordados+total_pax);
        dbs.update("botes",up,"id_bote="+id_bote,null);

        dbs.close();
        update_barcos_check_clean();


    }

    public ArrayList<modelo_lista_dbrazaletes> getBrazaletes_disponibles(){

        ArrayList<modelo_lista_dbrazaletes> datos = new ArrayList<>();
        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select folio,tipo,color,desc from brazaletes,productos where id_producto=id_tour and status=0 order by folio,tipo, desc";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                String folio=cursor.getString(cursor.getColumnIndex("folio"));
                String tipo=cursor.getString(cursor.getColumnIndex("tipo"));
                String color=cursor.getString(cursor.getColumnIndex("color"));
                String tour=cursor.getString(cursor.getColumnIndex("desc"));


                datos.add(new modelo_lista_dbrazaletes(folio,tipo,color,tour));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public void cancela_Cupon(String cupon){

        // Marcar status de cupon en tabla de reservas
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues up = new ContentValues();

        up.put("status",2);
        dbs.update("reservas",up,"cupon='"+cupon+"'",null);

        dbs.close();

    }

}

