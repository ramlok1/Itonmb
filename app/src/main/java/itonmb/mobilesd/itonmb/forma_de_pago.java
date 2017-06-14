package itonmb.mobilesd.itonmb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.modelo.modelo_spinner_productos_upg;

public class forma_de_pago extends BaseMenu {

    DBhelper dbs ;
    String cupon,autoriza_descuento;
    Button btn_pagar,btn_cancelar_upgrade ;
    int importe_total;
    double importe_final;
    Spinner spi_forma_pago,spi_comprobante;
    TextView txt_monto_forma_pago,txt_cambio_forma_pago,txt_total_pago_upgrade_fp;
    EditText txt_descuento_forma_pago,txt_recibido_forma_pago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_forma_de_pago, contentFrameLayout);
        toolbar.setTitle("Forma de Pago");
        dbs = new DBhelper(getApplicationContext());
        cupon= Global.cupon;
        Bundle extras = getIntent().getExtras();
        importe_total= extras.getInt("total");
        importe_final= extras.getInt("total");

        findview();
        set_triggers();
        prepara_spinner();

        txt_monto_forma_pago.setText(Integer.toString(importe_total));
        txt_total_pago_upgrade_fp.setText(Double.toString(importe_final));

        // Oculta teclado
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void findview (){

        btn_pagar= (Button) findViewById(R.id.btn_pagar_upgrade);
        btn_cancelar_upgrade= (Button) findViewById(R.id.btn_cancelar_upgrade);

        spi_comprobante= (Spinner) findViewById(R.id.spi_comprobante);
        spi_forma_pago= (Spinner) findViewById(R.id.spi_forma_pago);

        txt_monto_forma_pago = (TextView) findViewById(R.id.txt_monto_forma_pago);
        txt_cambio_forma_pago = (TextView) findViewById(R.id.txt_cambio_forma_pago);
        txt_total_pago_upgrade_fp = (TextView) findViewById(R.id.txt_total_pago_upgrade_fp);

        txt_descuento_forma_pago = (EditText) findViewById(R.id.txt_descuento_forma_pago);
        txt_recibido_forma_pago = (EditText) findViewById(R.id.txt_recibido_forma_pago);

    }

    private void set_triggers(){

        btn_cancelar_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbs.cancela_upgrade(cupon);
                Intent intent = new Intent(getApplicationContext(), listado_orden.class);
                startActivity(intent);
            }
        });

        btn_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forma_pago = spi_forma_pago.getSelectedItem().toString();
                double monto = importe_final;
                int descuento = Integer.parseInt(txt_descuento_forma_pago.getText().toString());
                int recibido = Integer.parseInt(txt_recibido_forma_pago.getText().toString());
                double cambio = Double.parseDouble(txt_cambio_forma_pago.getText().toString());

                int id_upg = dbs.inserta_upgrade(cupon,importe_final);
                dbs.inserta_forma_pago(id_upg,cupon,forma_pago,monto,descuento,recibido,cambio);

                Intent intent = new Intent(getApplicationContext(), listado_orden.class);
                startActivity(intent);
            }
        });

        txt_descuento_forma_pago.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    AlertDialog.Builder builder = new AlertDialog.Builder(forma_de_pago.this);
                    builder.setTitle("Autoriza descuento: ");
                    builder.setCancelable(false);

                        // Set up the input
                    final EditText input = new EditText(forma_de_pago.this);
                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setMaxLines(1);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            autoriza_descuento = input.getText().toString();
                            int descuento = Integer.parseInt(txt_descuento_forma_pago.getText().toString());
                            importe_final= importe_total - Math.round(((descuento/100.0)*importe_total));
                            txt_total_pago_upgrade_fp.setText(Double.toString(importe_final));
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txt_descuento_forma_pago.setText("0");
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            }
        });

        txt_recibido_forma_pago.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    int recibido = Integer.parseInt(txt_recibido_forma_pago.getText().toString());
                    if(recibido!=0) {
                        int importe_cambio = recibido - importe_total;
                        txt_cambio_forma_pago.setText(Integer.toString(importe_cambio));
                    }
                }
            }
        });
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
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };
        adapter_fp.setDropDownViewResource(R.layout.style_spinner_item);

        adapter_fp.add("Efectivo");
        adapter_fp.add("Efectivo");


        spi_forma_pago.setAdapter(adapter_fp);
        spi_forma_pago.setSelection(adapter_fp.getCount()); //display hint

        ArrayAdapter<String> adapter_cp = new ArrayAdapter<String>(getApplicationContext(), R.layout.style_spinner_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };
        adapter_cp.setDropDownViewResource(R.layout.style_spinner_item);

        adapter_cp.add("Ticket");
        adapter_cp.add("Ticket");


        spi_comprobante.setAdapter(adapter_cp);
        spi_comprobante.setSelection(adapter_cp.getCount()); //display hint
    }
}
