package itonmb.mobilesd.itonmb;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class search_orden extends BaseMenu {
    Button btn_search;
    EditText txt_fecha;
    Calendar calendario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_search_orden, contentFrameLayout);
        toolbar.setTitle("Ingresa número de la Orden de Servicio");

        // Asigna customa lay al spinner de productos
        Spinner spinner_productos = (Spinner) findViewById(R.id.spi_prod);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(search_orden.this, R.array.arqueo_array, R.layout.style_spinner_item);
        spinner_productos.setAdapter(adapter);

        findviews();
        set_triggers();


    }

    private void findviews() {
        txt_fecha = (EditText) findViewById(R.id.txt_bus_date);
        btn_search = (Button) findViewById(R.id.btn_search_orden);

    }

    private void set_triggers(){
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent =
                                        new Intent(getApplicationContext(), listado_orden.class);
                                startActivity(intent);
                            }
                        });
                    }
                }).start();
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
}


