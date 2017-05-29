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
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;

/**
 * Created by Conrado on 09/05/2017.
 */

public class adapter_lista_upgrade_productos extends BaseAdapter {
    TextView tview_desc, tview_adulto,tview_nino,tview_infante,tview_importe,tview_pago;
    Button btn_delete;


    // Declare Variables
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<modelo_lista_upgrade_productos> lista;

    public adapter_lista_upgrade_productos(Context context, ArrayList<modelo_lista_upgrade_productos> lista) {
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

        View itemView = inflater.inflate(R.layout.lista_productos_upgrade, parent, false);

        tview_desc = (TextView) itemView.findViewById(R.id.tview_desc_producto);
        tview_adulto = (TextView) itemView.findViewById(R.id.tview_upgrade_adulto);
        tview_nino = (TextView) itemView.findViewById(R.id.tview_upgrade_nino);
        tview_infante = (TextView) itemView.findViewById(R.id.tview_upgrade_infante);
        tview_importe = (TextView) itemView.findViewById(R.id.tview_upgrade_importe);
        tview_pago = (TextView) itemView.findViewById(R.id.tview_upgrade_pago);



        btn_delete = (Button) itemView.findViewById(R.id.btn_upgrade_delete);
        tview_desc.setText(lista.get(position).descripcion);
        tview_adulto.setText(Integer.toString(lista.get(position).adulto));
        tview_nino.setText(Integer.toString(lista.get(position).nino));
        tview_infante.setText(Integer.toString(lista.get(position).infante));
        tview_importe.setText(Integer.toString(lista.get(position).importe));
        tview_pago.setText(Integer.toString(lista.get(position).pago));






        return itemView;

    }
}