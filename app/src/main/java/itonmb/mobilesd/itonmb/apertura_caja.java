package itonmb.mobilesd.itonmb;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.Utils.Utilerias;
import itonmb.mobilesd.itonmb.Utils.WsProcesos;
import itonmb.mobilesd.itonmb.modelo.modelo_spinner_productos_upg;


public class apertura_caja extends BaseMenu
      {

          Spinner spi_caja;
          EditText txt_init_caja,txt_init_caja_usd;
          Button btn_conf_ac,btn_canc_ac,btn_billetes,btn_monedas;
          View layout_popup;
          int pesos,usd;
          int[] billetes,monedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.content_apertura_caja, contentFrameLayout);
        toolbar.setTitle("Apertura de Caja");
        //Dibujado de spinners con style custom

        findview();
        set_triggers();
        prepara_spinner();

    }

          private void findview (){

              btn_conf_ac= (Button) findViewById(R.id.btn_conf_ac);
              btn_canc_ac= (Button) findViewById(R.id.btn_canc_ac);


              btn_billetes= (Button) findViewById(R.id.btn_billetes);
              btn_monedas= (Button) findViewById(R.id.btn_monedas);

              spi_caja= (Spinner) findViewById(R.id.spin_caja);

              txt_init_caja = (EditText) findViewById(R.id.txt_init_caja);
              txt_init_caja_usd = (EditText) findViewById(R.id.txt_init_caja_usd);

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

                      pesos = Utilerias.toNumero(txt_init_caja.getText().toString());
                      usd = Utilerias.toNumero(txt_init_caja_usd.getText().toString());

                      dbs.inserta_apertura_caja(pesos,usd,spi_caja.getSelectedItem().toString());
                      Global.status_caja=1;
                      Global.id_caja=dbs.getidCaja(spi_caja.getSelectedItem().toString());
                      Global.nombre_caja=spi_caja.getSelectedItem().toString();
                      new apertura_caja.AbreCaja().execute();

                      // Abrir busqueda de cupon
                      Intent intent_serv = new Intent(getApplicationContext(), search_orden.class);
                      startActivity(intent_serv);
                  }
              });
          }


          private void prepara_spinner(){

              ArrayList<String> data= dbs.getCajas();
              ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(), R.layout.style_spinner_item) {
                  @Override
                  public View getView(int position, View convertView, ViewGroup parent) {
                      View v = super.getView(position, convertView, parent);
                      return v;
                  }

                  @Override
                  public int getCount() {
                      return super.getCount(); // you dont display last item. It is used as hint.
                  }

              };
              adapter.setDropDownViewResource(R.layout.style_spinner_item);

              for (String caja: data) {
               adapter.add(caja);
              }

              spi_caja.setAdapter(adapter);

          }

          private PopupWindow popup_window_billetes(){
              final PopupWindow pwindo;
              final EditText txtb_1000,txtb_500,txtb_200,txtb_100,txtb_50,txtb_20;
              Button btn_aceptar;


              LayoutInflater inflat = (LayoutInflater) apertura_caja.this
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


              LayoutInflater inflat = (LayoutInflater) apertura_caja.this
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

          private class AbreCaja extends AsyncTask<Integer, String, String> {
              ProgressDialog progressDialog = new ProgressDialog(apertura_caja.this);

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
                  if(billetes!=null) {
                      dbs.inserta_denominacion_caja("B", "A", billetes);
                  }
                  if(monedas!=null) {
                      dbs.inserta_denominacion_caja("M", "A", monedas);
                  }

              }

              @Override
              protected String doInBackground(Integer... params) {
                  String resp="";
                  WsProcesos ws = new WsProcesos();
                  Global.id_sesion= ws.WSAbrir_Caja();
                  ws.WSArqueo_Abrir_Caja(billetes,monedas,pesos,usd);



                  return resp;
              }
          }


}
