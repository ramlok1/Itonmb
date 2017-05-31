package itonmb.mobilesd.itonmb.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.R;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_agregar_brazalete;

/**
 * Created by Conrado on 09/05/2017.
 */

public class adapter_lista_agregar_brazalete extends BaseAdapter {
    TextView tview_folio, tview_producto;
    Button btn_del_brazalete, btn_color;


    // Declare Variables
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<modelo_lista_agregar_brazalete> lista;

    public adapter_lista_agregar_brazalete(Context context, ArrayList<modelo_lista_agregar_brazalete> lista) {
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


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_agregar_brazaletes, parent, false);

        tview_folio = (TextView) itemView.findViewById(R.id.tview_folio_brazalete);
        tview_producto = (TextView) itemView.findViewById(R.id.tview_producto);




        btn_del_brazalete = (Button) itemView.findViewById(R.id.btn_delete_brazalete);
        btn_color = (Button) itemView.findViewById(R.id.color_braz_agregado);
        tview_folio.setText(lista.get(position).folio);
        tview_producto.setText(lista.get(position).producto);

        btn_color.setBackgroundColor(Color.parseColor(lista.get(position).color));







        return itemView;

    }
}