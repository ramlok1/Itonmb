package itonmb.mobilesd.itonmb;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.adapters.adapter_lista_barcos_abordar;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbarcos;

public class barcos_abordar extends BaseMenu {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_barcos_abordar, contentFrameLayout);
        toolbar.setTitle("Abordar Barco");

        genera_lista_barcos();
    }

    private void genera_lista_barcos() {

        final ArrayList<modelo_lista_dbarcos> datos = new ArrayList<>();

        datos.add(new modelo_lista_dbarcos("Don Diego",50,45,45));
        datos.add(new modelo_lista_dbarcos("Mar y Arena",40,17,45));
        datos.add(new modelo_lista_dbarcos("The Big",60,17,45));
        datos.add(new modelo_lista_dbarcos("Saga Boy",70,17,45));
        datos.add(new modelo_lista_dbarcos("Just Mars",60,17,45));
        datos.add(new modelo_lista_dbarcos("Mar y Arena",30,17,45));
        datos.add(new modelo_lista_dbarcos("Just Mars",50,17,45));
        datos.add(new modelo_lista_dbarcos("The Big",40,17,45));



        ListView lay_barcos_abordar = (ListView) findViewById(R.id.list_barcos_abordar);
        adapter_lista_barcos_abordar adapter = new adapter_lista_barcos_abordar(barcos_abordar.this, datos);
        lay_barcos_abordar.setAdapter(adapter);
    }
}
