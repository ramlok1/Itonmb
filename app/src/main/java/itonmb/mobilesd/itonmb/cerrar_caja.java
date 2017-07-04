package itonmb.mobilesd.itonmb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class cerrar_caja extends BaseMenu {

    TextView txt_caja_inicial,txt_caja_libro;
    EditText txt_monto_cierre;
    Button btn_conf_ac,btn_canc_ac,btn_billetes,btn_monedas;
    View layout_popup;
    int[] billetes,monedas;

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

    }

    private void findview (){

        btn_conf_ac= (Button) findViewById(R.id.btn_conf_cierra_caja);
        btn_canc_ac= (Button) findViewById(R.id.btn_canc_cierra_caja);


        btn_billetes= (Button) findViewById(R.id.btn_billetes);
        btn_monedas= (Button) findViewById(R.id.btn_monedas);



        txt_caja_inicial = (TextView) findViewById(R.id.txt_caja_inicial_cierre);
        txt_caja_libro = (TextView) findViewById(R.id.txt_caja_libro);

        txt_monto_cierre = (EditText) findViewById(R.id.txt_monto_final);

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

                dbs.inserta_denominacion_caja("B","C",billetes);
                dbs.inserta_denominacion_caja("M","C",monedas);
                dbs.inserta_cierre_caja(Utilerias.toDouble(txt_monto_cierre.getText().toString()));
                Global.status_caja=0;
                Global.id_caja=0;

                // Abrir busqueda de cupon
                Intent intent_serv = new Intent(getApplicationContext(), apertura_caja.class);
                startActivity(intent_serv);
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



        pwindo = new PopupWindow(layout_popup, 1000, 160, true);

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



        pwindo = new PopupWindow(layout_popup, 1000, 160, true);

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
}
