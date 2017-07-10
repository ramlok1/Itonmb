package itonmb.mobilesd.itonmb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_barcos_abordar;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbarcos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_ws_brazalete;

public class barcos_abordar extends BaseMenu {

    Button btn_abordar;
    DBhelper dbs;
    int id_tour,total_pax, total_pax_cupon;
    String cupon;
    View snkv;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_barcos_abordar, contentFrameLayout);
        toolbar.setTitle("Abordar Barco");

        dbs = new DBhelper(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        cupon = Global.cupon;
        id_tour=extras.getInt("producto");
        total_pax=extras.getInt("total_pax");
        total_pax_cupon=extras.getInt("total_pax_cupon");

        findviews();
        set_triggers();
        genera_lista_barcos();

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
    private void genera_lista_barcos() {

        ArrayList<modelo_lista_dbarcos> datos = dbs.getBarcos_disponibles(id_tour);



        ListView lay_barcos_abordar = (ListView) findViewById(R.id.list_barcos_abordar);
        adapter_lista_barcos_abordar adapter = new adapter_lista_barcos_abordar(barcos_abordar.this, datos);
        lay_barcos_abordar.setAdapter(adapter);
    }
    private void findviews(){

        btn_abordar = (Button) findViewById(R.id.btn_abordar);
    }
    private void set_triggers(){

        btn_abordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dbs.valida_barco_seleccion()) {
                    snkv=v;
                    new barcos_abordar.Abordar().execute();
                }else{
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "Error en seleccion de barco.", R.drawable.warn, "#f9db59").show();
                }
            }
        });
    }

    private class Abordar extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(barcos_abordar.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Actualizando lista de barcos...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String resp) {
            progressDialog.dismiss();
            if(resp.equals("ok")) {
                Intent intent = new Intent(getApplicationContext(), listado_orden.class);
                startActivity(intent);
            }else {
                Snackmsg bar = new Snackmsg();
                bar.getBar(snkv, "Barco Lleno...", R.drawable.warn, "#f9db59").show();

            }

        }

        @Override
        protected String doInBackground(String... params) {
            String resp="";
            boolean valida = ws.WSValida_Capacidad_Bote("",dbs.getBote_seleccionado(id_tour),total_pax);
            if(valida){
                ArrayList<modelo_lista_ws_brazalete> dato = dbs.getFolio_Brazalete_Abordar(cupon,id_tour);
                for(modelo_lista_ws_brazalete data:dato) {
                    ws.WSUpdate_Brazalete(data.idBrazalete,data.folio);
                }
                int[] px= dbs.getPax_BRazalete(cupon);

                ws.WSinserta_detalleOpBoat_Abordar(Global.orden_de_servicio,Global.reservadetalle,px[0] , px[1] , 0 ,"2017/07/04" ,Global.user_id);
                dbs.inserta_abordaje_in(cupon,total_pax,id_tour,total_pax_cupon);
              resp="ok";
            }
            return resp;
        }
    }


}
