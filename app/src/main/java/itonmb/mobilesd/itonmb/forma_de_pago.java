package itonmb.mobilesd.itonmb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_formas_de_pago;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_formas_de_pago;


public class forma_de_pago extends BaseMenu {

    DBhelper dbs ;
    String cupon,autoriza_descuento,producto_desc;
    Button btn_pago_fp,btn_cancelar_fp,btn_finalizar_fp,btn_descuento ;
    int importe_total,id_rva,tipo,id_upg=9999999,adulto,menor,infante,id_producto,total_pax, impuesto_muelle;
    double importe_final;
    Spinner spi_forma_pago,spi_divisa;
    TextView txt_cambio_forma_pago,txt_total_fp,txt_subtotal,txt_descuento,txt_imp_muelle,txt_saldo,txt_total_pagado;
    EditText txt_monto_forma_pago,txt_recibido_forma_pago;
    int[] upg_datos  = new int[4];


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
        id_rva= extras.getInt("id_rva");
        tipo= extras.getInt("tipo");
        adulto = extras.getInt("adulto");
        menor =extras.getInt("menor");
        infante = extras.getInt("infante");
        producto_desc = extras.getString("producto_desc");
        id_producto = extras.getInt("id_producto");

        findview();
        set_triggers();
        prepara_spinner();
        total_pax = dbs.getUpgrade_total_pax(cupon);
        impuesto_muelle = total_pax*Global.importe_muelle;
        importe_final=importe_final+impuesto_muelle;

        txt_subtotal.setText(Integer.toString(importe_total));
        txt_total_fp.setText(Double.toString(importe_final));
        txt_imp_muelle.setText(Integer.toString(impuesto_muelle));
        txt_descuento.setText(Integer.toString(0));
        txt_saldo.setText(Integer.toString(0));
        txt_total_pagado.setText(Integer.toString(0));
        txt_cambio_forma_pago.setText(Integer.toString(0));
        txt_recibido_forma_pago.setText(Integer.toString(0));

        if(tipo==2){
            btn_descuento.setEnabled(false);
        }

        // Oculta teclado
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void genera_lista_pagos() {

        ArrayList<modelo_lista_formas_de_pago> datos = dbs.getPagos(cupon);

        //Coloca total a pagar
       double total = dbs.getTotal_pagado(cupon);
        txt_total_pagado.setText(Double.toString(total));

        ListView lista_formas_pago = (ListView) findViewById(R.id.list_formas_pago);
        adapter_lista_formas_de_pago adapter = new adapter_lista_formas_de_pago(forma_de_pago.this, datos);
        lista_formas_pago.setAdapter(adapter);

    }

    private void findview (){

        btn_pago_fp= (Button) findViewById(R.id.btn_pago_fp);
        btn_cancelar_fp= (Button) findViewById(R.id.btn_cancelar_fp);
        btn_finalizar_fp= (Button) findViewById(R.id.btn_finalizar_fp);
        btn_descuento= (Button) findViewById(R.id.btn_descuento);

        spi_divisa= (Spinner) findViewById(R.id.spi_divisa);
        spi_forma_pago= (Spinner) findViewById(R.id.spi_forma_pago);

        txt_monto_forma_pago = (EditText) findViewById(R.id.txt_monto_forma_pago);
        txt_recibido_forma_pago = (EditText) findViewById(R.id.txt_recibido_forma_pago);

        txt_cambio_forma_pago = (TextView) findViewById(R.id.txt_cambio_forma_pago);
        txt_total_fp = (TextView) findViewById(R.id.txt_total_fp);
        txt_subtotal = (TextView) findViewById(R.id.txt_subtotal);
        txt_descuento = (TextView) findViewById(R.id.txt_descuento);
        txt_imp_muelle = (TextView) findViewById(R.id.txt_imp_muelle);

        txt_saldo = (TextView) findViewById(R.id.txt_saldo_fp);
        txt_total_pagado = (TextView) findViewById(R.id.txt_total_pagado);

    }

    private void set_triggers(){

        btn_cancelar_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo==1) {
                dbs.cancela_upgrade(cupon);
                }
                Intent intent = new Intent(getApplicationContext(), listado_orden.class);
                startActivity(intent);
            }
        });

        btn_pago_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forma_pago = spi_forma_pago.getSelectedItem().toString();
                String moneda = spi_divisa.getSelectedItem().toString();
                double monto_moneda = Integer.parseInt(txt_monto_forma_pago.getText().toString());
                double monto_mn = monto_moneda*18.45;
                int recibido = Integer.parseInt(txt_recibido_forma_pago.getText().toString());
                double cambio = Double.parseDouble(txt_cambio_forma_pago.getText().toString());

              /*  if(tipo==1) {
                    upg_datos = dbs.inserta_upgrade(cupon, importe_final, id_rva);
                    id_upg=upg_datos[0];
                    adulto=upg_datos[1];
                    menor=upg_datos[2];
                    infante=upg_datos[3];
                }*/
                dbs.inserta_forma_pago(999991,cupon,forma_pago,monto_moneda,monto_mn,18.45,moneda,recibido,cambio);
                genera_lista_pagos();


               /* Intent intent = new Intent(getApplicationContext(), agregar_brazalete.class);
                intent.putExtra("adulto",adulto);
                intent.putExtra("menor",menor);
                intent.putExtra("infante",infante);
                intent.putExtra("producto_desc",producto_desc);
                intent.putExtra("id_producto",id_producto);
                startActivity(intent);*/
            }
        });

       /* txt_descuento_forma_pago.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                            importe_final= impuesto_muelle+importe_total - Math.round(((descuento/100.0)*importe_total));
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
        });*/

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
        adapter_fp.add("Tarjeta");



        spi_forma_pago.setAdapter(adapter_fp);
        spi_forma_pago.setSelection(1); //display hint

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

        adapter_cp.add("Euro");
        adapter_cp.add("Dolar");
        adapter_cp.add("MXN");


        spi_divisa.setAdapter(adapter_cp);
        spi_divisa.setSelection(2); //display hint
    }
}
