package itonmb.mobilesd.itonmb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.adapters.adapter_lista_upgrade_productos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;

public class agregar_brazalete extends BaseMenu {

    Button btn_fp_upgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_upgrade, contentFrameLayout);
        toolbar.setTitle("Upgrade");

        findviews();
        set_triggers();
        genera_lista_productos_seleccionados();
    }

    private void genera_lista_productos_seleccionados() {

        final ArrayList<modelo_lista_upgrade_productos> datos = new ArrayList<>();

        datos.add(new modelo_lista_upgrade_productos("Isla M. Regular F-II",4,2,0,300,300));
        datos.add(new modelo_lista_upgrade_productos("Isla M. PLUS",4,2,0,450,450));
        datos.add(new modelo_lista_upgrade_productos("Cozumel Plus",4,2,0,550,550));






        ListView lay_upgrades = (ListView) findViewById(R.id.list_productos_upgrade);
        adapter_lista_upgrade_productos adapter = new adapter_lista_upgrade_productos(agregar_brazalete.this, datos);
        lay_upgrades.setAdapter(adapter);
    }

    private void findviews(){

        btn_fp_upgrade = (Button) findViewById(R.id.btn_fp_upgrade);
    }

    private void set_triggers (){

        btn_fp_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), forma_de_pago.class);
                startActivity(intent);
            }
        });



    }
}
