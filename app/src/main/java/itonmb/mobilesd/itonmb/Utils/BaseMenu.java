package itonmb.mobilesd.itonmb.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.R;
import itonmb.mobilesd.itonmb.barcos_disponibles;
import itonmb.mobilesd.itonmb.brazaletes_disponibles;
import itonmb.mobilesd.itonmb.cerrar_caja;
import itonmb.mobilesd.itonmb.forma_de_pago;
import itonmb.mobilesd.itonmb.ingresa_efectivo_caja;
import itonmb.mobilesd.itonmb.modelo.modelo_spinner_productos_upg;
import itonmb.mobilesd.itonmb.retirar_efectivo_caja;
import itonmb.mobilesd.itonmb.search_orden;

/**
 * Created by Conrado on 15/05/2017.
 */

public class BaseMenu extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public Toolbar toolbar;
    View lay_base;
    public DBhelper dbs ;
    public WsProcesos ws = new WsProcesos();
    String obs;
    double monto=0;
    int tp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apertura_caja);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        dbs = new DBhelper(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_buscar_serv:
                        if(Global.status_caja==1) {
                            Intent intent_serv = new Intent(getApplicationContext(), search_orden.class);
                            startActivity(intent_serv);
                        }else{
                            Toast.makeText(getApplicationContext(),"Caja Cerrada",Toast.LENGTH_SHORT).show();
                        }
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_ver_disp:
                        if(Global.status_caja==1) {

                            new BaseMenu.DatosBarcos().execute();

                        }else{
                            Toast.makeText(getApplicationContext(),"Caja Cerrada",Toast.LENGTH_SHORT).show();
                        }
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_ver_braz:
                        if(Global.status_caja==1) {
                        Intent intent_disp_br = new Intent(getApplicationContext(), brazaletes_disponibles.class);
                        startActivity(intent_disp_br);
                        }else{
                            Toast.makeText(getApplicationContext(),"Caja Cerrada",Toast.LENGTH_SHORT).show();
                        }
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.apertura_caja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ing_efe_caja) {
            if(Global.status_caja==1) {
                PopupWindow pwin = popup_ingreso_egreso_caja(1);
                pwin.showAtLocation(lay_base, Gravity.CENTER, 0, -20);
            }else{
                Toast.makeText(getApplicationContext(),"Caja Cerrada",Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawers();
            return true;
        } else if (id == R.id.ret_efe_caja) {
            if(Global.status_caja==1) {
            PopupWindow pwin = popup_ingreso_egreso_caja(2);
            pwin.showAtLocation(lay_base, Gravity.CENTER, 0, -20);
            }else{
                  Toast.makeText(getApplicationContext(),"Caja Cerrada",Toast.LENGTH_SHORT).show();
             }
            drawerLayout.closeDrawers();
            return true;
        } else if (id == R.id.cerrar_caja) {
            if(Global.status_caja==1) {
            Intent anIntent = new Intent(getApplicationContext(), cerrar_caja.class);
            startActivity(anIntent);
            }else{
                Toast.makeText(getApplicationContext(),"Caja Cerrada",Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawers();
            return true;
        }else if (id == R.id.consultar_caja) {
            PopupWindow pwin = popup_consulta_caja();
            pwin.showAtLocation(lay_base, Gravity.CENTER, 0, -20);
            drawerLayout.closeDrawers();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    private PopupWindow popup_ingreso_egreso_caja(final int funcion){
        final PopupWindow pwindo;



        LayoutInflater inflat = (LayoutInflater) BaseMenu.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        lay_base = inflat.inflate(R.layout.popup_ingresa_efectivo_caja,
                (ViewGroup) findViewById(R.id.layout_principal_pop_iec));


        Button btn_cancelar = (Button) lay_base.findViewById(R.id.btn_iec_cancelar);
        Button btn_confirmar = (Button) lay_base.findViewById(R.id.btn_iec_confirmar);
        final EditText txt_monto = (EditText) lay_base.findViewById(R.id.txt_iec_monto);
        final EditText txt_obs = (EditText) lay_base.findViewById(R.id.txt_iec_obs);
        txt_monto.setText("0");
        pwindo = new PopupWindow(lay_base, 900, 400, true);


        /// spinner de tipos de operaciones
        ///////////////////////////////////////////////////////////////////////////////
             final Spinner spi_operacion = (Spinner) lay_base.findViewById(R.id.spi_iec_t_operacion);
             final ArrayList<String> tipos = dbs.getTipo_operacionCaja_entrada(funcion);
             ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.style_spinner_item) {

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

        //Obtener descipciones
                 for (String dato: tipos) {
                          adapter.add(dato);
                     }
        spi_operacion.setAdapter(adapter);
        ////////////////////////////////////////////////////////////////////////////
        // Triggers de Popup
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwindo.dismiss();

            }
        });

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 monto=0;
                if(!Utilerias.isNull(txt_monto.getText().toString())) {
                    monto = Integer.parseInt(txt_monto.getText().toString());
                }
                obs = txt_obs.getText().toString();
                String operacion = spi_operacion.getSelectedItem().toString();

                if(monto!=0){
                    tp=funcion;
                    if(funcion==1){

                        dbs.inserta_movimiento_detalle_caja("E", operacion, monto, "USD", monto, obs);
                        pwindo.dismiss();
                    }else {
                        dbs.inserta_movimiento_detalle_caja("S", operacion, monto, "USD", monto, obs);
                        pwindo.dismiss();
                    }
                    new BaseMenu.movimientoCaja().execute();
                }else{
                    Toast.makeText(getApplicationContext(),"Monto no puede ser 0",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return pwindo;
    }

    private PopupWindow popup_consulta_caja(){
        PopupWindow pwindo;



        LayoutInflater inflat = (LayoutInflater) BaseMenu.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        lay_base = inflat.inflate(R.layout.popup_consultar_caja,
                (ViewGroup) findViewById(R.id.layout_principal_pop_iec));



        TextView txt_nombre_caja = (TextView) lay_base.findViewById(R.id.txt_nombre_caja);
        TextView txt_usuario_caja = (TextView) lay_base.findViewById(R.id.txt_usuario_caja);
        TextView txt_caja_inicial_consulta = (TextView) lay_base.findViewById(R.id.txt_caja_inicial_consulta);
        TextView txt_caja_libro_contulta = (TextView) lay_base.findViewById(R.id.txt_caja_libro_consulta);
        TextView txt_venta_consulta = (TextView) lay_base.findViewById(R.id.txt_venta_dia_consulta);
        TextView txt_status= (TextView) lay_base.findViewById(R.id.txt_status);


        if(Global.status_caja==1) {
            txt_nombre_caja.setText("Caja: " + Global.nombre_caja);
            txt_usuario_caja.setText("Usuario: " + Global.usuario_nombre);
            txt_caja_inicial_consulta.setText(Integer.toString(dbs.getMontoinicial()));
            txt_caja_libro_contulta.setText(Double.toString(dbs.getMontolibro()));
            txt_venta_consulta.setText(Double.toString(dbs.getMontoVenta()));
            txt_status.setText("Abierta");
            txt_status.setTextColor(Color.parseColor("#0b8043"));
        }else {
            txt_status.setText("Cerrada");
            txt_status.setTextColor(Color.parseColor("#ea3c3b"));
        }



        pwindo = new PopupWindow(lay_base, 1200, 300, true);
        // Closes the popup window when touch outside.
        pwindo.setOutsideTouchable(true);
        // Removes default background.
        pwindo.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        return pwindo;
    }

    private class DatosBarcos extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(BaseMenu.this);

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
            Intent intent_disp = new Intent(getApplicationContext(), barcos_disponibles.class);
            startActivity(intent_disp);

        }

        @Override
        protected String doInBackground(String... params) {
            String resp="";
            ws.WSObtenerEquipo_Base(getApplicationContext(),"",1);


            return resp;
        }
    }

    private class movimientoCaja extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(BaseMenu.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Actualizando caja...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String resp) {
            progressDialog.dismiss();

        }

        @Override
        protected String doInBackground(String... params) {
            String resp="";
            if(tp==1) {
                ws.WSInserta_detalle_caja(1, Double.toString(monto), 2, Double.toString(Global.TC), obs);
            }else{
                ws.WSInserta_detalle_caja(3, Double.toString(monto), 2, Double.toString(Global.TC), obs);
            }


            return resp;
        }
    }



}