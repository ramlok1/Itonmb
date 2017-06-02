package itonmb.mobilesd.itonmb;

import itonmb.mobilesd.itonmb.adapters.adapter_lista_servicio;
import itonmb.mobilesd.itonmb.modelo.*;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class listado_orden extends BaseMenu {
    ArrayList<modelo_lista_orden> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_listado_orden, contentFrameLayout);
        toolbar.setTitle("Apertura de Caja");
        data = (ArrayList<modelo_lista_orden>)getIntent().getSerializableExtra("datos");


        genera_lista_servicio();
    }

    private void genera_lista_servicio() {

        final ArrayList<modelo_lista_orden> datos = new ArrayList<>();

        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));
        datos.add(new modelo_lista_orden("2365","Expedia","Scale Isla Mujeres",2,2,1,"Conrado Gonzalez","Grand Oasis","2563",123.36));



            ListView lay_servicio = (ListView) findViewById(R.id.lv_datos_servicio);
            adapter_lista_servicio adapter = new adapter_lista_servicio(listado_orden.this, datos);
            lay_servicio.setAdapter(adapter);
        }


}
