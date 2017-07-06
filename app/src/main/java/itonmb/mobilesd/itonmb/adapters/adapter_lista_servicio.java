package itonmb.mobilesd.itonmb.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
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

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.agregar_brazalete;
import itonmb.mobilesd.itonmb.barcos_abordar;
import itonmb.mobilesd.itonmb.cancelar;
import itonmb.mobilesd.itonmb.forma_de_pago;
import itonmb.mobilesd.itonmb.noshow;
import itonmb.mobilesd.itonmb.upgrade;
import itonmb.mobilesd.itonmb.R;
import itonmb.mobilesd.itonmb.ingresa_efectivo_caja;
import itonmb.mobilesd.itonmb.modelo.*;

import java.util.ArrayList;

/**
 * Created by Conrado on 09/05/2017.
 */

public class adapter_lista_servicio extends BaseAdapter {
    TextView tview_cupon, tview_agencia, tview_tour, tview_adulto, tview_nino, tview_infante, tview_nombre, tview_hotel, tview_habi,hidden_producto_padre;
    Button btn_obs_ver, btn_flag, btn_status, btn_menu;
    DBhelper dbs ;

    // Declare Variables
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<modelo_lista_orden> lista;

    public adapter_lista_servicio(Context context, ArrayList<modelo_lista_orden> lista) {
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

        final View itemView = inflater.inflate(R.layout.lista_datos_servicio, parent, false);
        final String cupon = lista.get(position).cupon;



        tview_cupon = (TextView) itemView.findViewById(R.id.tview_cupon);
        tview_agencia = (TextView) itemView.findViewById(R.id.tview_agencia);
        tview_tour = (TextView) itemView.findViewById(R.id.tview_tour);
        tview_adulto = (TextView) itemView.findViewById(R.id.tview_adulto);
        tview_nino = (TextView) itemView.findViewById(R.id.tview_nino);
        tview_infante = (TextView) itemView.findViewById(R.id.tview_infante);
        tview_nombre = (TextView) itemView.findViewById(R.id.tview_nombre);
        tview_hotel = (TextView) itemView.findViewById(R.id.tview_hotel);
        tview_habi = (TextView) itemView.findViewById(R.id.tview_habi);



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

        /// Verifica status del cupon

        switch (lista.get(position).status){
            case 10:
                btn_status.setBackground(ContextCompat.getDrawable(context,R.drawable.go_show));
                break;
            case 11:
                btn_status.setBackground(ContextCompat.getDrawable(context,R.drawable.noshow));
                break;
            case 14:
                btn_status.setBackground(ContextCompat.getDrawable(context,R.drawable.abordar));
                break;
            case 13:
                btn_status.setBackground(ContextCompat.getDrawable(context,R.drawable.pendiente));
                break;
        }

        //Revisar id de drawer idioma
     /*   int esp = R.drawable.espa;
        int fr = R.drawable.french;
        int al = R.drawable.aleman;
        int ing = R.drawable.ingles;*/
        // Coloca bandera de idioma
        btn_flag.setBackground(ContextCompat.getDrawable(context,lista.get(position).idioma_icono));




        btn_obs_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackmsg bar = new Snackmsg();
                bar.getBar(v,lista.get(position).obs.toString(), R.drawable.obs, "#88d2f2").show();
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
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

                            if(lista.get(position).status==14){
                                Snackmsg bar = new Snackmsg();
                                bar.getBar(v,"Cupon ya abordado, no se pueden realizar cambios", R.drawable.error, "#fe3939").show();
                                return false;
                            }
                             if(dbs.valida_muelle_pagado(cupon)){

                                 Intent intent = new Intent(context, agregar_brazalete.class);
                                 intent.putExtra("adulto", lista.get(position).adulto);
                                 intent.putExtra("menor", lista.get(position).menor);
                                 intent.putExtra("infante", lista.get(position).infante);
                                 intent.putExtra("producto_desc", lista.get(position).producto);
                                 intent.putExtra("id_producto", lista.get(position).producto_padre);
                                 Global.cupon=cupon;
                                 context.startActivity(intent);
                             }
                              else {
                                 Intent intent = new Intent(context, forma_de_pago.class);
                                 //datos para cobro muelle
                                 intent.putExtra("total",0);
                                 intent.putExtra("id_rva",lista.get(position).id_rva);
                                 intent.putExtra("tipo",2);
                                 /////datos para abordaje
                                 intent.putExtra("adulto",lista.get(position).adulto);
                                 intent.putExtra("menor",lista.get(position).menor);
                                 intent.putExtra("infante",lista.get(position).infante);
                                 intent.putExtra("producto_desc",lista.get(position).producto);
                                 intent.putExtra("id_producto",lista.get(position).producto_padre);
                                 Global.cupon=cupon;
                                 context.startActivity(intent);

                                 }
                            return true;
                        }

                        if (id == R.id.cancelar) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Cancelar Reserva: ")
                            .setMessage("Esta seguro desea cancelar la reserva del Cupon: "+cupon);
                            builder.setCancelable(false);

                            // Set up the buttons
                            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        dbs.cancela_Cupon(cupon);
                                    lista.remove(position);
                                    adapter_lista_servicio.this.notifyDataSetChanged();
                                }
                            });
                            builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();


                            return true;
                        }
                        if (id == R.id.no_show) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Reserva No Show: ")
                                    .setMessage("Esta seguro desea cambiar status del cupon: "+cupon+" a no show?");
                            builder.setCancelable(false);

                            // Set up the buttons
                            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbs.noshow_Cupon(cupon);
                                    lista.get(position).status=11;
                                    adapter_lista_servicio.this.notifyDataSetChanged();
                                }
                            });
                            builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                            return true;
                        }
                        if (id == R.id.upgrade) {
                            if(lista.get(position).status==14){
                                Snackmsg bar = new Snackmsg();
                                bar.getBar(v,"Cupon ya abordado, no se pueden realizar cambios", R.drawable.error, "#fe3939").show();
                                return false;
                            }
                            Intent anIntent = new Intent(context, upgrade.class);
                            Global.cupon=lista.get(position).cupon;
                            anIntent.putExtra("adulto",Integer.toString(lista.get(position).adulto));
                            anIntent.putExtra("menor",Integer.toString(lista.get(position).menor));
                            anIntent.putExtra("infante",Integer.toString(lista.get(position).infante));
                            anIntent.putExtra("producto_padre",Integer.toString(lista.get(position).producto_padre));
                            anIntent.putExtra("id_rva",Integer.toString(lista.get(position).id_rva));
                            context.startActivity(anIntent);
                            return true;
                        }

                      /*  if (id==R.id.muelle){

                            int total = (lista.get(position).adulto+lista.get(position).menor+lista.get(position).infante)*10;
                            Intent intent = new Intent(context, forma_de_pago.class);
                            intent.putExtra("total",total);
                            intent.putExtra("id_rva",lista.get(position).id_rva);
                            intent.putExtra("tipo",2);
                            context.startActivity(intent);

                            return true;
                        }*/
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        return itemView;

    }
}
