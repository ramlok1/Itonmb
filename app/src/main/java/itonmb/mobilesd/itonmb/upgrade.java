package itonmb.mobilesd.itonmb;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_upgrade_productos;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;
import itonmb.mobilesd.itonmb.modelo.modelo_spinner_productos_upg;

public class upgrade extends BaseMenu {

    Button btn_fp_upgrade,btn_menos_a,btn_mas_a,btn_menos_n,btn_mas_n,btn_menos_infante,btn_mas_infante,btn_add_upg;
    TextView txt_upgrade_adultos,txt_upgrade_nino,txt_upgrade_infante,txt_total_pago_upgrade;
    String cupon;
    int ad_cupon,me_cupon,in_cupon,id_producto_padre,total;
    Spinner spi_productos_upg;
    DBhelper dbs ;
    int[] id_producto,importe_producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_upgrade, contentFrameLayout);
        toolbar.setTitle("Upgrade");
        dbs = new DBhelper(getApplicationContext());

        //Obtengo valores enviados por el activity anterior
        Bundle extras = getIntent().getExtras();
        cupon= Global.cupon;
        ad_cupon=Integer.parseInt(extras.getString("adulto"));
        me_cupon=Integer.parseInt(extras.getString("menor"));
        in_cupon=Integer.parseInt(extras.getString("infante"));
        in_cupon=Integer.parseInt(extras.getString("infante"));
        id_producto_padre=Integer.parseInt(extras.getString("producto_padre"));

        findviews();
        set_triggers();
        genera_lista_productos_seleccionados();
        prepara_spinner();

        // Oculta teclado
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void genera_lista_productos_seleccionados() {

        ArrayList<modelo_lista_upgrade_productos> datos = dbs.getUpgrade_seleccionados(cupon);

        //Coloca total a pagar
        total = dbs.getTotal_upgrade(cupon);
        txt_total_pago_upgrade.setText(Integer.toString(total));

        ListView lay_upgrades = (ListView) findViewById(R.id.list_productos_upgrade);
        adapter_lista_upgrade_productos adapter = new adapter_lista_upgrade_productos(upgrade.this, datos);
        lay_upgrades.setAdapter(adapter);
    }

    private void findviews(){

        btn_fp_upgrade = (Button) findViewById(R.id.btn_fp_upgrade);
        btn_menos_a = (Button) findViewById(R.id.btn_menos_a);
        btn_mas_a = (Button) findViewById(R.id.btn_mas_a);
        btn_menos_n = (Button) findViewById(R.id.btn_menos_n);
        btn_mas_n = (Button) findViewById(R.id.btn_mas_n);
        btn_menos_infante = (Button) findViewById(R.id.btn_menos_infante);
        btn_mas_infante = (Button) findViewById(R.id.btn_mas_infante);
        btn_add_upg = (Button) findViewById(R.id.btn_add_upg);

        txt_upgrade_adultos = (TextView) findViewById(R.id.txt_upgrade_adultos);
        txt_upgrade_nino = (TextView) findViewById(R.id.txt_upgrade_nino);
        txt_upgrade_infante = (TextView) findViewById(R.id.txt_upgrade_infante);
        txt_total_pago_upgrade = (TextView) findViewById(R.id.txt_total_pago_upgrade);

        spi_productos_upg = (Spinner) findViewById(R.id.spin_producto_upg);
    }

    private void set_triggers (){

        btn_fp_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (total == 0){
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "Ningun upgrade seleccionado.", R.drawable.warn, "#f9db59").show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), forma_de_pago.class);
                    intent.putExtra("total",total);
                    startActivity(intent);
                }
            }
        });

        // Botones mas menos de adultos////////////////
        btn_menos_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_upgrade_adultos.getText().equals("0")) {
                    txt_upgrade_adultos.setText("0");
                } else {
                    int tiempo = Integer.parseInt(txt_upgrade_adultos.getText().toString());
                    tiempo = tiempo - 1;
                    txt_upgrade_adultos.setText(Integer.toString(tiempo));
                }

            }
        });

        btn_mas_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor_actual = Integer.parseInt(txt_upgrade_adultos.getText().toString());
                if (valor_actual==ad_cupon) {
                    txt_upgrade_adultos.setText(Integer.toString(valor_actual));
                } else {
                    int adult = valor_actual;
                    adult = adult + 1;
                    txt_upgrade_adultos.setText(Integer.toString(adult));
                }

            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////
        // Botones mas menos de menores////////////////
        btn_menos_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_upgrade_nino.getText().equals("0")) {
                    txt_upgrade_nino.setText("0");
                } else {
                    int tiempo = Integer.parseInt(txt_upgrade_nino.getText().toString());
                    tiempo = tiempo - 1;
                    txt_upgrade_nino.setText(Integer.toString(tiempo));
                }

            }
        });

        btn_mas_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor_actual = Integer.parseInt(txt_upgrade_nino.getText().toString());
                if (valor_actual==me_cupon) {
                    txt_upgrade_nino.setText(Integer.toString(valor_actual));
                } else {
                    int adult = valor_actual;
                    adult = adult + 1;
                    txt_upgrade_nino.setText(Integer.toString(adult));
                }

            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////
        // Botones mas menos de infantes////////////////
        btn_menos_infante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_upgrade_infante.getText().equals("0")) {
                    txt_upgrade_infante.setText("0");
                } else {
                    int tiempo = Integer.parseInt(txt_upgrade_infante.getText().toString());
                    tiempo = tiempo - 1;
                    txt_upgrade_infante.setText(Integer.toString(tiempo));
                }

            }
        });

        btn_mas_infante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor_actual = Integer.parseInt(txt_upgrade_infante.getText().toString());
                if (valor_actual==in_cupon) {
                    txt_upgrade_infante.setText(Integer.toString(valor_actual));
                } else {
                    int adult = valor_actual;
                    adult = adult + 1;
                    txt_upgrade_infante.setText(Integer.toString(adult));
                }

            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////

        btn_add_upg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int upg_adulto =Integer.parseInt(txt_upgrade_adultos.getText().toString());
                int upg_menor =Integer.parseInt(txt_upgrade_nino.getText().toString());
                int upg_infante =Integer.parseInt(txt_upgrade_infante.getText().toString());

                ///variables del producto
                String producto_selecc = spi_productos_upg.getSelectedItem().toString();


                if (producto_selecc.equals("Producto"))
                {
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "Favor de Seleccionar un Producto.", R.drawable.warn, "#f9db59").show();
                }
                else if(upg_adulto==0&&upg_menor==0&&upg_infante==0){
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "Favor de indicar pax.", R.drawable.warn, "#f9db59").show();
                }else{
                    int producto_importe = importe_producto[spi_productos_upg.getSelectedItemPosition()];
                    int producto_id = id_producto[spi_productos_upg.getSelectedItemPosition()];
                    dbs.inserta_upgrade_temporal(cupon,producto_id,producto_selecc,upg_adulto,upg_menor,upg_infante,producto_importe);
                    genera_lista_productos_seleccionados();
                }
            }
        });

    }

    private void prepara_spinner(){

        int c=0;
        // Obtener datos para la lista de productos
        ArrayList<modelo_spinner_productos_upg> data= dbs.getProductos_upgrade(id_producto_padre);
        id_producto= new int[data.size()];
        importe_producto= new int[data.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.style_spinner_item) {

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

        adapter.setDropDownViewResource(R.layout.style_spinner_item);
        for (modelo_spinner_productos_upg producto: data) {
            adapter.add(producto.descripcion);
            id_producto[c]=producto.id;
            importe_producto[c]=producto.importe;
            c++;
        }
        adapter.add("Producto");

        spi_productos_upg.setAdapter(adapter);
        spi_productos_upg.setSelection(adapter.getCount()); //display hint
    }
}
