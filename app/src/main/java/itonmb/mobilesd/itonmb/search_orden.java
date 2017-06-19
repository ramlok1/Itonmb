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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_orden;

public class search_orden extends BaseMenu {
    Button btn_search;
    EditText txt_fecha,txt_name,txt_id_oper;
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

        // Oculta teclado
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


    }

    private void findviews() {
        txt_name = (EditText) findViewById(R.id.txt_busc_name);
        txt_fecha = (EditText) findViewById(R.id.txt_busc_date);
        txt_id_oper = (EditText) findViewById(R.id.txt_busc_id_oper);
        btn_search = (Button) findViewById(R.id.btn_search_orden);
        spi_producto = (Spinner) findViewById(R.id.spi_busc_prod);
    }

    private void set_triggers(){

        // Disparador de boton para buscar orden de servicio
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consulta_where=" where 0=0";
                // Se pasan a variables para que se coloque "" en vez de null
                String name= txt_name.getText().toString();
                String fecha= txt_fecha.getText().toString();
                String operacion= txt_id_oper.getText().toString();
                String producto = spi_producto.getSelectedItem().toString();

                if(producto=="Producto"){producto="";}

                // Armado de where dinamico
                if(name.length()!=0){consulta_where=consulta_where+" and nombre_cliente like '%"+name+"%'";}
                if(fecha.length()!=0){consulta_where=consulta_where+" and fecha = '"+fecha+"'";}
                if(producto.length()!=0){consulta_where=consulta_where+" and producto like '%"+producto+"%'";}
                if(operacion.length()!=0){consulta_where=consulta_where+" and orden_servicio="+operacion;}
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

        txt_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                calendario = Calendar.getInstance();
                int mYear = calendario.get(Calendar.YEAR);
                int mMonth = calendario.get(Calendar.MONTH);
                int mDay = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(search_orden.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        calendario.set(Calendar.YEAR, selectedyear);
                        calendario.set(Calendar.MONTH, selectedmonth);
                        calendario.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        txt_fecha.setText(sdf.format(calendario.getTime()));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Selecciona fecha:");
                mDatePicker.show();
            }
        });

    }

    private void prepara_spinner(){
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
        adapter.add("Isla M. Plus");
        adapter.add("Cozumel");
        adapter.add("Producto");

        spi_producto.setAdapter(adapter);
       spi_producto.setSelection(adapter.getCount()); //display hint
    }





}


