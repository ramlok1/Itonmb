package itonmb.mobilesd.itonmb;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.DB.Inserta_datos_pruebas;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.Utils.WsProcesos;

public class login_main extends AppCompatActivity {

    Button btn_login;

    DBhelper dbs ;
    TextView txt_usr,txt_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        findviews();
        setTriggers();
        dbs = new DBhelper(getApplicationContext());

        /////////////////////////////////////////////////////
        // Datos pruebas
        dbs.clean_database();
           Inserta_datos_pruebas datos = new Inserta_datos_pruebas(getApplicationContext());
           datos.inserta_datos_pruebas();
        // Oculta teclado
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void findviews(){
        btn_login = (Button) findViewById(R.id.btn_login_entrar);
        txt_usr = (TextView) findViewById(R.id.txt_login_user);
        txt_pwd = (TextView) findViewById(R.id.txt_login_pass);

    }
    private void setTriggers(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /*Intent intent =
                        new Intent(getApplicationContext(), apertura_caja.class);
                startActivity(intent);*/
              String v_user = txt_usr.getText().toString().toLowerCase();
                String v_pwd = txt_pwd.getText().toString().toLowerCase();

                if (v_user.equals("")||v_pwd.equals("")) {
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "Favor de llenar todos los campos.", R.drawable.error, "#fe3939").show();

                } else {
                    String nombre = dbs.getLogin(v_user, v_pwd);

                    if (nombre.equals("")) {
                        Snackmsg bar = new Snackmsg();
                        bar.getBar(v, "Usuario o Contraseña invalidos, favor de revisar.", R.drawable.error, "#fe3939").show();
                    } else {
                        Snackmsg bar = new Snackmsg();
                        bar.getBar(v, "Bienvenido al sistema: " + nombre, R.drawable.sucsess, "#5fba7d").show();
                        Global.usuario=v_user;
                        Global.usuario_nombre =nombre;


                        new login_main.DatosCupones().execute();





                    }


                }
            }
        });


    }

    Handler procHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    // calling to this function from other pleaces
                    // The notice call method of doing things
                    break;

                case 5:
                    if(!dbs.getcajaAbierta()) {
                        Intent intent =  new Intent(getApplicationContext(), apertura_caja.class);
                        startActivity(intent);
                    } else{
                        Intent intent =  new Intent(getApplicationContext(), search_orden.class);
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private class DatosCupones extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String resp) {
            procHandler.sendEmptyMessage(0);
        }

        @Override
        protected String doInBackground(String... params) {
            String resp="";
            WsProcesos ws = new WsProcesos();
            ws.WSObtenerReservas(getApplicationContext(),"");


            return resp;
        }
    }
}
