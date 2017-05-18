package itonmb.mobilesd.itonmb.adapters;

import android.content.Context;
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

import itonmb.mobilesd.itonmb.R;
import itonmb.mobilesd.itonmb.modelo.*;

import java.util.ArrayList;

/**
 * Created by Conrado on 09/05/2017.
 */

public class adapter_lista_barcos_disp extends BaseAdapter {
    TextView tview_nombre, tview_capacidad, tview_booking, tview_abordar;
    Button btn_menu_det;


    // Declare Variables
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<modelo_lista_dbarcos> lista;

    public adapter_lista_barcos_disp(Context context, ArrayList<modelo_lista_dbarcos> lista) {
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

        final View itemView = inflater.inflate(R.layout.lista_barcos_disponibles, parent, false);

        tview_nombre = (TextView) itemView.findViewById(R.id.tview_nombre_barco);
        tview_capacidad = (TextView) itemView.findViewById(R.id.tview_cap_barco);
        tview_booking = (TextView) itemView.findViewById(R.id.tview_book_barco);
        tview_abordar = (TextView) itemView.findViewById(R.id.tview_abordar_barco);


        btn_menu_det = (Button) itemView.findViewById(R.id.btn_menu_barco);



        // Capture position and set to the TextViews
        tview_nombre.setText(lista.get(position).nombre);
        tview_capacidad.setText(Integer.toString(lista.get(position).capacidad));
        tview_booking.setText(Integer.toString(lista.get(position).booking));
        tview_abordar.setText(Integer.toString(lista.get(position).abordar));





        btn_menu_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, v);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_detalle_barcos_disp, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        //noinspection SimplifiableIfStatement
                        if (id == R.id.abordar) {
                            Toast.makeText(context,"Abordar",Toast.LENGTH_SHORT).show();
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