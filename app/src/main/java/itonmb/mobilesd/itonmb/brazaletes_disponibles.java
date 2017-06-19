package itonmb.mobilesd.itonmb;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_brazaletes_disp;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbrazaletes;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;

public class brazaletes_disponibles extends BaseMenu {
    DBhelper dbs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_brazaletes_disponibles, contentFrameLayout);
        toolbar.setTitle("Disponibilidad de Brazaletes");
        dbs = new DBhelper(getApplicationContext());

        genera_lista_brazaletes();
    }

    private void genera_lista_brazaletes() {

        ArrayList<modelo_lista_dbrazaletes> datos = dbs.getBrazaletes_disponibles();

        ListView lay_braza_disp = (ListView) findViewById(R.id.list_brazaletes_disponibles);
        adapter_lista_brazaletes_disp adapter = new adapter_lista_brazaletes_disp(brazaletes_disponibles.this, datos);
        lay_braza_disp.setAdapter(adapter);
    }
}
