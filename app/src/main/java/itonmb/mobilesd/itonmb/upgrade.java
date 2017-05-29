package itonmb.mobilesd.itonmb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.adapters.adapter_lista_upgrade_productos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;

public class upgrade extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_upgrade, contentFrameLayout);
        toolbar.setTitle("Upgrade");

        genera_lista_productos_seleccionados();
    }

    private void genera_lista_productos_seleccionados() {

        final ArrayList<modelo_lista_upgrade_productos> datos = new ArrayList<>();

        datos.add(new modelo_lista_upgrade_productos("Isla M. Regular F-II",4,2,0,300,300));
        datos.add(new modelo_lista_upgrade_productos("Isla M. PLUS",4,2,0,450,450));
        datos.add(new modelo_lista_upgrade_productos("Cozumel Plus",4,2,0,550,550));






        ListView lay_upgrades = (ListView) findViewById(R.id.list_productos_upgrade);
        adapter_lista_upgrade_productos adapter = new adapter_lista_upgrade_productos(upgrade.this, datos);
        lay_upgrades.setAdapter(adapter);
    }
}
