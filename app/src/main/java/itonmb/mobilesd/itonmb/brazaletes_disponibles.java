package itonmb.mobilesd.itonmb;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_brazaletes_disp;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbrazaletes;

public class brazaletes_disponibles extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_brazaletes_disponibles, contentFrameLayout);
        toolbar.setTitle("Disponibilidad de Brazaletes");

        genera_lista_brazaletes();
    }

    private void genera_lista_brazaletes() {

        final ArrayList<modelo_lista_dbrazaletes> datos = new ArrayList<>();

        datos.add(new modelo_lista_dbrazaletes(50,"Extreme","#f5054e"));
        datos.add(new modelo_lista_dbrazaletes(50,"Ecopark","#f47004"));
        datos.add(new modelo_lista_dbrazaletes(50,"Selfinus","#17a86f"));
        datos.add(new modelo_lista_dbrazaletes(50,"Chichen","#000000"));
        datos.add(new modelo_lista_dbrazaletes(50,"Basico","#0575f3"));




        ListView lay_braza_disp = (ListView) findViewById(R.id.list_brazaletes_disponibles);
        adapter_lista_brazaletes_disp adapter = new adapter_lista_brazaletes_disp(brazaletes_disponibles.this, datos);
        lay_braza_disp.setAdapter(adapter);
    }
}
