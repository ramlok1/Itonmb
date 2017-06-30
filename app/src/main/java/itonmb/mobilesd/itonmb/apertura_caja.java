package itonmb.mobilesd.itonmb;


import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import itonmb.mobilesd.itonmb.Utils.BaseMenu;


public class apertura_caja extends BaseMenu
      {

          Spinner spi_caja;
          EditText txt_init_caja;
          Button btn_conf_ac,btn_canc_ac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.content_apertura_caja, contentFrameLayout);
        toolbar.setTitle("Apertura de Caja");
        //Dibujado de spinners con style custom

        findview();
        prepara_spinner();

    }

          private void findview (){

              btn_conf_ac= (Button) findViewById(R.id.btn_pago_fp);
              btn_canc_ac= (Button) findViewById(R.id.btn_cancelar_fp);

              spi_caja= (Spinner) findViewById(R.id.spin_caja);

              txt_init_caja = (EditText) findViewById(R.id.txt_monto_forma_pago);

          }

          private void set_triggers() {

          }


          private void prepara_spinner(){

              ArrayAdapter<String> adapter_fp = new ArrayAdapter<String>(getApplicationContext(), R.layout.style_spinner_item) {
                  @Override
                  public View getView(int position, View convertView, ViewGroup parent) {
                      View v = super.getView(position, convertView, parent);
                      return v;
                  }

                  @Override
                  public int getCount() {
                      return super.getCount(); // you dont display last item. It is used as hint.
                  }

              };
              adapter_fp.setDropDownViewResource(R.layout.style_spinner_item);

              adapter_fp.add("Caja 1");
              adapter_fp.add("Caja 2");

              spi_caja.setAdapter(adapter_fp);

          }

}
