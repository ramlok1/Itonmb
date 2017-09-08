package itonmb.mobilesd.itonmb;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;
import itonmb.mobilesd.itonmb.modelo.modelo_spinner_productos_upg;

public class search_orden extends BaseMenu {
    Button btn_search;
    EditText txt_name,txt_id_oper, txt_cupon;
    TextView txt_fecha;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    Calendar calendario;
    Spinner spi_producto;
    DBhelper dbs ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_search_orden, contentFrameLayout);
        toolbar.setTitle("Ingresa número de la Orden de Servicio");
        dbs = new DBhelper(getApplicationContext());

        // Asigna customa lay al spinner de productos



        findviews();
        set_triggers();
        prepara_spinner();


        txt_fecha.setText(dateFormat.format(date).toString());

        // Oculta teclado
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


    }

    private void findviews() {
        txt_name = (EditText) findViewById(R.id.txt_busc_name);
        txt_fecha = (TextView) findViewById(R.id.txt_busc_date);
        txt_id_oper = (EditText) findViewById(R.id.txt_busc_id_oper);
        txt_cupon = (EditText) findViewById(R.id.txt_busc_cupon);
        btn_search = (Button) findViewById(R.id.btn_search_orden);
        spi_producto = (Spinner) findViewById(R.id.spi_busc_prod);
    }

    private void set_triggers(){

        // Disparador de boton para buscar orden de servicio
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se pasan a variables para que se coloque "" en vez de null
                String name= txt_name.getText().toString();
               // String fecha= "14/07/17";//txt_fecha.getText().toString();
                String operacion= txt_id_oper.getText().toString();
                String producto = spi_producto.getSelectedItem().toString();
                String cupon = txt_cupon.getText().toString();
                Global.consulta_where=" where status not in (14,2) and (adulto+menor+infante)!=0";
                String consulta_where=Global.consulta_where;
                if(producto=="Producto"){producto="";}

                // Armado de where dinamico
                if(name.length()!=0){consulta_where=consulta_where+" and nombre_cliente like '%"+name+"%'";}
               // if(fecha.length()!=0){consulta_where=consulta_where+" and fecha = '"+fecha+"'";}
                if(producto.length()!=0){consulta_where=consulta_where+" and producto like '%"+producto+"%'";}
                if(operacion.length()!=0){consulta_where=consulta_where+" and orden_servicio="+operacion;}
                if(cupon.length()!=0){consulta_where=consulta_where+" and cupon='"+cupon+"'";}
                Global.consulta_where=consulta_where;
                ArrayList<modelo_lista_orden> data= dbs.getSearch_Cupones(consulta_where);


                if(data==null){
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "No se encontro información.", R.drawable.error, "#fe3939").show();

                }else {
                    Intent intent = new Intent(getApplicationContext(), listado_orden.class);
                    startActivity(intent);
                }

            }
        });

    }

    private void prepara_spinner(){

        ArrayList<modelo_spinner_productos_upg> data= dbs.getProductos();

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
        }
        adapter.add("Producto");

        spi_producto.setAdapter(adapter);
       spi_producto.setSelection(adapter.getCount()); //display hint
    }





}


