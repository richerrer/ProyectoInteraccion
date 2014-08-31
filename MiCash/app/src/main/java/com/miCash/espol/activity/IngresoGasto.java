package com.miCash.espol.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.miCash.espol.activity.R;
import com.miCash.espol.global.GlobalClass;
import com.miCash.espol.menu.ArrayAdapterMenu;
import com.miCash.espol.menu.Item;
import com.miCash.espol.others.Cookie;

import java.util.ArrayList;

public class IngresoGasto extends Activity {

    private GlobalClass globalVariable = null;
    private DrawerLayout drawerLayout;
    private String[] opciones;
    private ArrayList<Item> items ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_gasto);
        inicializarComponentes();
        globalVariable = (GlobalClass) getApplicationContext();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ingreso_gasto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializarComponentes(){
        inicializarDrawer();

    }

    private void inicializarDrawer(){
        opciones = getResources().getStringArray(R.array.options_array);
        ListView drawer = (ListView) findViewById(R.id.drawer_gasto);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_gasto);
        items = new ArrayList<Item>();
        items.add(new Item(R.drawable.ic_action_edit,"Editar Perfil"));
        items.add(new Item(R.drawable.ic_action_paste,"Reporte"));
        items.add(new Item(R.drawable.ic_action_new,"Añadir Ingreso/Gasto"));
        //items.add(new Item(R.drawable.ic,"Proyección"));
        items.add(new Item(R.drawable.ic_action_alarms,"Configurar Alarma"));
        items.add(new Item(R.drawable.ic_action_location_searching,"Nuevo Lugar Favorito"));
        items.add(new Item(R.drawable.ic_action_favorite,"Ver Lugares Favoritos"));
        items.add(new Item(R.drawable.ic_action_secure,"Cerrar Sesión"));
        ArrayAdapterMenu adapter = new ArrayAdapterMenu(this,items);
        drawer.setAdapter(adapter);
        //drawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,opciones));
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                drawerLayout.closeDrawers();
                if(items.get(arg2).getTexto().equals("Añadir Ingreso/Gasto")){
                    Intent ingresoGasto = new Intent(IngresoGasto.this, IngresoGasto.class);
                    startActivity(ingresoGasto);
                }

                if(items.get(arg2).getTexto().equals("Cerrar Sesión")){
                    Cookie.writeCookie("", IngresoGasto.this);
                    globalVariable.setUsuario(null);
                    Intent iniciarSesion = new Intent(IngresoGasto.this, Start.class);
                    iniciarSesion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(iniciarSesion);
                    finish();
                }
            }
        });
    }

}
