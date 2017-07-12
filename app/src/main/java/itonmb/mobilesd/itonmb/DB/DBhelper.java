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
import itonmb.mobilesd.itonmb.modelo.modelo_lista_Caja;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_Tour;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_TourUpgrade;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_agregar_brazalete;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbarcos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbrazaletes;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_formas_de_pago;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_tipoOperacionCaja;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_tour_barcos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_ws_brazalete;
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

    private String TABLA_TIPO_OPERACION_CAJA = "create table tipo_operacion_caja(id_op integer, desc text, tipo text)";

    private String TABLA_CAJA_DENOMINACION = "create table caja_denominacion(id_sesion integer,id_caja integer, cant integer, denom double, tipo text, tipo_operacion text, usuario text)";

    private String TABLA_ENC_CAJA = "create table encabezado_caja( id_caja integer,caja text, fecha_abre datetime,fecha_cierre datetime,usuario text, monto_inicial_usd double," +
            "monto_final_usd double,monto_inicial_mxn double,monto_final_mxn double, status integer)";
    private String TABLA_DET_CAJA = "create table detalle_caja(id_d_caja integer PRIMARY KEY, id_caja integer, fecha datetime,tipo_movimiento text," +
            " monto_moneda double, moneda text,monto_mn double,usuario text,operacion text,observacion text,forma_ingreso integer)";
    private String TABLA_RESERVAS = "create table reservas(id_rva integer PRIMARY KEY,  orden_servicio integer, reservaDetalle integer, cupon text, agencia text,id_producto_padre integer, id_producto integer,producto text," +
            " adulto integer, menor integer, infante integer,nombre_cliente text, hotel text, habi text, observaciones text,idioma integer,idioma_icono integer, fecha datetime," +
            " status integer )";
    private String TABLA_PRODUCTOS = "create table productos(id_producto integer,id_producto_padre integer, desc text, idEquipoBase integer, idBrazalete_Adulto integer, idBrazalete_menor integer, idBrazalete_complemento integer, precio_ad double, precio_me double, precio_in double)";

    private String TABLA_PRODUCTOS_UPGRADE = "create table productos_upgrade(idTour integer, idTourCombinacion integer)";

    private String TABLA_BRAZALETES = "create table brazaletes (idBrazalete integer, folio integer, tipo text, color text,id_usr text, status integer)";

    private String TABLA_ABORDAJE = "create table abordado(cupon text, barco text, fecha datetime, hora text)";

    private String TABLA_UPGRADE = "create table upgrade(id_ugr integer PRIMARY KEY,orden_servicio integer ,cupon text,usuario text,subtotal double,descuento double,total double, fecha datetime, firma blob, autoriza_descuento text)";

    private String TABLA_BRAZALETE_ASIGNACION = "create table brazalete_asignacion(id_asignacion integer PRIMARY KEY, cupon text,idBrazalete integer, folio integer,id_producto integer ,producto_desc text, color text, usuario text,abordado integer)";

    private String TABLA_FORMA_PAGO = "create table forma_de_pago(id_fp integer PRIMARY KEY,id_upg integer, cupon text, forma text,moneda text,monto_moneda double,tc double, monto_mn double,recibido double," +
            " cambio double, fecha datetime, usuario text)";
    private String TABLA_BOTES = "create table botes(idOpboat integer,id_bote integer, nombre text, capacidad integer, abordado integer,seleccion integer)";

    private String TABLA_BOTES_TOUR = "create table botes_tour(idEquipoBase integer,idTour integer)";

    private String TABLA_CHECKIN = "create table checkin(orden_servicio integer, cupon text, folio_brazalete integer, " +
            "id_bote integer, fecha datetime, usuario text )";

    private String TABLA_UPG_TEMPORAL   = "create table temporal_upg(id_tmp integer PRIMARY KEY, cupon text, id_producto integer, producto_desc text," +
            " adulto integer,menor integer,infante integer, importe double)";

    private String TABLA_UPG_DETALLE   = "create table upgrade_detalle(id_tmp integer PRIMARY KEY,id_upg integer, cupon text, id_producto integer, producto_desc text," +
            " adulto integer,menor integer,infante integer)";



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
        db.execSQL(TABLA_TIPO_OPERACION_CAJA);
        db.execSQL(TABLA_CAJA_DENOMINACION);
        db.execSQL(TABLA_PRODUCTOS_UPGRADE);
        db.execSQL(TABLA_BOTES_TOUR);




    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        db.execSQL("drop table if exists usuarios");
        db.execSQL("drop table if exists encabezado_caja");
        db.execSQL("drop table if exists detalle_caja");
        db.execSQL("drop table if exists reservas");
        db.execSQL("drop table if exists productos");
        db.execSQL("drop table if exists productos_upgrade");
        db.execSQL("drop table if exists brazaletes");
        db.execSQL("drop table if exists abordado");
        db.execSQL("drop table if exists upgrade");
        db.execSQL("drop table if exists brazalete_asignacion");
        db.execSQL("drop table if exists forma_de_pago");
        db.execSQL("drop table if exists botes");
        db.execSQL("drop table if exists botes_tour");
        db.execSQL("drop table if exists temporal_upg");
        db.execSQL("drop table if exists checkin");
        db.execSQL("drop table if exists upgrade_detalle");
        db.execSQL("drop table if exists tipo_operacion_caja");
        db.execSQL("drop table if exists caja_denominacion");


        onCreate(db);


    }

    public void clean_database(){
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.execSQL("delete from usuarios");
        dbs.execSQL("delete from encabezado_caja");
        dbs.execSQL("delete from detalle_caja");
        dbs.execSQL("delete from reservas");
        dbs.execSQL("delete from productos");
        dbs.execSQL("delete from productos_upgrade");
        dbs.execSQL("delete from brazaletes");
        dbs.execSQL("delete from abordado");
        dbs.execSQL("delete from upgrade");
        dbs.execSQL("delete from brazalete_asignacion");
        dbs.execSQL("delete from forma_de_pago");
        dbs.execSQL("delete from botes");
        dbs.execSQL("delete from botes_tour");
        dbs.execSQL("delete from checkin");
        dbs.execSQL("delete from temporal_upg");
        dbs.execSQL("delete from upgrade_detalle");
        dbs.execSQL("delete from tipo_operacion_caja");
        dbs.execSQL("delete from caja_denominacion");
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



        String consulta = "select id_rva,orden_servicio,reservaDetalle,cupon,agencia,id_producto,producto,adulto,menor,infante,nombre_cliente,hotel,habi,status,observaciones,idioma,idioma_icono from reservas "+ consulta_where+ " order by cupon";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                int id_rva=cursor.getInt(cursor.getColumnIndex("id_rva"));
                int reservaDetalle=cursor.getInt(cursor.getColumnIndex("reservaDetalle"));
                int orden_servicio=cursor.getInt(cursor.getColumnIndex("orden_servicio"));
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
                int status=cursor.getInt(cursor.getColumnIndex("status"));
                String obs=cursor.getString(cursor.getColumnIndex("observaciones"));
                int idioma=cursor.getInt(cursor.getColumnIndex("idioma"));
                int idioma_icono=cursor.getInt(cursor.getColumnIndex("idioma_icono"));

                datos.add(new modelo_lista_orden(id_rva,orden_servicio,reservaDetalle,cupon,agencia,id_prodcuto_padre,producto1,adulto,menor,infante,nombre_cliente,hotel,habi,obs,idioma,idioma_icono,status));
             }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public ArrayList<String> getTipo_operacionCaja_entrada( int funcion ){

       ArrayList<String> tipos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();
        String consulta="";

        if (funcion==1) {
             consulta = "select desc from tipo_operacion_caja where tipo='E'";
            }else {
             consulta = "select desc from tipo_operacion_caja where tipo='S'";
        }
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                tipos.add(cursor.getString(cursor.getColumnIndex("desc")));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return tipos;

    }

    public ArrayList<modelo_spinner_productos_upg> getProductos_upgrade( int producto_padre){
        ArrayList<modelo_spinner_productos_upg> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_producto,desc,precio_ad,precio_me,precio_in from productos,productos_upgrade where idTour="+producto_padre+" and id_producto = idTourCombinacion";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                datos.add(new modelo_spinner_productos_upg(cursor.getString(cursor.getColumnIndex("desc"))
                        ,cursor.getInt(cursor.getColumnIndex("id_producto")),
                        cursor.getDouble(cursor.getColumnIndex("precio_ad")),
                        cursor.getDouble(cursor.getColumnIndex("precio_me")),
                        cursor.getDouble(cursor.getColumnIndex("precio_in"))
                ));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public ArrayList<modelo_spinner_productos_upg> getProducto_precio( int producto_padre){
        ArrayList<modelo_spinner_productos_upg> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_producto,desc,precio_ad,precio_me,precio_in from productos where id_producto="+producto_padre;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                datos.add(new modelo_spinner_productos_upg(cursor.getString(cursor.getColumnIndex("desc"))
                        ,cursor.getInt(cursor.getColumnIndex("id_producto")),
                        cursor.getDouble(cursor.getColumnIndex("precio_ad")),
                        cursor.getDouble(cursor.getColumnIndex("precio_me")),
                        cursor.getDouble(cursor.getColumnIndex("precio_in"))
                ));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public ArrayList<modelo_spinner_productos_upg> getProductos(){
        ArrayList<modelo_spinner_productos_upg> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select id_producto,desc,precio_ad,precio_me,precio_in from productos";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                datos.add(new modelo_spinner_productos_upg(cursor.getString(cursor.getColumnIndex("desc"))
                        ,cursor.getInt(cursor.getColumnIndex("id_producto")),
                        cursor.getDouble(cursor.getColumnIndex("precio_ad")),
                        cursor.getDouble(cursor.getColumnIndex("precio_me")),
                        cursor.getDouble(cursor.getColumnIndex("precio_in"))
                ));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public int[] getProducto_idbrazalete( int producto_padre){
        int[] response = new int[3];

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select idBrazalete_Adulto,idBrazalete_menor,idBrazalete_complemento from productos where id_producto="+producto_padre;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                response[0] =  cursor.getInt(cursor.getColumnIndex("idBrazalete_Adulto"));
                response[1] =  cursor.getInt(cursor.getColumnIndex("idBrazalete_menor"));
                response[2] =  cursor.getInt(cursor.getColumnIndex("idBrazalete_complemento"));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return response;

    }

    public int[] getPax_BRazalete (String cupon) {


        int[] paxbr = new int[3];


        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select a.tipo as tipo, count(b.id_asignacion) as cant from brazaletes a,brazalete_asignacion b where b.cupon='" + cupon + "' and a.folio=b.folio and a.idBRazalete=b.idBrazalete group by a.tipo";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String tipo_1 = cursor.getString(cursor.getColumnIndex("tipo"));
            if (tipo_1.equals("adulto")) {
                paxbr[0]= cursor.getInt(cursor.getColumnIndex("cant"));
            } else if (tipo_1.equals("menor")) {
                paxbr[1]= cursor.getInt(cursor.getColumnIndex("cant"));
            }
        }
        cursor.close();
        return paxbr;
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
                double importe = cursor.getDouble(cursor.getColumnIndex("importe"));
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


        String consulta = "select id_fp, forma,moneda,monto_moneda,monto_mn from forma_de_pago where cupon='"+cupon+"' and id_upg=999991";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{

                int id_fp = cursor.getInt(cursor.getColumnIndex("id_fp"));
                String forma_pago = cursor.getString(cursor.getColumnIndex("forma"));
                String moneda = cursor.getString(cursor.getColumnIndex("moneda"));
                double monto_moneda = cursor.getDouble(cursor.getColumnIndex("monto_moneda"));
                double monto_mn = cursor.getDouble(cursor.getColumnIndex("monto_mn"));

                // Se cargan datos de la bd en el arraylist
                datos.add(new modelo_lista_formas_de_pago(id_fp,forma_pago,moneda,monto_moneda,monto_mn));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public ArrayList<modelo_lista_agregar_brazalete> getBrazaletes_asignados(String cupon, int id_tour, String producto_desc){
        ArrayList<modelo_lista_agregar_brazalete> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select idBrazalete,folio,color from brazalete_asignacion where cupon='"+cupon+"' and id_producto="+id_tour+"";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{

                int id_br=cursor.getInt(cursor.getColumnIndex("idBrazalete"));
                int folio =cursor.getInt(cursor.getColumnIndex("folio"));
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



        String consulta = "select idOpboat,id_bote,nombre,capacidad,abordado  from botes,botes_tour where id_bote=idEquipoBase and  idTour="+id_producto;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                String nombre_barco=cursor.getString(cursor.getColumnIndex("nombre"));
                int capac =cursor.getInt(cursor.getColumnIndex("capacidad"));
                int idOpBote =cursor.getInt(cursor.getColumnIndex("idOpboat"));
                int id_bote =cursor.getInt(cursor.getColumnIndex("id_bote"));
                int aborda =cursor.getInt(cursor.getColumnIndex("abordado"));
                datos.add(new modelo_lista_dbarcos(idOpBote,id_bote,nombre_barco,capac,aborda));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public ArrayList<modelo_lista_dbarcos> getBarcos_disponibles_show(){
        ArrayList<modelo_lista_dbarcos> datos = new ArrayList<>();

        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select idOpboat,id_bote,nombre,capacidad,abordado,id_producto  from botes ";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                String nombre_barco=cursor.getString(cursor.getColumnIndex("nombre"));
                int capac =cursor.getInt(cursor.getColumnIndex("capacidad"));
                int idOpboat =cursor.getInt(cursor.getColumnIndex("idOpboat"));
                int id_bote =cursor.getInt(cursor.getColumnIndex("id_bote"));
                int id_producto =cursor.getInt(cursor.getColumnIndex("id_producto"));
                int aborda =cursor.getInt(cursor.getColumnIndex("abordado"));
                datos.add(new modelo_lista_dbarcos(idOpboat,id_bote,nombre_barco,capac,aborda));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

    }

    public ArrayList<modelo_lista_dbrazaletes> getBrazaletes_disponibles(){

        ArrayList<modelo_lista_dbrazaletes> datos = new ArrayList<>();
        SQLiteDatabase dbs = this.getWritableDatabase();



        String consulta = "select folio,tipo,color from brazaletes where  status=1 order by folio,tipo";
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                int folio=cursor.getInt(cursor.getColumnIndex("folio"));
                String tipo=cursor.getString(cursor.getColumnIndex("tipo"));
                String color=cursor.getString(cursor.getColumnIndex("color"));



                datos.add(new modelo_lista_dbrazaletes(0,folio,tipo,color));
            }while(cursor.moveToNext());
        }
        dbs.close();
        return datos;

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

    public String inserta_upgrade_temporal(String cupon,int id_producto, String producto,int adulto, int menor, int infante, double importe_producto, int ad_cupon,int me_cupon, int in_cupon){

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

    public void borra_elemento_upg(int id_tmp){
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.delete("temporal_upg", "id_tmp=" + id_tmp, null);
        dbs.close();
    }

    public void borra_elemento_br(int id_br, int folio){
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.delete("brazalete_asignacion", "idBrazalete=" + id_br+" and folio="+folio, null);

        ContentValues up = new ContentValues();
        up.put("status",1);
        dbs.update("brazaletes",up,"idBrazalete=" + id_br+" and folio="+folio,null);

        dbs.close();


    }

    public void borra_elemento_fp(int id_fp){
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.delete("forma_de_pago", "id_fp=" + id_fp, null);


        dbs.close();


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

    public void inserta_apertura_caja(double importe,double importe_usd, String caja){

        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues up = new ContentValues();
        up.put("fecha_abre",dateFormat.format(date));
        up.put("usuario",Global.usuario);
        up.put("monto_inicial_mxn",importe);
        up.put("monto_inicial_usd",importe_usd);
        up.put("status",1);
        dbs.update("encabezado_caja",up,"caja='"+caja+"'",null);
        dbs.close();

    }

    public void inserta_cierre_caja(double importe,double importe_usd){
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues up = new ContentValues();
        up.put("fecha_cierre",dateFormat.format(date));
        up.put("usuario",Global.usuario);
        up.put("monto_final_usd",importe_usd);
        up.put("monto_final_mxn",importe);
        up.put("status",0);
        dbs.update("encabezado_caja",up,"id_caja='"+Global.id_caja+"'",null);

        dbs.delete("detalle_caja", "", null);
        dbs.close();
    }

    public void inserta_movimiento_detalle_caja(String movimiento,String operacion, double monto_moneda,String moneda,double monto_mn,String observacion  ){
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_caja",Global.id_caja);
        cv.put("fecha",dateFormat.format(date));
        cv.put("tipo_movimiento",movimiento);
        cv.put("monto_moneda", monto_moneda);
        cv.put("moneda",moneda);
        cv.put("monto_mn",monto_mn);
        cv.put("usuario",Global.usuario);
        cv.put("operacion", operacion);
        cv.put("observacion", observacion);
        //cv.put("forma_ingreso",forma_ingreso);
        dbs.insert("detalle_caja", null, cv);
        dbs.close();

    }

    public void inserta_denominacion_caja(String tipo, String tipo_operacion, int [] valores){

        int c=0;
        double[] denom;
            if(tipo.equals("B")) {
                 denom = new double [] {1000, 500, 200, 100, 50, 20};
            }else {
                 denom = new double [] {10, 5, 2, 1, 0.50, 0.20,0.10};
            }

        SQLiteDatabase dbs = this.getWritableDatabase();

        for(int valor:valores) {
            ContentValues cv = new ContentValues();
            cv.put("id_sesion", Global.id_sesion);
            cv.put("id_caja", Global.id_caja);
            cv.put("cant", valor);
            cv.put("denom", denom[c]);
            cv.put("tipo", tipo);
            cv.put("tipo_operacion", tipo_operacion);
            cv.put("usuario", Global.usuario);
            dbs.insert("caja_denominacion", null, cv);
            c++;
        }
        dbs.close();

    }

    public int getidCaja(String caja){
        int id_caja=0;
        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select id_caja from encabezado_caja  where caja='"+caja+"'";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            id_caja = cursor.getInt(cursor.getColumnIndex("id_caja"));
        }
        cursor.close();

        return id_caja;
    }

    public boolean getcajaAbierta(){
        boolean response = false;
        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select id_caja,caja from encabezado_caja  where usuario='"+Global.usuario+"' and status=1";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            Global.id_caja = cursor.getInt(cursor.getColumnIndex("id_caja"));
            Global.nombre_caja = cursor.getString(cursor.getColumnIndex("caja"));
            Global.status_caja=1;
            response=true;
        }
        cursor.close();

        return response;
    }

    public ArrayList<String> getCajas(){
        ArrayList<String> cajas = new ArrayList<>();
        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select caja from encabezado_caja  where status=0";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do{
                cajas.add(cursor.getString(cursor.getColumnIndex("caja")));
            }while(cursor.moveToNext());
        }
        cursor.close();

        return cajas;
    }

    public int getMontoinicial(){
        int monto_inicial=0;
        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select monto_inicial_usd,monto_inicial_usd from encabezado_caja  where id_caja='"+Global.id_caja+"'";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            monto_inicial = cursor.getInt(cursor.getColumnIndex("monto_inicial_usd"));
        }
        cursor.close();

        return monto_inicial;
    }

    public double getMontolibro(){
        double monto_libro=getMontoinicial();
        SQLiteDatabase dbs = this.getWritableDatabase();
        //Query entradas
        String querye = "select monto_mn from detalle_caja  where id_caja='"+Global.id_caja+"' and tipo_movimiento='E'";

        //Query salidas
        String querys = "select monto_mn from detalle_caja  where id_caja='"+Global.id_caja+"' and tipo_movimiento='S'";
        Cursor cursor = dbs.rawQuery(querye, null);

        if (cursor.moveToFirst()) {
            do {
                monto_libro = monto_libro + cursor.getDouble(cursor.getColumnIndex("monto_mn"));
            }while(cursor.moveToNext());
        }

         cursor = dbs.rawQuery(querys, null);

        if (cursor.moveToFirst()) {
            do {
                monto_libro = monto_libro - cursor.getDouble(cursor.getColumnIndex("monto_mn"));
            }while(cursor.moveToNext());
        }
        cursor.close();

        return monto_libro;
    }

    public double getMontoVenta(){
        double venta=0;
        SQLiteDatabase dbs = this.getWritableDatabase();

        //Query salidas
        String query = "select sum(monto_mn) as venta from detalle_caja  where id_caja='"+Global.id_caja+"' and tipo_movimiento='E' and operacion='Venta'";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                venta = cursor.getDouble(cursor.getColumnIndex("venta"));
            }while(cursor.moveToNext());
        }
        cursor.close();

        return venta;
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

    public String busca_brazalete (String folio, int id_tour,String producto_desc, String cupon, int adulto,int menor, int complemento,int idbr_adulto,int idbr_menor, int idbr_complemento){

        String encontrado = "nada";
        int[] idbr = getProducto_idbrazalete(id_tour);



        SQLiteDatabase dbs = this.getWritableDatabase();
        String query = "select a.tipo as tipo, count(b.id_asignacion) as cant from brazaletes a,brazalete_asignacion b where b.folio='"+folio+"' and a.folio=b.folio and a.idBRazalete=b.idBrazalete group by a.tipo";
        Cursor cursor = dbs.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String tipo_1=cursor.getString(cursor.getColumnIndex("tipo"));
            if(tipo_1.equals("adulto")) {
                adulto = adulto-cursor.getInt(cursor.getColumnIndex("cant"));
            }
            else if(tipo_1.equals("menor")) {
                menor = menor-cursor.getInt(cursor.getColumnIndex("cant"));
            }
            else if(tipo_1.equals("complemento")) {
                complemento = complemento-cursor.getInt(cursor.getColumnIndex("cant"));
            }
        }
        cursor.close();

        String sql_brazalete = "Select idBrazalete,tipo,color from brazaletes where folio="+folio+"  and idBrazalete in ("+idbr[0]+","+idbr[1]+","+idbr[2]+") and status=1 and id_usr='"+Global.user_id+"'";
         cursor = dbs.rawQuery(sql_brazalete, null);
        if (cursor.moveToFirst()) {
            do{
                String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                boolean continua = validaPax_brazalete(adulto,menor,complemento,tipo);

                if(continua) {
                    // inserta datos de brazalete
                    ContentValues cv = new ContentValues();
                    cv.put("cupon", cupon);
                    cv.put("idBrazalete", cursor.getInt(cursor.getColumnIndex("idBrazalete")));
                    cv.put("folio", folio);
                    cv.put("id_producto", id_tour);
                    cv.put("producto_desc", producto_desc);
                    cv.put("color", cursor.getString(cursor.getColumnIndex("color")));
                    cv.put("usuario", Global.usuario);
                    cv.put("abordado", 0);
                    dbs.insert("brazalete_asignacion", null, cv);

                    ContentValues up = new ContentValues();
                    up.put("status",0);
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
        ArrayList<modelo_lista_ws_brazalete> lbrazalete = new ArrayList<>();
        int id_bote=0, abordados=0;


        // Obtener id de bote seleccionado
        String consulta_id_bote = "select id_bote,abordado from botes where seleccion=1 and id_producto="+id_tour;
        Cursor cb = dbs.rawQuery(consulta_id_bote, null);

        if (cb.moveToFirst()) {
                id_bote = cb.getInt(cb.getColumnIndex("id_bote"));
                abordados = cb.getInt(cb.getColumnIndex("abordado"));
        }
        // obtener folios de brazaletes seleccionados
        String consulta = "select idBrazalete,folio from brazalete_asignacion where cupon='"+cupon+"' and id_producto="+id_tour;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                int folio =cursor.getInt(cursor.getColumnIndex("folio"));
                int idBrazalete =cursor.getInt(cursor.getColumnIndex("idBrazalete"));

                lbrazalete.add(new modelo_lista_ws_brazalete(idBrazalete,folio));
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

    public ArrayList<modelo_lista_ws_brazalete> getFolio_Brazalete_Abordar(String cupon,int id_tour){

        SQLiteDatabase dbs = this.getWritableDatabase();

        ArrayList<modelo_lista_ws_brazalete> lbrazalete = new ArrayList<>();

        // obtener folios de brazaletes seleccionados
        String consulta = "select idBrazalete,folio from brazalete_asignacion where cupon='"+cupon+"' and id_producto="+id_tour;
        Cursor cursor = dbs.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do{
                int folio =cursor.getInt(cursor.getColumnIndex("folio"));
                int idBrazalete =cursor.getInt(cursor.getColumnIndex("idBrazalete"));

                lbrazalete.add(new modelo_lista_ws_brazalete(idBrazalete,folio));

            }while(cursor.moveToNext());
        }

        dbs.close();


        return lbrazalete;
    }

    public int getBote_seleccionado(int id_tour) {

        SQLiteDatabase dbs = this.getWritableDatabase();
        int id_bote = 0;


        // Obtener id de bote seleccionado
        String consulta_id_bote = "select id_bote from botes,botes_tour where seleccion=1 and id_bote=idEquipoBase and  idTour="+id_tour;
        Cursor cb = dbs.rawQuery(consulta_id_bote, null);

        if (cb.moveToFirst()) {
            id_bote = cb.getInt(cb.getColumnIndex("id_bote"));
        }
        return id_bote;
    }

    public void cancela_Cupon(String cupon){

        // Marcar status de cupon en tabla de reservas
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues up = new ContentValues();

        up.put("status",2);
        dbs.update("reservas",up,"cupon='"+cupon+"'",null);

        dbs.close();

    }

    public void noshow_Cupon(String cupon){

        // Marcar status de cupon en tabla de reservas
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues up = new ContentValues();

        up.put("status",11);
        dbs.update("reservas",up,"cupon='"+cupon+"'",null);

        dbs.close();

    }


    ///////Inserts Web Service.

    public void ws_Inserta_datos_reservas(ArrayList<modelo_lista_orden> datos){

        SQLiteDatabase dbs = this.getWritableDatabase();

        for (modelo_lista_orden data: datos) {
            ContentValues cv1 = new ContentValues();
            cv1.put("orden_servicio", data.idOpboat);
            cv1.put("reservaDetalle", data.reservaDetalle);
            cv1.put("cupon", data.cupon);
            cv1.put("agencia", data.agencia);
            cv1.put("id_producto_padre", data.producto_padre);
            cv1.put("id_producto", data.producto_padre);
            cv1.put("producto", data.producto);
            cv1.put("adulto", data.adulto);
            cv1.put("menor", data.menor);
            cv1.put("infante", data.infante);
            cv1.put("nombre_cliente", data.nombre);
            cv1.put("hotel", data.hotel);
            cv1.put("habi", data.habi);
            cv1.put("observaciones", data.obs);
            cv1.put("idioma", data.idioma);
            cv1.put("idioma_icono", data.idioma_icono);
            cv1.put("fecha", dateFormat_reserva.format(date).toString());
            cv1.put("status", data.status);
            dbs.insert("reservas", null, cv1);
        }

        dbs.close();

    }

    public void ws_Inserta_datos_brazaletes(ArrayList<modelo_lista_dbrazaletes> datos){

        SQLiteDatabase dbs = this.getWritableDatabase();


        for (modelo_lista_dbrazaletes data: datos) {
            ContentValues cv1 = new ContentValues();
            cv1.put("idBrazalete", data.idBrazalete);
            cv1.put("folio", data.folio);
            cv1.put("tipo", data.tipo);
            cv1.put("color", data.color);
            cv1.put("id_usr", Global.user_id);
            cv1.put("status", 1);
            dbs.insert("brazaletes", null, cv1);
        }

        dbs.close();

    }

    public void ws_Inserta_datos_Tour(ArrayList<modelo_lista_Tour> datos){

        SQLiteDatabase dbs = this.getWritableDatabase();


        for (modelo_lista_Tour data: datos) {
            ContentValues cv1 = new ContentValues();
            cv1.put("id_producto", data.idTour);
            cv1.put("id_producto_padre", data.idTour);
            cv1.put("desc", data.nombreTour);
            cv1.put("idEquipoBase", data.idEquipoBase);
            cv1.put("idBrazalete_Adulto", data.idBrazaleteAd);
            cv1.put("idBrazalete_menor", data.idBrazaleteNino);
            cv1.put("idBrazalete_complemento", data.idBrazaleteComplemento);
            cv1.put("precio_ad", data.precioAdulto);
            cv1.put("precio_me", data.precioNino);
            cv1.put("precio_in", data.precioInfante);
            dbs.insert("productos", null, cv1);
        }

        dbs.close();

    }

    public void ws_Inserta_datos_TourUpgrade(ArrayList<modelo_lista_TourUpgrade> datos){

        SQLiteDatabase dbs = this.getWritableDatabase();


        for (modelo_lista_TourUpgrade data: datos) {
            ContentValues cv1 = new ContentValues();
            cv1.put("idTour", data.idTour);
            cv1.put("idTourCombinacion", data.idTourCombinacion);

            dbs.insert("productos_upgrade", null, cv1);
        }

        dbs.close();

    }

    public void ws_Inserta_datos_Equipo_Base(ArrayList<modelo_lista_dbarcos> datos){

        SQLiteDatabase dbs = this.getWritableDatabase();

        for (modelo_lista_dbarcos data: datos) {
            ContentValues cv1 = new ContentValues();
            cv1.put("idOpboat", data.idOpBoat);
            cv1.put("id_bote", data.idtourequipobase);
            cv1.put("nombre", data.nombre);
            cv1.put("capacidad", data.capacidad);
            cv1.put("abordado", data.abordar);

            dbs.insert("botes", null, cv1);
        }

        dbs.close();

    }

    public void ws_Inserta_datos_Equipo_Base_Tour(ArrayList<modelo_lista_tour_barcos> datos){

        SQLiteDatabase dbs = this.getWritableDatabase();

        for (modelo_lista_tour_barcos data: datos) {
            ContentValues cv1 = new ContentValues();
            cv1.put("idEquipoBase", data.idEquipoBase);
            cv1.put("idTour", data.idTour);


            dbs.insert("botes_tour", null, cv1);
        }

        dbs.close();

    }

    public void ws_Update_datos_Equipo_Base(ArrayList<modelo_lista_dbarcos> datos){
        SQLiteDatabase dbs = this.getWritableDatabase();
        for (modelo_lista_dbarcos data: datos) {
            ContentValues up = new ContentValues();
            up.put("abordado", data.abordar);
            dbs.update("botes", up, "id_bote=" + data.idtourequipobase, null);
        }

        dbs.close();


    }

    public void ws_Inserta_Cajas(ArrayList<modelo_lista_Caja> datos){

        SQLiteDatabase dbs = this.getWritableDatabase();

        for (modelo_lista_Caja data: datos) {
            ContentValues cv1 = new ContentValues();
            cv1.put("id_caja",data.idCaja);
            cv1.put("caja",data.nombreCaja);
            cv1.put("status",data.status);
            dbs.insert("encabezado_caja", null, cv1);
        }

        dbs.close();

    }

    public void ws_Inserta_tipoOperacionCaja(ArrayList<modelo_lista_tipoOperacionCaja> datos){

        SQLiteDatabase dbs = this.getWritableDatabase();

        for (modelo_lista_tipoOperacionCaja data: datos) {
            ContentValues cv1 = new ContentValues();
            cv1.put("id_op",data.idTipoOperacionCaja);
            cv1.put("desc",data.nomTipoOperacionCaja);
            cv1.put("tipo", data.tipo);
            dbs.insert("tipo_operacion_caja", null, cv1);
        }

        dbs.close();

    }

}

