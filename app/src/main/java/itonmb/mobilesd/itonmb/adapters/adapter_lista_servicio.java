package itonmb.mobilesd.itonmb.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import itonmb.mobilesd.itonmb.barcos_abordar;
import itonmb.mobilesd.itonmb.upgrade;
import itonmb.mobilesd.itonmb.R;
import itonmb.mobilesd.itonmb.ingresa_efectivo_caja;
import itonmb.mobilesd.itonmb.modelo.*;

import java.util.ArrayList;

/**
 * Created by Conrado on 09/05/2017.
 */

public class adapter_lista_servicio extends BaseAdapter {
    TextView tview_cupon, tview_agencia, tview_tour, tview_adulto, tview_nino, tview_infante, tview_nombre, tview_hotel, tview_habi, tview_importe,hidden_producto_padre;
    Button btn_obs_ver, btn_flag, btn_status, btn_menu;


    // Declare Variables
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<modelo_lista_orden> lista;

    public adapter_lista_servicio(Context context, ArrayList<modelo_lista_orden> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, final View convertView, ViewGroup parent) {

        // Declare Variables



        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = inflater.inflate(R.layout.lista_datos_servicio, parent, false);

        tview_cupon = (TextView) itemView.findViewById(R.id.tview_cupon);
        tview_agencia = (TextView) itemView.findViewById(R.id.tview_agencia);
        tview_tour = (TextView) itemView.findViewById(R.id.tview_tour);
        tview_adulto = (TextView) itemView.findViewById(R.id.tview_adulto);
        tview_nino = (TextView) itemView.findViewById(R.id.tview_nino);
        tview_infante = (TextView) itemView.findViewById(R.id.tview_infante);
        tview_nombre = (TextView) itemView.findViewById(R.id.tview_nombre);
        tview_hotel = (TextView) itemView.findViewById(R.id.tview_hotel);
        tview_habi = (TextView) itemView.findViewById(R.id.tview_habi);
        tview_importe = (TextView) itemView.findViewById(R.id.tview_importe);
        hidden_producto_padre = (TextView) itemView.findViewById(R.id.hidden_producto_padre);

        btn_obs_ver = (Button) itemView.findViewById(R.id.btn_obs_ver);
        btn_flag = (Button) itemView.findViewById(R.id.btn_flag);
        btn_status = (Button) itemView.findViewById(R.id.btn_status);
        btn_menu = (Button) itemView.findViewById(R.id.btn_menu);


        // Capture position and set to the TextViews
        tview_cupon.setText(lista.get(position).cupon);
        tview_agencia.setText(lista.get(position).agencia);
        tview_tour.setText(lista.get(position).producto);
        tview_adulto.setText(Integer.toString(lista.get(position).adulto));
        tview_nino.setText(Integer.toString(lista.get(position).menor));
        tview_infante.setText(Integer.toString(lista.get(position).infante));
        tview_nombre.setText(lista.get(position).nombre);
        tview_hotel.setText(lista.get(position).hotel);
        tview_habi.setText(lista.get(position).habi);
        tview_importe.setText(Double.toString(lista.get(position).importe));



        btn_obs_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, lista.get(position).cupon.toString(), Toast.LENGTH_LONG).show();
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, v);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_lista_orden, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        //noinspection SimplifiableIfStatement
                        if (id == R.id.abordar) {
                            Intent anIntent = new Intent(context, barcos_abordar.class);
                            context.startActivity(anIntent);
                            return true;
                        }

                        if (id == R.id.cancelar) {
                            Toast.makeText(context,"En construcción...",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (id == R.id.no_show) {
                            Toast.makeText(context,"En construcción...",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (id == R.id.upgrade) {
                            Intent anIntent = new Intent(context, upgrade.class);
                            anIntent.putExtra("cupon",lista.get(position).cupon);
                            anIntent.putExtra("adulto",Integer.toString(lista.get(position).adulto));
                            anIntent.putExtra("menor",Integer.toString(lista.get(position).menor));
                            anIntent.putExtra("infante",Integer.toString(lista.get(position).infante));
                            anIntent.putExtra("producto_padre",Integer.toString(lista.get(position).producto_padre));
                            context.startActivity(anIntent);
                            return true;
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        return itemView;

    }
}