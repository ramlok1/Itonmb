package itonmb.mobilesd.itonmb;


import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import itonmb.mobilesd.itonmb.Utils.BaseMenu;


public class apertura_caja extends BaseMenu
      {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.content_apertura_caja, contentFrameLayout);
        toolbar.setTitle("Apertura de Caja");
        //Dibujado de spinners con style custom
        maqueta_spinners();

    }

    private void maqueta_spinners(){
        Spinner spinner_arqueo = (Spinner) findViewById(R.id.spin_arqueo);
        Spinner spinner_bill = (Spinner) findViewById(R.id.spin_billete);
        Spinner spienner_mon = (Spinner) findViewById(R.id.spin_moneda);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(apertura_caja.this, R.array.arqueo_array, R.layout.style_spinner_item);
        spinner_arqueo.setAdapter(adapter);
        spinner_bill.setAdapter(adapter);
        spienner_mon.setAdapter(adapter);
    }



}
