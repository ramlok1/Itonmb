package itonmb.mobilesd.itonmb.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.R;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_dbrazaletes;

/**
 * Created by Conrado on 09/05/2017.
 */

public class adapter_lista_brazaletes_disp extends BaseAdapter {
    TextView tview_folio, tview_tipo,tview_tour;
    Button btn_color;


    // Declare Variables
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<modelo_lista_dbrazaletes> lista;

    public adapter_lista_brazaletes_disp(Context context, ArrayList<modelo_lista_dbrazaletes> lista) {
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

        View itemView = inflater.inflate(R.layout.lista_brazaletes_disponibles, parent, false);

        tview_folio = (TextView) itemView.findViewById(R.id.tview_folio_brazalete);
        tview_tipo = (TextView) itemView.findViewById(R.id.tview_tipo_braz);
        tview_tour = (TextView) itemView.findViewById(R.id.tview_tour_brazalete);



        btn_color = (Button) itemView.findViewById(R.id.color_braz);



        // Capture position and set to the TextViews
        String colors=lista.get(position).color;
        tview_folio.setText(lista.get(position).folio);
        tview_tipo.setText(lista.get(position).tipo);
        tview_tour.setText(lista.get(position).tour);
        btn_color.setBackgroundColor(Color.parseColor(colors));


        return itemView;

    }
}
