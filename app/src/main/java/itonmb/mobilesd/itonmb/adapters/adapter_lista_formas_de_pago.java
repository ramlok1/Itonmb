package itonmb.mobilesd.itonmb.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.R;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_formas_de_pago;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_upgrade_productos;

/**
 * Created by Conrado on 09/05/2017.
 */

public class adapter_lista_formas_de_pago extends BaseAdapter {
    TextView tview_forma_pago,tview_moneda,tview_monto_moneda,tview_monto_mn;
    DecimalFormat precision = new DecimalFormat("0.00");
    DBhelper dbs ;


    // Declare Variables
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<modelo_lista_formas_de_pago> lista;

    public adapter_lista_formas_de_pago(Context context, ArrayList<modelo_lista_formas_de_pago> lista) {
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
          inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_pagos, parent, false);

        //txtview del total en el activity de formas
        tview_forma_pago = (TextView) itemView.findViewById(R.id.tview_forma_pago);
        tview_moneda = (TextView) itemView.findViewById(R.id.tview_moneda);
        tview_monto_moneda = (TextView) itemView.findViewById(R.id.tview_monto_moneda);
        tview_monto_mn = (TextView) itemView.findViewById(R.id.tview_monto_mn);
        ////
        tview_forma_pago.setText(lista.get(position).forma_papgo);
        tview_moneda.setText(lista.get(position).moneda);
        tview_monto_moneda.setText(precision.format(lista.get(position).monto_moneda));
        tview_monto_mn.setText(precision.format(lista.get(position).monto_mn));

        return itemView;

    }
}