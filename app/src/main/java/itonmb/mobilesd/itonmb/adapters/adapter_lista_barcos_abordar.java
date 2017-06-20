package itonmb.mobilesd.itonmb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.R;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbarcos;

/**
 * Created by Conrado on 09/05/2017.
 */

public class adapter_lista_barcos_abordar extends BaseAdapter {
    TextView tview_nombre, tview_capacidad, tview_booking, tview_abordar;
    CheckBox chk_sel_abordar;
    DBhelper dbs;

    // Declare Variables
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<modelo_lista_dbarcos> lista;

    public adapter_lista_barcos_abordar(Context context, ArrayList<modelo_lista_dbarcos> lista) {
        this.context = context;
        this.lista = lista;
        dbs = new DBhelper(context);
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

        final View itemView = inflater.inflate(R.layout.lista_barcos_abordar, parent, false);

        tview_nombre = (TextView) itemView.findViewById(R.id.tview_nombre_barco_abordar);
        tview_capacidad = (TextView) itemView.findViewById(R.id.tview_cap_barco_abordar);
        tview_booking = (TextView) itemView.findViewById(R.id.tview_book_barco_abordar);
        tview_abordar = (TextView) itemView.findViewById(R.id.tview_abordar_barco_abordar);


        chk_sel_abordar = (CheckBox) itemView.findViewById(R.id.chk_sel_abordar);


        final int id_bote =lista.get(position).id;
        // Capture position and set to the TextViews
        tview_nombre.setText(lista.get(position).nombre);
        tview_capacidad.setText(Integer.toString(lista.get(position).capacidad));
        tview_booking.setText(Integer.toString(lista.get(position).booking));
        tview_abordar.setText(Integer.toString(lista.get(position).abordar));

        chk_sel_abordar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dbs.update_barcos_check_clean();
                if (isChecked){
                    dbs.update_barcos_check_yes(id_bote);
                }
            }
        });



        return itemView;

    }
}