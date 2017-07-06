package itonmb.mobilesd.itonmb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import itonmb.mobilesd.itonmb.DB.DBhelper;
import itonmb.mobilesd.itonmb.Utils.BaseMenu;
import itonmb.mobilesd.itonmb.Utils.Global;
import itonmb.mobilesd.itonmb.Utils.Snackmsg;
import itonmb.mobilesd.itonmb.adapters.adapter_lista_agregar_brazalete;
import itonmb.mobilesd.itonmb.modelo.modelo_lista_agregar_brazalete;

public class agregar_brazalete extends BaseMenu {

    Button btn_asignar,btn_aceptar;
    int ad_cupon,me_cupon,in_cupon,id_tour;
    String cupon,producto_desc;
    TextView txt_folio_brazalete, txt_br_adultos,txt_br_menores,txt_br_infantes;
    DBhelper dbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_agregar_brazalete, contentFrameLayout);
        toolbar.setTitle("Asignar Brazaletes");
        dbs = new DBhelper(getApplicationContext());

        //Obtengo valores enviados por el activity anterior
        Bundle extras = getIntent().getExtras();
        cupon= Global.cupon;
        ad_cupon=extras.getInt("adulto");
        me_cupon=extras.getInt("menor");
        in_cupon=extras.getInt("infante");
        id_tour=extras.getInt("id_producto");
        producto_desc=extras.getString("producto_desc");

        findviews();
        set_triggers();
        genera_lista_brazaletes_seleccionados();


        txt_br_adultos.setText(Integer.toString(ad_cupon));
        txt_br_menores.setText(Integer.toString(me_cupon));
        txt_br_infantes.setText(Integer.toString(in_cupon));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    private void genera_lista_brazaletes_seleccionados() {

        ArrayList<modelo_lista_agregar_brazalete> datos = dbs.getBrazaletes_asignados(cupon,id_tour,producto_desc);

        ListView lay_upgrades = (ListView) findViewById(R.id.list_brazaletes_agregados);
        adapter_lista_agregar_brazalete adapter = new adapter_lista_agregar_brazalete(agregar_brazalete.this, datos);
        lay_upgrades.setAdapter(adapter);
    }

    private void findviews(){

        btn_asignar = (Button) findViewById(R.id.btn_asignar_brazalete);
        btn_aceptar = (Button) findViewById(R.id.btn_aceptar_brazalete);

        txt_folio_brazalete = (TextView) findViewById(R.id.txt_folio_brazalete);
        txt_br_adultos = (TextView) findViewById(R.id.txt_br_adultos);
        txt_br_menores = (TextView) findViewById(R.id.txt_br_menores);
        txt_br_infantes = (TextView) findViewById(R.id.txt_br_infantes);

    }

    private void set_triggers (){

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener total de brazaletes asignados.
                int total_pax = dbs.getTotal_pax_abordar(cupon);
                if (total_pax!=0) {
                    Intent intent = new Intent(getApplicationContext(), barcos_abordar.class);
                    intent.putExtra("producto", id_tour);
                    intent.putExtra("total_pax_cupon", ad_cupon+me_cupon+in_cupon);
                    intent.putExtra("total_pax", total_pax);
                    startActivity(intent);
                }else{
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "No se han asignado brazaletes.", R.drawable.warn, "#f9db59").show();
                }
            }
        });

        btn_asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String folio = txt_folio_brazalete.getText().toString();
                if(!folio.equals("")) {
                    String encontrado = dbs.busca_brazalete(folio,id_tour,producto_desc,cupon,ad_cupon,me_cupon,in_cupon,0,0,0);
                    valida_encontrado(encontrado,v);
                    txt_folio_brazalete.setText("");
                }else{
                    Snackmsg bar = new Snackmsg();
                    bar.getBar(v, "Favor de Seleccionar un Folio.", R.drawable.warn, "#f9db59").show();
                }
                // Oculta teclado
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

            }
        });



    }
    private void valida_encontrado(String encontrado, View v){
        Snackmsg bar = new Snackmsg();
        switch (encontrado){
            case "adulto":
                genera_lista_brazaletes_seleccionados();
                break;
            case "menor":
                genera_lista_brazaletes_seleccionados();
                break;
            case "infante":
                genera_lista_brazaletes_seleccionados();
                break;
            case "adultox":
                bar.getBar(v, "Numero de adultos superado, no se puede agregar el brazalete.", R.drawable.error, "#fe3939").show();
                break;
            case "menorx":
                bar.getBar(v, "Numero de menores superado, no se puede agregar el brazalete.", R.drawable.error, "#fe3939").show();
                break;
            case "infantex":
                bar.getBar(v, "Numero de infantes superado, no se puede agregar el brazalete.", R.drawable.error, "#fe3939").show();
                break;
            case "nada":
                bar.getBar(v, "No se encontro el folio o ya se encuentra asignado.", R.drawable.warn, "#f9db59").show();
                break;
        }




    }
}
