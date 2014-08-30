package com.miCash.espol.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miCash.espol.global.GlobalClass;
import com.miCash.espol.others.Cookie;

public class MenuPrincipal extends Activity {

    private GlobalClass globalVariable = null;
    private DrawerLayout drawerLayout;
    private String[] opciones;
    private ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        globalVariable = (GlobalClass) getApplicationContext();
        inicializarComponentes();

        LayoutInflater inflater = LayoutInflater.from(this);
        int id = R.layout.layout_all_transactions;

        for(int i=0;i<10;i++){
            TableLayout tableLayout = (TableLayout)inflater.inflate(id,null,false);
            TextView textView = (TextView) tableLayout.findViewById(R.id.transactions_description);
            textView.setText("Cumple");

            textView = (TextView) tableLayout.findViewById(R.id.transactions_categoria);
            textView.setText("Entretenimiento");

            textView = (TextView) tableLayout.findViewById(R.id.transactions_fecha);
            textView.setText("20/01/2014");

            textView = (TextView) tableLayout.findViewById(R.id.transactions_valor);
            textView.setText(" - $10");
            textView.setBackgroundColor(Color.RED);
            if(i==8||i==9||i==7){
                textView.setText(" + $15");
                textView.setBackgroundColor(Color.GREEN);
            }
            layout.addView(tableLayout);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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

    private void inicializarDrawer(){
        opciones = getResources().getStringArray(R.array.options_array);
        ListView drawer = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,opciones));
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                drawerLayout.closeDrawers();
                if(opciones[arg2].equals("Añadir Ingreso/Gasto")){
                    Intent ingresoGasto = new Intent(MenuPrincipal.this, IngresoGasto.class);
                    startActivity(ingresoGasto);
                }

                if(opciones[arg2].equals("Cerrar Sesión")){
                    Cookie.writeCookie("", MenuPrincipal.this);
                    globalVariable.setUsername(null);
                    Intent iniciarSesion = new Intent(MenuPrincipal.this, Start.class);
                    iniciarSesion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(iniciarSesion);
                    finish();
                }
            }
        });
    }

    private void inicializarComponentes(){
        inicializarDrawer();
        layout = (ViewGroup) findViewById(R.id.content);
    }

}
