package itonmb.mobilesd.itonmb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.WsProcesos;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_barcos_abordar;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_barcos_disp;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_servicio;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbarcos;

public class barcos_disponibles extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_barcos_disponibles, contentFrameLayout);
        toolbar.setTitle("Disponibilidad de Barcos");

        genera_lista_barcos();
    }

    private void genera_lista_barcos() {

        ArrayList<modelo_lista_dbarcos> datos = dbs.getBarcos_disponibles_show();


        ListView lay_barcos_abordar = (ListView) findViewById(R.id.list_barcos_disponibles);
        adapter_lista_barcos_disp adapter = new adapter_lista_barcos_disp(barcos_disponibles.this, datos);
        lay_barcos_abordar.setAdapter(adapter);
    }


}
