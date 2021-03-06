package itonmb.mobilesd.itonmb;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_servicio;
import itonmb.mobilesd.itonmb.modelo.*;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class listado_orden extends BaseMenu {
    ArrayList<modelo_lista_orden> data=new ArrayList<>();
    DBhelper dbs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_listado_orden, contentFrameLayout);
        toolbar.setTitle("Apertura de Caja");

        dbs = new DBhelper(getApplicationContext());
        data = dbs.getSearch_Cupones(Global.consulta_where);


        genera_lista_servicio();
    }

    private void genera_lista_servicio() {


            ListView lay_servicio = (ListView) findViewById(R.id.lv_datos_servicio);
            adapter_lista_servicio adapter = new adapter_lista_servicio(listado_orden.this, data);
            lay_servicio.setAdapter(adapter);
        }


}
