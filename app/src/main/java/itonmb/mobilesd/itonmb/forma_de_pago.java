package itonmb.mobilesd.itonmb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_formas_de_pago;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_formas_de_pago;


public class forma_de_pago extends BaseMenu {

    DBhelper dbs ;
    String cupon,autoriza_descuento="",producto_desc;
    Button btn_pago_fp,btn_cancelar_fp,btn_finalizar_fp,btn_descuento ;
    int importe_total,id_rva,tipo,id_upg=9999999,adulto,menor,infante,id_producto,total_pax, impuesto_muelle=0;
    double importe_final,total_pagado,importe_descuento=0;
    Spinner spi_forma_pago,spi_divisa;
    TextView txt_cambio_forma_pago,txt_total_fp,txt_subtotal,txt_descuento,txt_imp_muelle,txt_saldo,txt_total_pagado;
    EditText txt_monto_forma_pago,txt_recibido_forma_pago;
    int[] upg_datos  = new int[4];
    DecimalFormat precision = new DecimalFormat("0.00");
    View layout_popup;
    SignatureView signatureView;
    byte[] byteArray;
    DateFormat dateFormat_hora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_forma_de_pago, contentFrameLayout);
        toolbar.setTitle("Forma de Pago");
        dbs = new DBhelper(getApplicationContext());
        cupon = Global.cupon;
        Bundle extras = getIntent().getExtras();
        importe_total = extras.getInt("total");
        importe_final = extras.getInt("total");
        id_rva = extras.getInt("id_rva");
        tipo = extras.getInt("tipo");
        adulto = extras.getInt("adulto");
        menor = extras.getInt("menor");
        infante = extras.getInt("infante");
        producto_desc = extras.getString("producto_desc");
        id_producto = extras.getInt("id_producto");

        findview();
        set_triggers();
        prepara_spinner();
        if (tipo == 1) {
            total_pax = dbs.getUpgrade_total_pax(cupon);
            //Cancela boton de descuento en caso de que exista descuento aplicado

        } else {
            total_pax = adulto + menor + infante;
            btn_descuento.setVisibility(View.GONE);
        }

        // Valida si ya se pago el impuesto de muelle con este cupon
        if (!dbs.valida_muelle_pagado(cupon)){
            impuesto_muelle = total_pax * Global.importe_muelle;
            }

        importe_final=importe_total+impuesto_muelle;

        //Llenado de valores iniciales
        txt_subtotal.setText(precision.format(importe_total));
        txt_total_fp.setText(precision.format(importe_final));
        txt_imp_muelle.setText(precision.format(impuesto_muelle));
        txt_descuento.setText(precision.format(0));
        txt_saldo.setText(precision.format(importe_final));
        txt_total_pagado.setText(precision.format(0));
        txt_cambio_forma_pago.setText(precision.format(0));
        txt_recibido_forma_pago.setText(precision.format(0));


        genera_lista_pagos();
        // Oculta teclado
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
    private void genera_lista_pagos() {

        ArrayList<modelo_lista_formas_de_pago> datos = dbs.getPagos(cupon);

        //Coloca total a pagar
        total_pagado = dbs.getTotal_pagado(cupon);
        txt_total_pagado.setText(precision.format(total_pagado));

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

        btn_finalizar_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(total_pagado!=importe_final){
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "Importe total no cubierto.", R.drawable.error, "#fe3939").show();
                  }else{

                    if(tipo==1) {
                        upg_datos = dbs.inserta_upgrade(cupon, importe_final,importe_total, id_rva,importe_descuento,autoriza_descuento,byteArray);
                        id_upg = upg_datos[0];
                        adulto = upg_datos[1];
                        menor = upg_datos[2];
                        infante = upg_datos[3];
                        new forma_de_pago.imprime_test().execute();
                    }
                    dbs.update_forma_pago(id_upg,cupon);

                    /////////////////////////////////////////////////////////




                    /////////////////////////////////////////////////////////
                    Intent intent = new Intent(getApplicationContext(), agregar_brazalete.class);
                      intent.putExtra("adulto",adulto);
                      intent.putExtra("menor",menor);
                      intent.putExtra("infante",infante);
                      intent.putExtra("producto_desc",producto_desc);
                      intent.putExtra("id_producto",id_producto);
                     startActivity(intent);
                }
            }
        });

        btn_pago_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forma_pago = spi_forma_pago.getSelectedItem().toString();
                String moneda = spi_divisa.getSelectedItem().toString();


                double monto_moneda = Double.parseDouble(txt_monto_forma_pago.getText().toString());
                double monto_mn;
                // revisa moneda
                if (moneda.equals("EUR")) {
                    monto_mn = monto_moneda * 20.45;
                } else if (moneda.equals("USD")) {
                    monto_mn = monto_moneda * 18.45;
                } else {
                    monto_mn = monto_moneda;
                }

                double recibido = Double.parseDouble(txt_recibido_forma_pago.getText().toString());
                double cambio = Double.parseDouble(txt_cambio_forma_pago.getText().toString());

                    if(recibido!=0) {
                        double importe_cambio = recibido - monto_moneda;
                        txt_cambio_forma_pago.setText(Double.toString(importe_cambio));
                    }

                if (monto_mn + total_pagado > importe_final) {
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "Supera monto a pagar, favor de verificar saldo.", R.drawable.warn, "#f9db59").show();
                } else{
                    dbs.inserta_forma_pago(999991, cupon, forma_pago, monto_moneda, monto_mn, 18.45, moneda, recibido, cambio);
                genera_lista_pagos();
                double saldo = importe_final - total_pagado;
                txt_saldo.setText(precision.format(saldo));
                 }


            }
        });

        btn_descuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow pwin = popup_window(v);
                pwin.showAtLocation(layout_popup, Gravity.CENTER, 0, -20);
            }
        });



        txt_monto_forma_pago.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                txt_recibido_forma_pago.setText("0.00");
                    txt_cambio_forma_pago.setText("0.00");

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
                return super.getCount(); // you dont display last item. It is used as hint.
            }

        };
        adapter_fp.setDropDownViewResource(R.layout.style_spinner_item);


        adapter_fp.add("Tarjeta");
        adapter_fp.add("Efectivo");




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
                return super.getCount(); // you dont display last item. It is used as hint.
            }

        };
        adapter_cp.setDropDownViewResource(R.layout.style_spinner_item);

        adapter_cp.add("EUR");
        adapter_cp.add("USD");
        adapter_cp.add("MXN");


        spi_divisa.setAdapter(adapter_cp);
        spi_divisa.setSelection(2); //display hint
    }

    private PopupWindow popup_window(final View vo){
        final PopupWindow pwindo;


        LayoutInflater inflat = (LayoutInflater) forma_de_pago.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout_popup = inflat.inflate(R.layout.popup_descuento_forma_pago,
                (ViewGroup) findViewById(R.id.layout_principal_pop));




        pwindo = new PopupWindow(layout_popup, 600, 600, true);
        signatureView =  (SignatureView) layout_popup.findViewById(R.id.signature_view);
        Button btnClosePopup = (Button) layout_popup.findViewById(R.id.btnxpop);
        Button btnAplicar_desc = (Button) layout_popup.findViewById(R.id.btn_aplicar_desc);
        final EditText txt_desc = (EditText)  layout_popup.findViewById(R.id.p_descuento);
        final EditText txt_desc_autoriza = (EditText)  layout_popup.findViewById(R.id.txt_autoriza_desc);



        // Triggers de Popup
        btnClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwindo.dismiss();

            }
        });


        btnAplicar_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int p_descuento=0;
                String descuento =txt_desc.getText().toString();
                autoriza_descuento = txt_desc_autoriza.getText().toString();
                if(!descuento.equals("")||!descuento.equals("0")) {
                    p_descuento=Integer.parseInt(txt_desc.getText().toString());
                }

                // Tratamiento de imagen de firma
                Bitmap bmp = signatureView.getSignatureBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
                int img_size = byteArray.length;


               if (p_descuento > 0 && !autoriza_descuento.equals("") && img_size > 2000) {

                    importe_descuento = (p_descuento / 100.00) * importe_total;
                    txt_descuento.setText(precision.format(importe_descuento));

                    //Cambio de importe total
                    importe_final = (importe_total + impuesto_muelle) - importe_descuento;
                    txt_total_fp.setText(precision.format(importe_final));

                    //Cambio de importe saldo
                    double saldo = importe_final - total_pagado;
                    txt_saldo.setText(precision.format(saldo));

                   btn_descuento.setEnabled(false);
                   pwindo.dismiss();

                }else{
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(vo, "Favor de verificar porcentaje de descuento, Autoriza descuento y/o Firma", R.drawable.error, "#fe3939").show();
                }
            }
        });
        return pwindo;
    }

    private class imprime_test extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String resp) {

            super.onPostExecute(resp);

        }

        @Override
        protected String doInBackground(String... params) {
            String resp="";
            try
            {
                String center = "\u001b\u0061\u0001";
                Socket sock = new Socket("192.168.100.21",9100);
                PrintWriter oStream = new PrintWriter(sock.getOutputStream());
                oStream.println(center+"UPGRADE");
                oStream.println("\n");
                oStream.println(center+"  Folio: " + cupon+"                  "+dateFormat.format(date));
                oStream.println("  Upgrade: "+producto_desc);
                oStream.println("  Cantidad: "+total_pax);
                oStream.println("  Brazalete: ");
                oStream.println("\n");
                oStream.println(center+" Impreso: "+dateFormat_hora.format(date)+"  Cajero: "+Global.usuario);
                oStream.println("\n");
                oStream.println(center+"¡MUCHAS GRACIAS! ");
                oStream.println(center+"Marina del Hotel Templation ");
                oStream.println(center+"Blvd Kukulcan KM 3.5 ZH, CANCUN MEXICO ");
                oStream.println(center+"Telf: 848 7972/849 4223/849 5396 ");
                oStream.println(center+"www.albatrossailway.com ");
                oStream.println(center+"ventas@albatrossailway.com ");
                oStream.println("\n\n");
                oStream.println(new char[]{0x1D, 0x56, 0x41, 0x10});
                oStream.close();
                sock.close();
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


            return resp;
        }
    }
}
