package itonmb.mobilesd.itonmb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Utilerias;
import itonmb.mobilesd.itonmb.Utils.WsProcesos;

public class cerrar_caja extends BaseMenu {

    TextView txt_caja_inicial,txt_caja_libro,txt_venta_dia,txt_diferencia;
    EditText txt_monto_cierre,txt_monto_cierre_usd;
    Button btn_conf_ac,btn_canc_ac,btn_billetes,btn_monedas;
    View layout_popup;
    int[] billetes= {0,0,0,0,0,0};
    int[] monedas={0,0,0,0,0,0};
    String pesos,usd,inicial,libros,diferencia,venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_cerrar_caja, contentFrameLayout);
        toolbar.setTitle("Cerrar Caja");

        dbs = new DBhelper(getApplicationContext());

        findview();
        set_triggers();
        txt_caja_inicial.setText(Integer.toString(dbs.getMontoinicial()));
        txt_caja_libro.setText(Double.toString(dbs.getMontolibro()));
        txt_venta_dia.setText(Double.toString(dbs.getMontoVenta()));

    }

    private void findview (){

        btn_conf_ac= (Button) findViewById(R.id.btn_conf_cierra_caja);
        btn_canc_ac= (Button) findViewById(R.id.btn_canc_cierra_caja);


        btn_billetes= (Button) findViewById(R.id.btn_billetes);
        btn_monedas= (Button) findViewById(R.id.btn_monedas);



        txt_caja_inicial = (TextView) findViewById(R.id.txt_caja_inicial_cierre);
        txt_caja_libro = (TextView) findViewById(R.id.txt_caja_libro);
        txt_venta_dia = (TextView) findViewById(R.id.txt_venta_dia);

        txt_diferencia = (TextView) findViewById(R.id.txt_diferencia);

        txt_monto_cierre = (EditText) findViewById(R.id.txt_monto_final_mxn);
        txt_monto_cierre_usd = (EditText) findViewById(R.id.txt_monto_final_usd);

    }
    private void set_triggers() {
        btn_billetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow pwin = popup_window_billetes();
                pwin.showAtLocation(layout_popup, Gravity.CENTER, 0, -20);

            }
        });

        btn_monedas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow pwin = popup_window_monedas();
                pwin.showAtLocation(layout_popup, Gravity.CENTER, 0, -20);

            }
        });

        btn_conf_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(billetes!=null) {
                    dbs.inserta_denominacion_caja("B", "C", billetes);
                }
                if(monedas!=null) {
                    dbs.inserta_denominacion_caja("M", "C", monedas);
                }
                pesos= txt_monto_cierre.getText().toString();
                usd =txt_monto_cierre_usd.getText().toString();
                inicial=txt_caja_inicial.getText().toString();
                libros=txt_caja_libro.getText().toString();
                diferencia=txt_diferencia.getText().toString();
                venta=txt_venta_dia.getText().toString();

                new cerrar_caja.CerrarCaja().execute();
                dbs.inserta_cierre_caja(Utilerias.toDouble(pesos),Utilerias.toDouble(usd));
                Global.status_caja=0;
                Global.id_caja=0;
                dbs.clean_databaseall();

                // Abrir busqueda de cupon
                Intent intent_serv = new Intent(getApplicationContext(), login_main.class);
                startActivity(intent_serv);
            }
        });

        btn_canc_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_serv = new Intent(getApplicationContext(), listado_orden.class);
                startActivity(intent_serv);

            }
        });

        txt_monto_cierre_usd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                double diferencia = Utilerias.toDouble(s.toString())-Utilerias.toDouble(txt_caja_libro.getText().toString());
                txt_diferencia.setText(Double.toString(diferencia));
                if(diferencia<0) {
                 txt_diferencia.setTextColor(Color.parseColor("#ea3c3b"));
                }else if(diferencia>0){
                    txt_diferencia.setTextColor(Color.parseColor("#0b8043"));
                }else{
                    txt_diferencia.setTextColor(Color.parseColor("#000000"));
                }

            }
        });
    }

    private PopupWindow popup_window_billetes(){
        final PopupWindow pwindo;
        final EditText txtb_1000,txtb_500,txtb_200,txtb_100,txtb_50,txtb_20;
        Button btn_aceptar;


        LayoutInflater inflat = (LayoutInflater) cerrar_caja.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout_popup = inflat.inflate(R.layout.popup_caja_billetes,
                (ViewGroup) findViewById(R.id.layout_principal_pop_billete));


        // Elementos del popup
        txtb_1000 = (EditText) layout_popup.findViewById(R.id.txtb_1000);
        txtb_500 = (EditText) layout_popup.findViewById(R.id.txtb_500);
        txtb_200 = (EditText) layout_popup.findViewById(R.id.txtb_200);
        txtb_100 = (EditText) layout_popup.findViewById(R.id.txtb_100);
        txtb_50 = (EditText) layout_popup.findViewById(R.id.txtb_50);
        txtb_20 = (EditText) layout_popup.findViewById(R.id.txtb_20);

        btn_aceptar = (Button)layout_popup.findViewById(R.id.btn_aceptar_b);



        pwindo = new PopupWindow(layout_popup, 1200, 200, true);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                billetes = new int[] {
                        Utilerias.toNumero(txtb_1000.getText().toString()),
                        Utilerias.toNumero(txtb_500.getText().toString()),
                        Utilerias.toNumero(txtb_200.getText().toString()),
                        Utilerias.toNumero(txtb_100.getText().toString()),
                        Utilerias.toNumero(txtb_50.getText().toString()),
                        Utilerias.toNumero(txtb_20.getText().toString())
                };
                pwindo.dismiss();
            }
        });




        return pwindo;
    }
    private PopupWindow popup_window_monedas(){
        final PopupWindow pwindo;
        final EditText txtm10,txtm5,txtm2,txtm1,txtm_50,txtm_20,txtm_10;
        Button btn_aceptar;


        LayoutInflater inflat = (LayoutInflater) cerrar_caja.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout_popup = inflat.inflate(R.layout.popup_caja_monedas,
                (ViewGroup) findViewById(R.id.layout_principal_pop_moneda));


        // Elementos del popup
        txtm10 = (EditText) layout_popup.findViewById(R.id.txtm10);
        txtm5 = (EditText) layout_popup.findViewById(R.id.txtm5);
        txtm2 = (EditText) layout_popup.findViewById(R.id.txtm2);
        txtm1 = (EditText) layout_popup.findViewById(R.id.txtm1);
        txtm_50 = (EditText) layout_popup.findViewById(R.id.txtm_50);
        txtm_20 = (EditText) layout_popup.findViewById(R.id.txtm_20);
        txtm_10 = (EditText) layout_popup.findViewById(R.id.txtm_10);

        btn_aceptar = (Button)layout_popup.findViewById(R.id.btn_aceptar_m);



        pwindo = new PopupWindow(layout_popup, 1200, 200, true);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monedas = new int[]{
                        Utilerias.toNumero(txtm10.getText().toString()),
                        Utilerias.toNumero(txtm5.getText().toString()),
                        Utilerias.toNumero(txtm2.getText().toString()),
                        Utilerias.toNumero(txtm1.getText().toString()),
                        Utilerias.toNumero(txtm_50.getText().toString()),
                        Utilerias.toNumero(txtm_20.getText().toString()),
                        Utilerias.toNumero(txtm_10.getText().toString())
                };
                pwindo.dismiss();
            }
        });




        return pwindo;
    }

    private class CerrarCaja extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(cerrar_caja.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Abriendo Caja...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String resp) {
            progressDialog.dismiss();

        }

        @Override
        protected String doInBackground(String... params) {
            String resp="";
            WsProcesos ws = new WsProcesos();
            ws.WSCerrar_Caja();
            ws.WSArqueo_Cerrar_Caja(billetes,monedas,pesos,usd,venta,inicial,libros,diferencia);



            return resp;
        }
    }
}
