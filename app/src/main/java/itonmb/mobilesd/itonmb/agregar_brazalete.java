package itonmb.mobilesd.itonmb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_agregar_brazalete;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_agregar_brazalete;

public class agregar_brazalete extends BaseMenu {

    Button btn_asignar,btn_aceptar;
    int ad_cupon,me_cupon,in_cupon,id_tour;
    String cupon,producto_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_agregar_brazalete, contentFrameLayout);
        toolbar.setTitle("Upgrade");


        //Obtengo valores enviados por el activity anterior
        Bundle extras = getIntent().getExtras();
        cupon= Global.cupon;
        ad_cupon=Integer.parseInt(extras.getString("adulto"));
        me_cupon=Integer.parseInt(extras.getString("menor"));
        in_cupon=Integer.parseInt(extras.getString("infante"));
        in_cupon=Integer.parseInt(extras.getString("infante"));
        id_tour=Integer.parseInt(extras.getString("producto_padre"));
        producto_desc=extras.getString("producto_padre");

        findviews();
        set_triggers();
        genera_lista_productos_seleccionados();
    }

    private void genera_lista_productos_seleccionados() {

        final ArrayList<modelo_lista_agregar_brazalete> datos = new ArrayList<>();

       /* datos.add(new modelo_lista_agregar_brazalete("03156634","Isla M. Regular F-II","#e0920c"));
        datos.add(new modelo_lista_agregar_brazalete("03156635","Isla M. Regular F-II","#e0920c"));
        datos.add(new modelo_lista_agregar_brazalete("03156636","Isla M. Regular F-II","#e0920c"));*/






        ListView lay_upgrades = (ListView) findViewById(R.id.list_brazaletes_agregados);
        adapter_lista_agregar_brazalete adapter = new adapter_lista_agregar_brazalete(agregar_brazalete.this, datos);
        lay_upgrades.setAdapter(adapter);
    }

    private void findviews(){

        btn_asignar = (Button) findViewById(R.id.btn_asignar_brazalete);
        btn_aceptar = (Button) findViewById(R.id.btn_aceptar_brazalete);
    }

    private void set_triggers (){

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), barcos_abordar.class);
                startActivity(intent);
            }
        });

        btn_asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
