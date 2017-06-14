package itonmb.mobilesd.itonmb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_barcos_abordar;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_agregar_brazalete;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbarcos;

public class barcos_abordar extends BaseMenu {

    Button btn_abordar;
    DBhelper dbs;
    int id_tour,total_pax;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_barcos_abordar, contentFrameLayout);
        toolbar.setTitle("Abordar Barco");

        dbs = new DBhelper(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        id_tour=extras.getInt("producto");
        total_pax=extras.getInt("total_pax");
        findviews();
        set_triggers();
        genera_lista_barcos();
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
                Intent intent = new Intent(getApplicationContext(), listado_orden.class);
                startActivity(intent);
            }
        });
    }
}
